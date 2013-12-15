package com.sci.machinery.block;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.tube.ITubeConnectable;
import com.sci.machinery.block.tube.TravellingItem;
import com.sci.machinery.block.tube.Tube;
import com.sci.machinery.core.BlockCoord;

public class TileTube extends TileEntity implements ITubeConnectable
{
	private Tube tube;

	public TileTube(Tube tube)
	{
		this.tube = tube;
		tube.setTile(this);
	}

	@Override
	public void updateEntity()
	{
		tube.update();
	}

	@Override
	public boolean canAcceptItems()
	{
		return tube.canAcceptItems();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		tube.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tube.writeToNBT(tag);
	}

	public void validate()
	{
		super.validate();
		tube.validate();
	}

	public void breakTube()
	{
		tube.breakTube();
	}

	@Override
	public void addItem(TravellingItem item, TileEntity entity)
	{
		tube.addItem(item, entity);
	}

	public Tube getTube()
	{
		return tube;
	}

	public List<TravellingItem> getItems()
	{
		return tube.getItems();
	}

	public boolean isPowering()
	{
		return tube.isPowering();
	}

	public void removeItem(int index)
	{
		tube.removeItem(index);
	}
}