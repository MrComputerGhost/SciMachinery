package com.sci.machinery.block.tube;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;
import com.sci.machinery.network.PacketRemoveItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TubePump extends TubeNormal
{
	private int timer;

	@Override
	public void update()
	{
		if(!isValid())
			return;

		timer++;
		if(timer == speed.delay && !getTile().worldObj.isRemote)
		{
			timer = 0;

			BlockCoord[] adjacent = Utils.blockCoord(getTile()).getAdjacent();
			for(int i = 0; i < adjacent.length; i++)
			{
				BlockCoord pos = adjacent[i];
				TileEntity adjTile = getTile().worldObj.getBlockTileEntity(pos.getX(), pos.getY(), pos.getZ());
				if(adjTile != null)
				{
					if(adjTile instanceof ISidedInventory)
					{
						ISidedInventory inv = (ISidedInventory) adjTile;
						int[] slots = inv.getAccessibleSlotsFromSide(ForgeDirection.OPPOSITES[i]);

						for(int j = 0; j < slots.length; j++)
						{
							ItemStack stack = inv.getStackInSlot(slots[j]);
							if(stack != null && inv.canExtractItem(j, stack, ForgeDirection.OPPOSITES[i]))
							{
								inv.decrStackSize(slots[j], stack.stackSize);
								this.addItem(new TravellingItem(stack), getTile());
								return;
							}
						}
					}
					else if(adjTile instanceof IInventory)
					{
						IInventory inv = (IInventory) adjTile;

						for(int j = 0; j < inv.getSizeInventory(); j++)
						{
							ItemStack stack = inv.getStackInSlot(j);
							if(stack != null)
							{
								inv.decrStackSize(j, stack.stackSize);
								this.addItem(new TravellingItem(stack), getTile());
								return;
							}
						}
					}
				}
			}

			if(!items.isEmpty())
			{
				TravellingItem item = items.remove(0);
				PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveItem(getTile().xCoord, getTile().yCoord, getTile().zCoord, 0)));
				for(int i = 0; i < adjacent.length; i++)
				{
					BlockCoord pos = adjacent[i];
					if(!pos.equals(item.getLastCoord()))
					{
						TileEntity adjTile = getTile().worldObj.getBlockTileEntity(pos.getX(), pos.getY(), pos.getZ());
						if(adjTile instanceof ITubeConnectable && ((ITubeConnectable) adjTile).canAcceptItems())
						{
							((ITubeConnectable) adjTile).addItem(item, getTile());
							return;
						}
					}
				}
				this.addItem(item, null);
			}

			if(!items.isEmpty())
			{
				PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveItem(getTile().xCoord, getTile().yCoord, getTile().zCoord, 0)));
				getTile().worldObj.spawnEntityInWorld(new EntityItem(getTile().worldObj, getTile().xCoord, getTile().yCoord, getTile().zCoord, items.remove(0).getStack()));
			}
		}
	}

	@Override
	public boolean canAcceptItems()
	{
		return false;
	}
	
	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			TileTube tube = (TileTube) e;
			return !(tube.getTube() instanceof TubePump);
		}
		return super.canConnectTube(e);
	}
}