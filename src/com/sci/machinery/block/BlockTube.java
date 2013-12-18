package com.sci.machinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.block.tube.TubeBase;
import com.sci.machinery.block.tube.TubeModifier;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.BlockSci;
import com.sci.machinery.core.Utils;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class BlockTube extends BlockSci
{
	private TubeModifier modifier;
	private Class<? extends TubeBase> tubeClazz;

	public BlockTube(int id, Class<? extends TubeBase> tubeClazz)
	{
		this(id, tubeClazz, null);
	}

	public BlockTube(int id, Class<? extends TubeBase> tubeClazz, TubeModifier modifier)
	{
		super(id, Material.iron);
		this.tubeClazz = tubeClazz;
		this.modifier = modifier;
		this.setCreativeTab(SciMachinery.tab);
		this.setHardness(0.7F);
		this.setStepSound(Block.soundMetalFootstep);
		this.setUnlocalizedName("tube");
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntity t = par1World.getBlockTileEntity(par2, par3, par4);
		if(t instanceof TileTube)
		{
			((TileTube) t).breakTube();
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		try
		{
			TileTube t = new TileTube(tubeClazz.newInstance());
			if(modifier != null)
			{
				modifier.modifyTube(t.getTube());
			}
			return t;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) == 1 ? 6 : 3;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		TileEntity t = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
		if(t != null && t instanceof TileTube) { return ((TileTube) t).isPowering() ? 15 : 0; }
		return 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return isProvidingStrongPower(par1IBlockAccess, par2, par3, par4, par5);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileTube)
		{
			TileTube tube = (TileTube) te;
			TileEntity[] t = Utils.getAdjacentTiles(world, new BlockCoord(x, y, z));

			float minX = 0.4f;
			float minY = 0.4f;
			float minZ = 0.4f;
			float maxX = 0.6f;
			float maxY = 0.6f;
			float maxZ = 0.6f;

			if(tube.canConnectTube(t[0]))
			{
				minY = 0.0f;
			}
			if(tube.canConnectTube(t[1]))
			{
				maxY = 1.0f;
			}
			if(tube.canConnectTube(t[2]))
			{
				minZ = 0.0f;
			}
			if(tube.canConnectTube(t[3]))
			{
				maxZ = 1.0f;
			}
			if(tube.canConnectTube(t[4]))
			{
				minX = 0.0f;
			}
			if(tube.canConnectTube(t[5]))
			{
				maxX = 1.0f;
			}

			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}
}