package com.sci.machinery.block.tube;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

public class TubeTransport extends TubeBase
{
	private int findSide(BlockCoord base, BlockCoord side)
	{
		BlockCoord[] adjacent = base.getAdjacent();
		for(int i = 0; i < adjacent.length; i++)
			if(adjacent[i].equals(side))
				return i;
		return -1;
	}

	@Override
	public void tick()
	{
		if(!this.items.isEmpty())
		{
			TravellingItem item = this.items.remove(0);
			BlockCoord next = this.router.route();
			if(next == null)
			{
				tile.worldObj.spawnEntityInWorld(new EntityItem(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, item.getStack()));
				return;
			}
			TileEntity nextTE = Utils.getTileEntity(this.tile.worldObj, next);
			if(nextTE instanceof ITubeConnectable)
			{
				ITubeConnectable tube = (TileTube) Utils.getTileEntity(this.tile.worldObj, next);
				if(tube.canAcceptItems())
				{
					tube.addItem(item, this.tile);
				}
			}
			else if(nextTE instanceof ISidedInventory)
			{
				int side = findSide(Utils.blockCoord(tile), next);
				if(side != -1)
				{
					side = ForgeDirection.OPPOSITES[side];
					ISidedInventory inv = (ISidedInventory) nextTE;
					int[] slots = inv.getAccessibleSlotsFromSide(side);

					for(int j = 0; j < slots.length; j++)
					{
						if(inv.canInsertItem(slots[j], item.getStack(), side))
						{
							ItemStack remaining = TileEntityHopper.insertStack(inv, item.getStack(), side);
							if(remaining != null)
							{
								item.setStack(remaining);
								this.addItem(item, nextTE);
							}
						}
					}
				}
			}
			else if(Utils.getTileEntity(this.tile.worldObj, next) instanceof IInventory)
			{
				int side = findSide(Utils.blockCoord(tile), next);
				if(side != -1)
				{
					side = ForgeDirection.OPPOSITES[side];
					IInventory inv = (IInventory) nextTE;
					ItemStack remaining = TileEntityHopper.insertStack(inv, item.getStack(), side);
					if(remaining != null)
					{
						item.setStack(remaining);
						this.addItem(item, tile);
					}
				}
			}
		}
	}
}