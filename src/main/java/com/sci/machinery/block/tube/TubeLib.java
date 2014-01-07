package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.block.tube.network.TubeNetwork;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

public final class TubeLib
{
	private TubeLib()
	{
	}

	public static TubeNetwork findNetwork(World world, TileTube tile)
	{
		TubeNetwork net = null;
		BlockCoord coord = Utils.blockCoord(tile);

		for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS)
		{
			BlockCoord adjCoord = coord.offset(fd);
			TileEntity adjTE = Utils.getTileEntity(world, adjCoord);
			if(adjTE instanceof TileTube)
			{
				net = ((TileTube)adjTE).getTube().getNetwork();
				break;
			}
		}

		if(net == null)
			net = new TubeNetwork();

		return net;
	}
}