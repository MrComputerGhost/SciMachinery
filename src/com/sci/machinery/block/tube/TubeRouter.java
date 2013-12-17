package com.sci.machinery.block.tube;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

public final class TubeRouter
{
	private TubeRouter()
	{
	}

	public static BlockCoord route(TileTube tube, TravellingItem item)
	{
		BlockCoord ret = null;
		BlockCoord[] adjacent = Utils.blockCoord(tube).getAdjacent();

		for(int i = 0; i < adjacent.length; i++)
		{
			TileEntity te = Utils.getTileEntity(tube.worldObj, adjacent[i]);
			if(te instanceof IInventory)
			{
				ret = adjacent[i];
				break;
			}
		}

		if(ret == null)
		{
			for(int i = 0; i < adjacent.length; i++)
			{
				TileEntity te = Utils.getTileEntity(tube.worldObj, adjacent[i]);
				if(te instanceof ITubeConnectable && ((ITubeConnectable) te).canAcceptItems())
				{
					if(!adjacent[i].equals(item.getLastCoord()))
					{
						ret = adjacent[i];
						break;
					}
				}
			}
		}

		return ret;
	}
}