package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;

public class TubeCobble extends TubeTransport
{
	public TubeCobble()
	{
		setR(125);
		setG(125);
		setB(125);
		setA(150);
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			TileTube tube = (TileTube) e;
			if(tube.getTube() instanceof TubeStone) { return false; }
		}
		return super.canConnectTube(e);
	}
}