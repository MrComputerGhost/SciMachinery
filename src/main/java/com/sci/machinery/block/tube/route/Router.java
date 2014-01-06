package com.sci.machinery.block.tube.route;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

public final class Router
{
	protected World world;
	protected TileTube tube;

	public Router(World world, TileTube tube)
	{
		this.world = world;
		this.tube = tube;
	}

	public BlockCoord route()
	{
		BlockCoord current = Utils.blockCoord(tube);

		int lowest = -1;
		ForgeDirection next = ForgeDirection.UNKNOWN;
		for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS)
		{
			int cost = findCost(current, fd, 0);
			if(cost < lowest)
				lowest = cost;
		}

		if(next == ForgeDirection.UNKNOWN)
			return null;

		return current.offset(next);
	}

	private int findCost(BlockCoord current, ForgeDirection fd, int lastCost)
	{
		int ret = lastCost;
		BlockCoord newCurrent = current.offset(fd);
		TileEntity tile = Utils.getTileEntity(world, newCurrent);

		if(tile instanceof TileTube || tile instanceof IInventory)
		{
			for(ForgeDirection afd : ForgeDirection.VALID_DIRECTIONS)
			{
				BlockCoord adj = newCurrent.offset(afd);
				TileEntity adjTile = Utils.getTileEntity(world, adj);
				if(!adj.equals(current) && (adjTile instanceof TileTube || adjTile instanceof IInventory))
				{
					int cost = findCost(newCurrent, afd, ret + 1);
					if(cost < ret) //this "is" wrong ... i think. my brain cant figure it out atm :/
					{
						ret = cost;
					}
				}
			}
		}

		return ret;
	}
}