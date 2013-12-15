package com.sci.machinery.block;

import com.sci.machinery.SciMachinery;

public class TileDetectorTube extends TileTube
{
	private boolean lastIsEmpty;
	
	@Override
	public void updateEntity()
	{
		if(lastIsEmpty != items.isEmpty())
		{
			lastIsEmpty = items.isEmpty();
			
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, SciMachinery.instance.detectorTube.blockID);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord + 1, zCoord, SciMachinery.instance.detectorTube.blockID);
			worldObj.notifyBlocksOfNeighborChange(xCoord - 1, yCoord, zCoord, SciMachinery.instance.detectorTube.blockID);
			worldObj.notifyBlocksOfNeighborChange(xCoord + 1, yCoord, zCoord, SciMachinery.instance.detectorTube.blockID);
            worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord - 1, SciMachinery.instance.detectorTube.blockID);
            worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord + 1, SciMachinery.instance.detectorTube.blockID);
            worldObj.notifyBlockChange(xCoord,  yCoord,  zCoord,  SciMachinery.instance.detectorTube.blockID);
		}
		
		super.updateEntity();
	}
	
	public boolean isPowering()
	{
		return !items.isEmpty();
	}
}