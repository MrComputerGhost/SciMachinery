package com.sci.machinery.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDetectorTube extends BlockTube
{
	public BlockDetectorTube(int id)
	{
		super(id);
		this.setUnlocalizedName("sm.detectortube");
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		TileEntity t = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
		if(t != null && t instanceof TileDetectorTube) { return ((TileDetectorTube) t).isPowering() ? 15 : 0; }
		return 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return isProvidingStrongPower(par1IBlockAccess, par2, par3, par4, par5);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileDetectorTube();
	}
}