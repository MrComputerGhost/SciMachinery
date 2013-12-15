package com.sci.machinery.core;

import net.minecraftforge.common.ForgeDirection;

public class BlockCoord
{
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

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return z;
	}

	public void setZ(int z)
	{
		this.z = z;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof BlockCoord)
		{
			BlockCoord c = (BlockCoord) obj;
			return c.x == x && c.y == y && c.z == z;
		}
		return super.equals(obj);
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

	public BlockCoord[] getAdjacent()
	{
		BlockCoord[] ret = new BlockCoord[6];
		for(int i = 0; i < ret.length; i++)
		{
			ret[i] = new BlockCoord(x + ForgeDirection.getOrientation(i).offsetX, y + ForgeDirection.getOrientation(i).offsetY, z + ForgeDirection.getOrientation(i).offsetZ);
		}
		return ret;
	}
}