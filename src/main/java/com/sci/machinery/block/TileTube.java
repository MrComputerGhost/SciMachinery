package com.sci.machinery.block;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.tube.ITubeConnectable;
import com.sci.machinery.block.tube.TravellingItem;
import com.sci.machinery.block.tube.TubeBase;
import com.sci.machinery.block.tube.TubeCobble;
import com.sci.machinery.block.tube.TubeDetector;
import com.sci.machinery.block.tube.TubePump;
import com.sci.machinery.block.tube.TubeStone;
import com.sci.machinery.block.tube.TubeValve;
import com.sci.machinery.block.tube.TubeVoid;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileTube extends TileEntity implements ITubeConnectable
{
	private TubeBase tube;

	public TileTube()
	{
	}

	public TileTube(TubeBase tube)
	{
		this.tube = tube;
		tube.setTile(this);
	}

	@Override
	public void addItem(TravellingItem item, TileEntity entity)
	{
		tube.addItem(item, entity);
	}

	public void breakTube()
	{
		tube.breakTube();
	}

	@Override
	public boolean canAcceptItems()
	{
		return tube.canAcceptItems();
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		return tube == null ? false : tube.canConnectTube(e);
	}

	public List<TravellingItem> getItems()
	{
		return tube.getItems();
	}

	public TubeBase getTube()
	{
		return tube;
	}

	private int getTubeID(TubeBase tube)
	{
		if(tube instanceof TubePump)
			return 2;
		else if(tube instanceof TubeDetector)
			return 3;
		else if(tube instanceof TubeStone)
			return 0;
		else if(tube instanceof TubeCobble)
			return 1;
		else if(tube instanceof TubeVoid)
			return 4;
		else if(tube instanceof TubeValve)
			return 5;
		return -1;
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		tube.invalidate();
	}

	public boolean isPowering()
	{
		return tube.isPowering();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		switch (tag.getInteger("tubeID"))
		{
		case 0:
		{
			this.tube = new TubeStone();
			break;
		}
		case 1:
		{
			this.tube = new TubeCobble();
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
		case 5:
		{
			this.tube = new TubeValve();
			break;
		}
		default:
		{
			break;
		}
		}
		tube.readFromNBT(tag);

		NBTTagList list = tag.getTagList("Items");
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound item = (NBTTagCompound) list.tagAt(i);
			this.getTube().addItem(new TravellingItem(ItemStack.loadItemStackFromNBT(item)), Utils.getTileEntity(worldObj, BlockCoord.fromNBT(tag.getTagList("lastCoord"))));
		}
	}

	@Override
	public void removeItem(int index)
	{
		tube.removeItem(index);
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
	public void validate()
	{
		super.validate();
		tube.validate();
	}

	@Override
	public void writeToNBT(NBTTagCompound root)
	{
		super.writeToNBT(root);
		root.setInteger("tubeID", getTubeID(tube));
		tube.writeToNBT(root);

		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.getTube().getItems().size(); i++)
		{
			NBTTagCompound tag = new NBTTagCompound();
			this.getTube().getItems().get(i).getStack().writeToNBT(tag);
			list.appendTag(tag);
		}
		root.setTag("Items", list);

		NBTTagList lcl = new NBTTagList();
		BlockCoord c = Utils.blockCoord(this);
		c.writeToNBT(lcl);
		root.setTag("lastCoord", lcl);
	}

	public boolean isPowered()
	{
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
}