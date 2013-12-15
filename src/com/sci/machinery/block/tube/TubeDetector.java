package com.sci.machinery.block.tube;

import java.util.List;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.block.TileTube;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TubeDetector extends TubeNormal
{
	private boolean lastIsEmpty;

	public TubeDetector(TileTube tile)
	{
		super(tile);
	}

	@Override
	public void update()
	{
		if(lastIsEmpty != items.isEmpty())
		{
			lastIsEmpty = items.isEmpty();

			tile.worldObj.setBlockMetadataWithNotify(tile.xCoord, tile.yCoord, tile.zCoord, isPowering() ? 1 : 0, 2);

			tile.worldObj.notifyBlocksOfNeighborChange(tile.xCoord, tile.yCoord - 1, tile.zCoord, SciMachinery.instance.detectorTube.blockID);
			tile.worldObj.notifyBlocksOfNeighborChange(tile.xCoord, tile.yCoord + 1, tile.zCoord, SciMachinery.instance.detectorTube.blockID);
			tile.worldObj.notifyBlocksOfNeighborChange(tile.xCoord - 1, tile.yCoord, tile.zCoord, SciMachinery.instance.detectorTube.blockID);
			tile.worldObj.notifyBlocksOfNeighborChange(tile.xCoord + 1, tile.yCoord, tile.zCoord, SciMachinery.instance.detectorTube.blockID);
			tile.worldObj.notifyBlocksOfNeighborChange(tile.xCoord, tile.yCoord, tile.zCoord - 1, SciMachinery.instance.detectorTube.blockID);
			tile.worldObj.notifyBlocksOfNeighborChange(tile.xCoord, tile.yCoord, tile.zCoord + 1, SciMachinery.instance.detectorTube.blockID);
			tile.worldObj.notifyBlockChange(tile.xCoord, tile.yCoord, tile.zCoord, SciMachinery.instance.detectorTube.blockID);
		}

		super.update();
	}
	
	@Override
	public boolean isPowering()
	{
		return !lastIsEmpty;
	}
}