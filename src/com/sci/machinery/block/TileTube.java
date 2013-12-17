package com.sci.machinery.block;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.tube.ITubeConnectable;
import com.sci.machinery.block.tube.Material;
import com.sci.machinery.block.tube.TravellingItem;
import com.sci.machinery.block.tube.Tube;
import com.sci.machinery.block.tube.TubeDetector;
import com.sci.machinery.block.tube.TubeNormal;
import com.sci.machinery.block.tube.TubePump;
import com.sci.machinery.block.tube.TubeVoid;

/**
 * SciMachinery
 *
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

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
		if(!tube.isValid())
		{
			tube.setTile(this);
		}
		
		if(!isInvalid())
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
			this.tube = new TubeNormal(false);
			break;
		}
		case 1:
		{
			this.tube = new TubeNormal(true);
			break;
		}
		case 2:
		{
			this.tube = new TubePump();
			break;
		}
		case 3:
		{
			this.tube = new TubeDetector();
			break;
		}
		case 4:
		{
			this.tube = new TubeVoid();
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
		if(tube instanceof TubePump)
			return 2;
		else if(tube instanceof TubeDetector)
			return 3;
		else if(tube instanceof TubeNormal)
		{
			return ((TubeNormal)tube).getMaterial() == Material.STONE ? 0 : 1;
		}
		else if(tube instanceof TubeVoid)
			return 4;
		return -1;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("tubeID", getTubeID(tube));
		tube.writeToNBT(tag);
	}

	@Override	
	public void validate()
	{
		super.validate();
		tube.validate();
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		tube.invalidate();
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

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		return tube == null ? false : tube.canConnectTube(e);
	}
}