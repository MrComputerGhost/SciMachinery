package com.sci.machinery.block.tube.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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

public class TubeNetwork
{
	private List<BlockCoord> nodes;

	public TubeNetwork()
	{
		this.nodes = new ArrayList<BlockCoord>();
	}

	public Queue<BlockCoord> findRoute(BlockCoord entryNode)
	{
		if(!nodes.contains(entryNode))
			throw new IllegalArgumentException("Node is not an element of this network! " + entryNode + " " + this);

		Map<Integer, Queue<BlockCoord>> possibleRoutes = new HashMap<Integer, Queue<BlockCoord>>();
		int lowestCostingRoute = -1;

		// TODO calculate all routes

		Iterator<Integer> keys = possibleRoutes.keySet().iterator();
		while(keys.hasNext())
		{
			int cost = keys.next();
			if(cost < lowestCostingRoute || lowestCostingRoute == -1)
				lowestCostingRoute = cost;
		}

		return possibleRoutes.get(lowestCostingRoute);
	}

	private List<BlockCoord> getAdjacentNodes(BlockCoord node)
	{
		BlockCoord[] adjacent = node.getAdjacent();
		List<BlockCoord> adjacentNodes = new ArrayList<BlockCoord>();

		for(BlockCoord adj : adjacent)
		{
			if(this.nodes.contains(adj))
				adjacentNodes.add(adj);
		}

		return adjacentNodes;
	}

	private BlockCoord getAdjacentDestination(World world, BlockCoord node)
	{
		for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity adjTE = Utils.getTileEntity(world, node.offset(fd));
			if(adjTE instanceof TileTube)
			{
				TileTube tube = (TileTube) adjTE;
				if(tube.canAcceptItems() && tube.canConnectTube(Utils.getTileEntity(world, node)) || adjTE instanceof IInventory) { return node.offset(fd); }
			}
		}

		return null;
	}

	public List<BlockCoord> getNodes()
	{
		return this.nodes;
	}

	public void addNode(BlockCoord node)
	{
		this.nodes.add(node);
	}

	public void removeNode(BlockCoord node)
	{
		this.nodes.remove(node);
	}
}