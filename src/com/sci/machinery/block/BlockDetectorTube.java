package com.sci.machinery.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDetectorTube extends BlockTube
{
	public BlockDetectorTube(int id)
	{
		super(id);
		this.setUnlocalizedName("detectorTube");
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

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		TileEntity t = par1World.getBlockTileEntity(par2, par3, par4);
		if(t != null && t instanceof TileDetectorTube)
		{
			if(((TileDetectorTube) t).isPowering())
			{
				int l = par1World.getBlockMetadata(par2, par3, par4);
				double d0 = (double) ((float) par2 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.2D;
				double d1 = (double) ((float) par3 + 0.7F) + (double) (par5Random.nextFloat() - 0.5F) * 0.2D;
				double d2 = (double) ((float) par4 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.2D;
				double d3 = 0.2199999988079071D;
				double d4 = 0.27000001072883606D;

				if(l == 1)
				{
					par1World.spawnParticle("reddust", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
				}
				else if(l == 2)
				{
					par1World.spawnParticle("reddust", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
				}
				else if(l == 3)
				{
					par1World.spawnParticle("reddust", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
				}
				else if(l == 4)
				{
					par1World.spawnParticle("reddust", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
				}
				else
				{
					par1World.spawnParticle("reddust", d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) == 1 ? 3 : 0;
	}
}