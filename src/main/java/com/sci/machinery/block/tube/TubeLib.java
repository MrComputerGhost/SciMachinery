package com.sci.machinery.block.tube;

import java.util.ArrayList;
import java.util.List;
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
		BlockCoord coord = Utils.blockCoord(tile);

		List<TubeNetwork> adjNetworks = new ArrayList<TubeNetwork>();
		for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS)
		{
			BlockCoord adjCoord = coord.offset(fd);
			TileEntity adjTE = Utils.getTileEntity(world, adjCoord);
			if(adjTE instanceof TileTube)
			{
				if(((TileTube) adjTE).getTube().getNetwork() != null)
				{
					adjNetworks.add(((TileTube) adjTE).getTube().getNetwork());
				}
			}
		}

		TubeNetwork net = new TubeNetwork();

		for(TubeNetwork n : adjNetworks)
		{
			for(BlockCoord c : n.getNodes())
			{
				net.addNode(c);
				((TileTube) Utils.getTileEntity(tile.worldObj, c)).getTube().setNetwork(net);
			}
		}

		return net;
	}
}