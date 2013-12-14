package com.sci.machinery.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPumpTube extends BlockTube
{
	public BlockPumpTube(int id)
	{
		super(id);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TilePumpTube();
	}
}