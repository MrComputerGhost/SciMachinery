package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TubeStone extends TubeBase
{
	public TubeStone()
	{
		setR(200);
		setG(200);
		setB(200);
		setA(150);
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			TileTube tube = (TileTube) e;
			if(tube.getTube() instanceof TubeCobble) { return false; }
		}
		return super.canConnectTube(e);
	}
}