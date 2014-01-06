package com.sci.machinery.block.tube;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TubePump extends TubeTransport
{
	private boolean doInput;

	public TubePump()
	{
		setR(200);
		setG(20);
		setB(20);
		setA(150);
	}

	@Override
	public void tick()
	{
		doInput = !doInput;
		if(doInput)
		{
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
		}
		else
		{
			super.tick();
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