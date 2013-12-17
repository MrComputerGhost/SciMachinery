package com.sci.machinery.core;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * SciMachinery
 *
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class Utils
{
	private Utils()
	{
	}

	public static BlockCoord blockCoord(TileEntity sender)
	{
		return new BlockCoord(sender.xCoord, sender.yCoord, sender.zCoord);
	}

	public static TileEntity[] getAdjacentTiles(IBlockAccess world, BlockCoord current)
	{
		int x = current.getX();
		int y = current.getY();
		int z = current.getZ();
		TileEntity[] t = new TileEntity[6];
		for(int i = 0; i < t.length; i++)
		{
			t[i] = world.getBlockTileEntity(x + ForgeDirection.getOrientation(i).offsetX, y + ForgeDirection.getOrientation(i).offsetY, z + ForgeDirection.getOrientation(i).offsetZ);
		}
		return t;
	}

	public static TileEntity getTileEntity(World worldObj, BlockCoord lastCoord)
	{
		return worldObj.getBlockTileEntity(lastCoord.getX(), lastCoord.getY(), lastCoord.getZ());
	}
}