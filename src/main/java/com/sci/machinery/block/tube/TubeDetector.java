package com.sci.machinery.block.tube;

import com.sci.machinery.SciMachinery;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TubeDetector extends TubeTransport
{
	private boolean lastIsEmpty;

	public TubeDetector()
	{
		setR(200);
		setG(200);
		setB(20);
		setA(150);
	}

	@Override
	public int getG(int a)
	{
		return this.g + (a == 1 ? 50 : 0);
	}

	@Override
	public int getR(int a)
	{
		return this.r + (a == 1 ? 50 : 0);
	}

	@Override
	public boolean isPowering()
	{
		return !lastIsEmpty;
	}

	@Override
	public void tick()
	{
		if(lastIsEmpty != items.isEmpty())
		{
			lastIsEmpty = items.isEmpty();

			getTile().worldObj.setBlockMetadataWithNotify(getTile().xCoord, getTile().yCoord, getTile().zCoord, isPowering() ? 1 : 0, 2);

			getTile().worldObj.notifyBlocksOfNeighborChange(getTile().xCoord, getTile().yCoord - 1, getTile().zCoord, SciMachinery.instance.detectorTube.blockID);
			getTile().worldObj.notifyBlocksOfNeighborChange(getTile().xCoord, getTile().yCoord + 1, getTile().zCoord, SciMachinery.instance.detectorTube.blockID);
			getTile().worldObj.notifyBlocksOfNeighborChange(getTile().xCoord - 1, getTile().yCoord, getTile().zCoord, SciMachinery.instance.detectorTube.blockID);
			getTile().worldObj.notifyBlocksOfNeighborChange(getTile().xCoord + 1, getTile().yCoord, getTile().zCoord, SciMachinery.instance.detectorTube.blockID);
			getTile().worldObj.notifyBlocksOfNeighborChange(getTile().xCoord, getTile().yCoord, getTile().zCoord - 1, SciMachinery.instance.detectorTube.blockID);
			getTile().worldObj.notifyBlocksOfNeighborChange(getTile().xCoord, getTile().yCoord, getTile().zCoord + 1, SciMachinery.instance.detectorTube.blockID);
			getTile().worldObj.notifyBlockChange(getTile().xCoord, getTile().yCoord, getTile().zCoord, SciMachinery.instance.detectorTube.blockID);
		}

		super.tick();
	}
}