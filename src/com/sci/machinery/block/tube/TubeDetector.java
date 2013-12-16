package com.sci.machinery.block.tube;

import com.sci.machinery.SciMachinery;

public class TubeDetector extends TubeNormal
{
	private boolean lastIsEmpty;

	@Override
	public void update()
	{
		if(!isValid())
			return;
		
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

		super.update();
	}
	
	@Override
	public boolean isPowering()
	{
		return !lastIsEmpty;
	}
}