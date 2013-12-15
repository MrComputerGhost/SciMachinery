package com.sci.machinery.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.IBlockAccess;
import com.sci.machinery.core.ITubeConnectable;
import com.sci.machinery.core.Speed;
import com.sci.machinery.core.TravellingItem;
import com.sci.machinery.network.PacketAddItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileTube extends TileEntity implements ITubeConnectable
{
	protected List<TravellingItem> items;
	private int timer;
	protected Speed speed;

	public TileTube()
	{
		this.speed = Speed.MEDIUM;
		this.items = new ArrayList<TravellingItem>();
	}

	public List<TravellingItem> getItems()
	{
		return items;
	}

	@Override
	public void updateEntity()
	{
		if(!items.isEmpty())
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
						this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.get(0).getStack()));
						items.remove(0);
					}
					else
					{
						for(int i = 0; i < t.length; i++)
						{
							if(!items.isEmpty())
							{
								if(t[i] instanceof IInventory)
								{
									ItemStack s = TileEntityHopper.insertStack((IInventory) t[i], items.remove(0).getStack(), 0);
									if(s != null)
									{
										if(!this.worldObj.isRemote)
											this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, s));
									}
								}
							}
						}

						boolean sent = true;
						for(int i = 0; i < t.length; i++)
						{
							if(!items.isEmpty())
							{
								if(t[i] instanceof ITubeConnectable)
								{
									if(i != items.get(0).getLastDir() || items.get(0).getLastDir() == -1 && ((ITubeConnectable) t[i]).canAcceptItems())
									{
										items.get(0).setLastDir(reverse(i));
										((ITubeConnectable) t[i]).addItem(items.remove(0));
										sent = false;
									}
								}
							}
						}
						if(!sent && !items.isEmpty())
						{
							this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.get(0).getStack()));
							items.remove(0);
						}
					}
				}
			}
		}
		else
		{
			timer = 0;
		}
	}

	public Speed getSpeed()
	{
		return speed;
	}

	public void setSpeed(Speed speed)
	{
		this.speed = speed;
	}

	protected int reverse(int i)
	{
		switch (i)
		{
		case 0:
		{
			return 1;
		}
		case 1:
		{
			return 0;
		}
		case 2:
		{
			return 3;
		}
		case 3:
		{
			return 2;
		}
		case 4:
		{
			return 5;
		}
		case 5:
		{
			return 4;
		}
		}
		return i;
	}

	protected boolean allNull(TileEntity[] t)
	{
		boolean ret = true;
		for(TileEntity te : t)
			if(te != null)
				ret = false;
		return ret;
	}

	public TileEntity[] getAdjacentTiles(IBlockAccess world, int x, int y, int z)
	{
		TileEntity[] t = new TileEntity[6];
		t[4] = world.getBlockTileEntity(x + 1, y, z);
		t[5] = world.getBlockTileEntity(x - 1, y, z);
		t[1] = world.getBlockTileEntity(x, y + 1, z);
		t[0] = world.getBlockTileEntity(x, y - 1, z);
		t[2] = world.getBlockTileEntity(x, y, z + 1);
		t[3] = world.getBlockTileEntity(x, y, z - 1);
		return t;
	}

	public void addItem(TravellingItem travellingItem)
	{
		items.add(travellingItem);
	}

	@Override
	public boolean canAcceptItems()
	{
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		items.clear();

		for(int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			int j = nbttagcompound1.getInteger("Slot");

			this.addItem(new TravellingItem(ItemStack.loadItemStackFromNBT(nbttagcompound1)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.items.size(); ++i)
		{
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setInteger("Slot", i);
			this.items.get(i).getStack().writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
	}

	public void validate()
	{
		super.validate();
		for(TravellingItem item : items)
		{
			ItemStack stack = item.getStack();
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128, worldObj.provider.dimensionId, PacketTypeHandler.populatePacket(new PacketAddItem(xCoord, yCoord, zCoord, stack.itemID, stack.stackSize)));
		}
	}

	public void breakTube()
	{
		if(!worldObj.isRemote && !items.isEmpty())
		{
			while(!items.isEmpty())
			{
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.remove(0).getStack()));
			}
		}
	}
}