package com.sci.machinery.block.tube.network;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import com.sci.machinery.core.BlockCoord;

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
		
		Queue<BlockCoord> route = new LinkedList<BlockCoord>();
		
		
		
		return route;
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