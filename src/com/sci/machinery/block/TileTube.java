package com.sci.machinery.block;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.tube.ITubeConnectable;
import com.sci.machinery.block.tube.TravellingItem;
import com.sci.machinery.block.tube.Tube;
import com.sci.machinery.block.tube.TubeDetector;
import com.sci.machinery.block.tube.TubeNormal;
import com.sci.machinery.block.tube.TubePump;

public class TileTube extends TileEntity implements ITubeConnectable
{
	private Tube tube;

	public TileTube()
	{
	}

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
		switch (tag.getInteger("tubeID"))
		{
		case 0:
		{
			this.tube = new TubeNormal();
			break;
		}
		case 1:
		{
			this.tube = new TubePump();
			break;
		}
		case 2:
		{
			this.tube = new TubeDetector();
			break;
		}
		default:
		{
			break;
		}
		}
		tube.readFromNBT(tag);
	}

	private int getTubeID(Tube tube)
	{
		if(tube instanceof TubeNormal)
			return 0;
		else if(tube instanceof TubePump)
			return 1;
		else if(tube instanceof TubeDetector)
			return 2;
		return -1;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("tubeID", getTubeID(tube));
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