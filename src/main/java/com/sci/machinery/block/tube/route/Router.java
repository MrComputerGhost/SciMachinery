package com.sci.machinery.block.tube.route;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

public class Router
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
		Map<ForgeDirection, Integer> costs = new HashMap<ForgeDirection, Integer>();
		BlockCoord current = Utils.blockCoord(tube);

		for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS)
		{
			costs.put(fd, findCost(current, fd, -1));
		}

		int lowest = 0;
		ForgeDirection next = ForgeDirection.UNKNOWN;
		Iterator<ForgeDirection> it = costs.keySet().iterator();
		while(it.hasNext())
		{
			ForgeDirection dir = it.next();
			int cost = costs.get(dir);
			if(cost < lowest)
			{
				lowest = cost;
				next = dir;
			}
		}

		if(lowest == -1 || next == ForgeDirection.UNKNOWN)
			return null;

		return new BlockCoord(tube.xCoord + next.offsetX, tube.yCoord + next.offsetY, tube.zCoord + next.offsetZ);
	}

	private int findCost(BlockCoord current, ForgeDirection fd, int lastCost)
	{
		BlockCoord offs = new BlockCoord(current.getX() + fd.offsetX, current.getY() + fd.offsetY, current.getZ() + fd.offsetZ);
		if(!(world.getBlockTileEntity(offs.getX(), offs.getY(), offs.getZ()) instanceof TileTube))
			return lastCost;

		Map<BlockCoord, Integer> adjacentCosts = new HashMap<BlockCoord, Integer>();
		for(ForgeDirection afd : ForgeDirection.VALID_DIRECTIONS)
		{
			BlockCoord a = new BlockCoord(offs.getX() + afd.offsetX, offs.getY() + afd.offsetY, offs.getZ() + afd.offsetZ);
			if(!current.equals(a) && (world.getBlockTileEntity(a.getX(), a.getY(), a.getZ()) instanceof TileTube))
			{
				adjacentCosts.put(a, findCost(offs, afd, lastCost + 1));
			}
		}
		
		int lowest = -1;
		Iterator<BlockCoord> keys = adjacentCosts.keySet().iterator();
		while(keys.hasNext())
		{
			BlockCoord key = keys.next();
			if(adjacentCosts.get(key) < lowest)
				lowest = adjacentCosts.get(key);
		}

		return lowest;
	}
}