package com.sci.machinery.block.tube;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;
import com.sci.machinery.network.PacketAddItem;
import com.sci.machinery.network.PacketRemoveItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class TubeNormal extends Tube
{
	protected List<TravellingItem> items;
	protected Speed speed = Speed.MEDIUM;
	private int timer;

	public TubeNormal()
	{
		this.items = new ArrayList<TravellingItem>();
	}

	@Override
	public void update()
	{
		if(tile != null && !tile.isInvalid() && !tile.worldObj.isRemote && !items.isEmpty())
		{
			timer++;
			if(timer == speed.delay)
			{
				timer = 0;

				TravellingItem item = items.remove(0);
				PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveItem(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord, 0)));
				BlockCoord[] adjacent = Utils.blockCoord(tile).getAdjacent();

				for(int i = 0; i < adjacent.length; i++)
				{
					BlockCoord pos = adjacent[i];
					if(!pos.equals(item.getLastCoord()))
					{
						TileEntity tile = this.tile.worldObj.getBlockTileEntity(pos.getX(), pos.getY(), pos.getZ());
						if(tile != null)
						{
							if(tile instanceof ISidedInventory)
							{
								ISidedInventory inv = (ISidedInventory) tile;
								int[] slots = inv.getAccessibleSlotsFromSide(ForgeDirection.OPPOSITES[i]);

								for(int j = 0; j < slots.length; j++)
								{
									if(inv.canInsertItem(j, item.getStack(), ForgeDirection.OPPOSITES[i]))
									{
										ItemStack remaining = TileEntityHopper.insertStack(inv, item.getStack(), ForgeDirection.OPPOSITES[i]);
										if(remaining != null)
										{
											items.add(new TravellingItem(remaining));
										}
										return;
									}
								}
							}
							else if(tile instanceof IInventory)
							{
								IInventory inv = (IInventory) tile;
								ItemStack remaining = TileEntityHopper.insertStack(inv, item.getStack(), ForgeDirection.OPPOSITES[i]);
								if(remaining != null)
								{
									items.add(new TravellingItem(remaining));
								}
								return;
							}
						}
					}
				}

				for(int i = 0; i < adjacent.length; i++)
				{
					BlockCoord pos = adjacent[i];
					if(!pos.equals(item.getLastCoord()))
					{
						TileEntity tile = this.tile.worldObj.getBlockTileEntity(pos.getX(), pos.getY(), pos.getZ());
						if(tile instanceof ITubeConnectable)
						{
							if(!item.getLastCoord().equals(pos))
							{
								((ITubeConnectable) tile).addItem(item, this.tile);
								return;
							}
						}
					}
				}

				if(!tile.worldObj.isRemote)
				{
					tile.worldObj.spawnEntityInWorld(new EntityItem(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, item.getStack()));
				}
			}
		}
	}

	@Override
	public void addItem(TravellingItem item, TileEntity sender)
	{
		if(tile == null)
			return;
		if(!tile.worldObj.isRemote)
		{
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketAddItem(tile.xCoord, tile.yCoord, tile.zCoord, item.getStack().itemID, item.getStack().stackSize)));
		}
		if(sender != null)
		{
			item.setLastCoord(Utils.blockCoord(sender));
		}
		items.add(item);
	}

	@Override
	public void breakTube()
	{

	}

	@Override
	public void validate()
	{

	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{

	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{

	}

	@Override
	public List<TravellingItem> getItems()
	{
		return items;
	}

	@Override
	public boolean isPowering()
	{
		return false;
	}

	@Override
	public boolean canAcceptItems()
	{
		return true;
	}

	@Override
	public void removeItem(int index)
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && !items.isEmpty())
		{
			items.remove(index);
		}
	}
}