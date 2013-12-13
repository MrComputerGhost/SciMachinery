package com.sci.machinery.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.IBlockAccess;
import com.sci.machinery.core.TileSci;
import com.sci.machinery.core.TravellingItem;

public class TileTube extends TileSci
{
	private List<TravellingItem> items;
	private int timer;

	public TileTube()
	{
		items = new ArrayList<TravellingItem>();
	}

	public List<TravellingItem> getItems()
	{
		return items;
	}

	@Override
	public void updateEntity()
	{
		timer++;
		if(timer == 10)
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
							if(t[i] instanceof TileTube)
							{
								if(i != items.get(0).getLastDir() || items.get(0).getLastDir() == -1)
								{
									items.get(0).setLastDir(reverse(i));
									((TileTube) t[i]).addItem(items.remove(0));
									sent = false;
								}
							}
						}
					}
					if(!sent && !items.isEmpty())
					{
						if(!this.worldObj.isRemote)
							this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.get(0).getStack()));
						items.remove(0);
					}
				}
			}
		}
	}

	private int reverse(int i)
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

	private boolean allNull(TileEntity[] t)
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
}