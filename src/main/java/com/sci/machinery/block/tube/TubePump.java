package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TubePump extends TubeBase
{
	public TubePump()
	{
		setR(200);
		setG(20);
		setB(20);
		setA(150);
	}

	@Override
	public void tick()
	{
		//TODO
	}
	
	@Override
	public boolean canAcceptItems()
	{
		return false;
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			TileTube tube = (TileTube) e;
			return !(tube.getTube() instanceof TubePump);
		}
		return super.canConnectTube(e);
	}
}