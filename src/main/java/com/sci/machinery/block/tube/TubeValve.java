package com.sci.machinery.block.tube;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TubeValve extends TubeTransport
{
	public TubeValve()
	{
		setR(20);
		setG(20);
		setB(20);
		setA(150);
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(isPowered())
		{
			if(e instanceof ITubeConnectable)
			{
				return true;
			}
			else if(e instanceof IInventory) { return true; }
		}
		return false;
	}
}