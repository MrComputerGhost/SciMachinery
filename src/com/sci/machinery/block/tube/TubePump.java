package com.sci.machinery.block.tube;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

public class TubePump extends TubeNormal
{
	private int timer;

	@Override
	public void update()
	{
		timer++;
		if(timer == speed.delay)
		{
			timer = 0;

			BlockCoord[] adjacent = Utils.blockCoord(tile).getAdjacent();
			for(int i = 0; i < adjacent.length; i++)
			{
				BlockCoord pos = adjacent[i];
				TileEntity tile = this.tile.worldObj.getBlockTileEntity(pos.getX(), pos.getY(), pos.getZ());
				if(tile != null)
				{
					if(tile instanceof ISidedInventory)
					{
						ISidedInventory inv = (ISidedInventory) tile;
						int[] slots = inv.getAccessibleSlotsFromSide(ForgeDirection.OPPOSITES[i]);

						for(int j = 0; j < slots.length; j++)
						{
							ItemStack stack = inv.getStackInSlot(slots[j]);
							if(stack != null && inv.canExtractItem(j, stack, ForgeDirection.OPPOSITES[i]))
							{
								inv.decrStackSize(slots[j], stack.stackSize);
								this.addItem(new TravellingItem(stack), tile);
								break;
							}
						}
					}
					else if(tile instanceof IInventory)
					{
						IInventory inv = (IInventory) tile;

						for(int j = 0; j < inv.getSizeInventory(); j++)
						{
							ItemStack stack = inv.getStackInSlot(j);
							if(stack != null)
							{
								inv.decrStackSize(j, stack.stackSize);
								this.addItem(new TravellingItem(stack), tile);
								break;
							}
						}
					}
				}
			}

			if(!items.isEmpty())
			{
				TravellingItem item = items.remove(0);
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
				items.add(item);
			}
		}
	}
	
	@Override
	public boolean canAcceptItems()
	{
		return false;
	}
}