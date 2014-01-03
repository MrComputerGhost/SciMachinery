package com.sci.machinery.block.tube.route;

import java.util.HashMap;
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

		// TODO calculate costs

		// TODO routing.. :P

		return null;
	}
}