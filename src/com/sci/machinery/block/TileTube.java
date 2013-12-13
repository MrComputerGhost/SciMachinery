package com.sci.machinery.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.core.TileSci;

public class TileTube extends TileSci
{
	@Override
	public void updateEntity()
	{
	}
	
	public TileEntity[] getAdjacentTiles(IBlockAccess world, int x, int y, int z)
	{
		TileEntity[] t = new TileEntity[6];
		t[4] = world.getBlockTileEntity(x + 1, y, z);
		t[5] = world.getBlockTileEntity(x - 1, y, z);
		t[1] = world.getBlockTileEntity(x, y + 1, z);
		t[0] = world.getBlockTileEntity(x, y - 1, z);
		t[2] = world.getBlockTileEntity(x, y, z + 1);
		t[3] = world.getBlockTileEntity(x, y, z - 1);
		return t;
	}
}