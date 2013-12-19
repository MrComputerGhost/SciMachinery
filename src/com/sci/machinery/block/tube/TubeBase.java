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

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public abstract class TubeBase implements ITubeConnectable
{
	public static final Class<? extends TubeBase> COBBLE = TubeCobble.class;
	public static final Class<? extends TubeBase> DETECTOR = TubeDetector.class;
	public static final Class<? extends TubeBase> PUMP = TubePump.class;
	public static final Class<? extends TubeBase> STONE = TubeStone.class;
	public static final Class<? extends TubeBase> VOID = TubeVoid.class;
	public static final Class<? extends TubeBase> VALVE = TubeValve.class;

	protected List<TravellingItem> items;

	protected Speed speed = Speed.MEDIUM;
	private TileTube tile;
	private int timer;

	protected int r, g, b, a = 150;

	public TubeBase()
	{
		this.items = new ArrayList<TravellingItem>();
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

	public void breakTube()
	{
		while(!items.isEmpty())
		{
			getTile().worldObj.spawnEntityInWorld(new EntityItem(getTile().worldObj, getTile().xCoord, getTile().yCoord, getTile().zCoord, items.remove(0).getStack()));
		}
	}

	@Override
	public boolean canAcceptItems()
	{
		return true;
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			if(((TileTube) e).getTube() instanceof TubeValve)
				return ((TubeValve) ((TileTube) e).getTube()).canConnectTube(e);
		}
		return e instanceof ITubeConnectable || e instanceof IInventory;
	}

	private int findSide(BlockCoord base, BlockCoord side)
	{
		BlockCoord[] adjacent = base.getAdjacent();
		for(int i = 0; i < adjacent.length; i++)
			if(adjacent[i].equals(side))
				return i;
		return -1;
	}

	public int getR(int a)
	{
		return r;
	}

	public void setR(int r)
	{
		this.r = r;
	}

	public int getG(int a)
	{
		return g;
	}

	public void setG(int g)
	{
		this.g = g;
	}

	public int getB(int a)
	{
		return b;
	}

	public void setB(int b)
	{
		this.b = b;
	}

	public int getA(int a)
	{
		return this.a;
	}

	public void setA(int a)
	{
		this.a = a;
	}

	public List<TravellingItem> getItems()
	{
		return items;
	}

	public final TileTube getTile()
	{
		return tile;
	}

	public void invalidate()
	{

	}

	public boolean isPowering()
	{
		return false;
	}

	public final boolean isValid()
	{
		return tile != null;
	}

	public void readFromNBT(NBTTagCompound tag)
	{

	}

	@Override
	public void removeItem(int index)
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && !items.isEmpty())
		{
			items.remove(index);
		}
	}

	public void setSpeed(Speed fast)
	{
		this.speed = fast;
	}

	public final void setTile(TileTube tile)
	{
		this.tile = tile;
	}

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

	public void validate()
	{

	}

	public void writeToNBT(NBTTagCompound tag)
	{

	}

	public boolean isPowered()
	{
		return getTile().isPowered();
	}
}