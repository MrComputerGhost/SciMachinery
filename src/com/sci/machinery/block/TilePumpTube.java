package com.sci.machinery.block;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.core.ITubeConnectable;
import com.sci.machinery.core.TravellingItem;
import com.sci.machinery.network.PacketAddItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TilePumpTube extends TileTube implements ITubeConnectable
{
	private int timer;

	@Override
	public void updateEntity()
	{
		timer++;
		if(timer == speed.delay)
		{
			timer = 0;
			if(!items.isEmpty())
			{
				TileEntity[] t = getAdjacentTiles(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				if(allNull(t))
				{
					if(!this.worldObj.isRemote)
						this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.get(0).getStack()));
					items.remove(0);
				}
				else
				{
					boolean sent = true;
					for(int i = 0; i < t.length; i++)
					{
						if(!items.isEmpty())
						{
							if(t[i] instanceof ITubeConnectable)
							{
								if(i != items.get(0).getLastDir() || items.get(0).getLastDir() == -1 && ((ITubeConnectable) t[i]).canAcceptItems())
								{
									items.get(0).setLastDir(i);
									((ITubeConnectable) t[i]).addItem(items.remove(0));
									sent = false;
								}
							}
						}
					}
					if(!sent && !items.isEmpty())
					{
						if(items.get(0) == null)
						{
							items.remove(0);
							return;
						}
						else if(items.get(0).getStack() == null)
						{
							items.remove(0);
							return;
						}
						if(!this.worldObj.isRemote)
							this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.remove(0).getStack()));
					}
				}
			}

			TileEntity[] t = this.getAdjacentTiles(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			if(!allNull(t))
			{
				for(int i = 0; i < t.length; i++)
				{
					TileEntity tile = t[i];
					if(tile instanceof ISidedInventory)
					{
						ISidedInventory inv = (ISidedInventory) tile;
						int[] aint = inv.getAccessibleSlotsFromSide(ForgeDirection.OPPOSITES[i]);

						for(int j = 0; j < aint.length; ++j)
						{
							ItemStack stack = inv.getStackInSlot(aint[j]);
							if(stack != null && inv.canExtractItem(aint[j], stack, ForgeDirection.OPPOSITES[i]))
							{
								inv.decrStackSize(aint[j], stack.stackSize);
								inv.onInventoryChanged();
								if(!worldObj.isRemote)
								{
									PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128, worldObj.provider.dimensionId, PacketTypeHandler.populatePacket(new PacketAddItem(xCoord, yCoord, zCoord, stack.itemID, stack.stackSize)));
									this.addItem(new TravellingItem(stack));
								}
							}
						}
					}
					else if(tile instanceof IInventory)
					{
						int j = ((IInventory) tile).getSizeInventory();

						for(int k = 0; k < j; ++k)
						{
							ItemStack stack = ((IInventory) tile).getStackInSlot(k);
							if(stack != null)
							{
								((IInventory) tile).decrStackSize(k, stack.stackSize);
								((IInventory) tile).onInventoryChanged();
								if(!worldObj.isRemote)
								{
									PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128, worldObj.provider.dimensionId, PacketTypeHandler.populatePacket(new PacketAddItem(xCoord, yCoord, zCoord, stack.itemID, stack.stackSize)));
									this.addItem(new TravellingItem(stack));
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canAcceptItems()
	{
		return false;
	}
}