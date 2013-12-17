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
import com.sci.machinery.block.TileTube;
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

	protected Material material;

	public TubeNormal(boolean isCobble)
	{
		if(isCobble)
			material = Material.COBBLESTONE;
		else
			material = Material.STONE;
		this.items = new ArrayList<TravellingItem>();
	}

	@Override
	public void update()
	{
		if(!isValid())
			return;

		if(!getTile().worldObj.isRemote && !items.isEmpty())
		{
			timer++;
			if(timer == speed.delay)
			{
				timer = 0;

				TravellingItem item = items.remove(0);
				PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveItem(getTile().xCoord, getTile().yCoord, getTile().zCoord, 0)));

				BlockCoord next = TubeRouter.route(getTile(), item);
				if(next == null)
				{
					getTile().worldObj.spawnEntityInWorld(new EntityItem(getTile().worldObj, getTile().xCoord, getTile().yCoord, getTile().zCoord, item.getStack()));
				}
				else
				{
					TileEntity nextTE = Utils.getTileEntity(getTile().worldObj, next);
					if(nextTE == null)
					{
						getTile().worldObj.spawnEntityInWorld(new EntityItem(getTile().worldObj, getTile().xCoord, getTile().yCoord, getTile().zCoord, item.getStack()));
					}
					else if(nextTE instanceof ISidedInventory)
					{
						int side = findSide(Utils.blockCoord(getTile()), next);
						if(side != -1)
						{
							side = ForgeDirection.OPPOSITES[side];
							ISidedInventory inv = (ISidedInventory) nextTE;
							int[] slots = inv.getAccessibleSlotsFromSide(side);

							for(int j = 0; j < slots.length; j++)
							{
								if(inv.canInsertItem(j, item.getStack(), side))
								{
									ItemStack remaining = TileEntityHopper.insertStack(inv, item.getStack(), side);
									if(remaining != null)
									{
										item.setStack(remaining);
										this.addItem(item, nextTE);
										timer = speed.delay - 1;
									}
								}
							}
						}
					}
					else if(nextTE instanceof IInventory)
					{
						int side = findSide(Utils.blockCoord(getTile()), next);
						if(side != -1)
						{
							side = ForgeDirection.OPPOSITES[side];
							IInventory inv = (IInventory) nextTE;
							ItemStack remaining = TileEntityHopper.insertStack(inv, item.getStack(), side);
							if(remaining != null)
							{
								item.setStack(remaining);
								this.addItem(item, getTile());
							}
						}
					}
					else if(nextTE instanceof ITubeConnectable)
					{
						((ITubeConnectable) nextTE).addItem(item, getTile());
					}
				}
			}
		}
		else
		{
			timer = 0;
		}
	}

	private int findSide(BlockCoord base, BlockCoord side)
	{
		BlockCoord[] adjacent = base.getAdjacent();
		for(int i = 0; i < adjacent.length; i++)
			if(adjacent[i].equals(side))
				return i;
		return -1;
	}

	@Override
	public void addItem(TravellingItem item, TileEntity sender)
	{
		if(!getTile().worldObj.isRemote)
		{
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketAddItem(getTile().xCoord, getTile().yCoord, getTile().zCoord, item.getStack().itemID, item.getStack().stackSize)));
		}
		if(sender != null)
		{
			item.getLastCoord().add(Utils.blockCoord(sender));
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

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			TileTube tube = (TileTube) e;
			if(tube.getTube().getClass().getName().equals(TubeNormal.class.getName()))
			{	
				TubeNormal ot = (TubeNormal) tube.getTube();
				return ot.material == this.material;
			}
		}
		return e instanceof ITubeConnectable || e instanceof IInventory;
	}

	@Override
	public Material getMaterial()
	{
		return material;
	}
}