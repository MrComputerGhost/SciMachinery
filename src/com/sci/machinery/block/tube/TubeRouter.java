package com.sci.machinery.block.tube;

import java.util.List;
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
				if(noneEqual(adjacent[i], item.getLastCoord()))
				{
					ret = adjacent[i];
					break;
				}
			}
		}

		if(ret == null)
		{
			for(int i = 0; i < adjacent.length; i++)
			{
				TileEntity te = Utils.getTileEntity(tube.worldObj, adjacent[i]);
				if(te instanceof ITubeConnectable && ((ITubeConnectable) te).canAcceptItems() && ((ITubeConnectable) te).canConnectTube(tube))
				{
					if(noneEqual(adjacent[i], item.getLastCoord()))
					{
						ret = adjacent[i];
						break;
					}
				}
			}
		}

		return ret;
	}

	private static boolean noneEqual(BlockCoord o, List<BlockCoord> e)
	{
		for(BlockCoord ob : e)
		{
			if(ob.equals(o))
				return false;
		}
		return true;
	}
}