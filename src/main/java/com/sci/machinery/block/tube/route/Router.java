package com.sci.machinery.block.tube.route;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.BlockCoord;

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

		for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS)
		{
			costs.put(fd, findCost(fd));
		}

		int lowest = 0;
		ForgeDirection next = ForgeDirection.UNKNOWN;
		Iterator<ForgeDirection> it = costs.keySet().iterator();
		while(it.hasNext())
		{
			ForgeDirection dir = it.next();
			int cost = costs.get(dir);
			if(cost < lowest)
				lowest = cost;
		}

		if(lowest == -1 || next == ForgeDirection.UNKNOWN)
			return null;

		return new BlockCoord(tube.xCoord + next.offsetX, tube.yCoord + next.offsetY, tube.zCoord + next.offsetZ);
	}

	private int findCost(ForgeDirection fd)
	{

		return -1;
	}
}