package com.sci.machinery.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class BlockCoord
{
	public static BlockCoord fromNBT(NBTTagList tagList)
	{
		BlockCoord ret = new BlockCoord();
		NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(0);
		ret.setX(tag.getInteger("x"));
		ret.setX(tag.getInteger("y"));
		ret.setX(tag.getInteger("z"));
		return ret;
	}

	private int x, y, z;

	public BlockCoord()
	{
		this(0, 0, 0);
	}

	public BlockCoord(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof BlockCoord)
		{
			BlockCoord c = (BlockCoord) obj;
			return c.x == this.x && c.y == this.y && c.z == this.z;
		}
		return false;
	}

	public BlockCoord[] getAdjacent()
	{
		BlockCoord[] ret = new BlockCoord[6];
		for(int i = 0; i < ret.length; i++)
		{
			ret[i] = new BlockCoord(x + ForgeDirection.getOrientation(i).offsetX, y + ForgeDirection.getOrientation(i).offsetY, z + ForgeDirection.getOrientation(i).offsetZ);
		}
		return ret;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getZ()
	{
		return z;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setZ(int z)
	{
		this.z = z;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("BlockCoord");
		sb.append('[');
		sb.append("x:");
		sb.append(x);
		sb.append(',');
		sb.append("y:");
		sb.append(',');
		sb.append(y);
		sb.append(',');
		sb.append("z:");
		sb.append(z);
		sb.append(']');

		return sb.toString();
	}

	public void writeToNBT(NBTTagList lcl)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		lcl.appendTag(tag);
	}
}