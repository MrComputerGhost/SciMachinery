package com.sci.machinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.core.BlockSciContainer;

public class BlockTube extends BlockSciContainer
{
	public BlockTube(int id)
	{
		super(id, Material.iron);
		this.setCreativeTab(SciMachinery.tab);
		this.setHardness(0.7F);
		this.setStepSound(Block.soundMetalFootstep);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileTube();
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		TileTube tube = (TileTube) world.getBlockTileEntity(x, y, z);
		TileEntity[] t = tube.getAdjacentTiles(world, x, y, z);
		float minX = 0.4f;
		float minY = 0.4f;
		float minZ = 0.4f;
		float maxX = 0.6f;
		float maxY = 0.6f;
		float maxZ = 0.6f;

		if(t[0] != null && t[0] instanceof TileTube)
		{
			
		}
		else if(t[1] != null && t[1] instanceof TileTube)
		{
		}
		else if(t[2] != null && t[2] instanceof TileTube)
		{
		}
		else if(t[3] != null && t[3] instanceof TileTube)
		{
		}
		else if(t[4] != null && t[4] instanceof TileTube)
		{

		}
		else if(t[5] != null && t[5] instanceof TileTube)
		{
		}

		this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
