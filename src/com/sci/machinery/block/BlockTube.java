package com.sci.machinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.core.ITubeConnectable;
import com.sci.machinery.core.TravellingItem;

public class BlockTube extends BlockContainer
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
		if(tube != null)
		{
			TileEntity[] t = tube.getAdjacentTiles(world, x, y, z);
			float minX = 0.4f;
			float minY = 0.4f;
			float minZ = 0.4f;
			float maxX = 0.6f;
			float maxY = 0.6f;
			float maxZ = 0.6f;

			if(t[0] != null && t[0] instanceof ITubeConnectable || t[0] instanceof IInventory)
			{
				minY = 0.0f;
			}
			if(t[1] != null && t[1] instanceof ITubeConnectable || t[1] instanceof IInventory)
			{
				maxY = 1.0f;
			}
			if(t[2] != null && t[2] instanceof ITubeConnectable || t[2] instanceof IInventory)
			{
				maxZ = 1.0f;
			}
			if(t[3] != null && t[3] instanceof ITubeConnectable || t[3] instanceof IInventory)
			{
				minZ = 0.0f;
			}
			if(t[4] != null && t[4] instanceof ITubeConnectable || t[4] instanceof IInventory)
			{
				maxX = 1.0f;
			}
			if(t[5] != null && t[5] instanceof ITubeConnectable || t[5] instanceof IInventory)
			{
				minX = 0.0f;
			}

			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		TileTube tube = (TileTube) par1World.getBlockTileEntity(par2, par3, par4);
		tube.addItem(new TravellingItem(new ItemStack(Item.diamond)));
		return true;
	}
}
