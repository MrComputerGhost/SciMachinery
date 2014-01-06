package com.sci.machinery.block.tube.route;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.inventory.IInventory;
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
		BlockCoord offs = current.offset(fd);
		if(!((world.getBlockTileEntity(offs.getX(), offs.getY(), offs.getZ()) instanceof TileTube) || (world.getBlockTileEntity(offs.getX(), offs.getY(), offs.getZ()) instanceof IInventory)))
			return lastCost;

		int lowest = -1;
		for(ForgeDirection afd : ForgeDirection.VALID_DIRECTIONS)
		{
			BlockCoord a = new BlockCoord(offs.getX() + afd.offsetX, offs.getY() + afd.offsetY, offs.getZ() + afd.offsetZ);
			if(!current.equals(a) && (Utils.getTileEntity(world, a) instanceof TileTube))
			{
				int cost = findCost(offs, afd, lastCost + 1);
				if(cost < lowest)
					lowest = cost;
			}
		}

		return lowest == -1 ? lastCost : lowest;
	}
}