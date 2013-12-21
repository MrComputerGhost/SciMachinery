package com.sci.machinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.core.BlockSci;

public class BlockCircuitMaker extends BlockSci
{
	public BlockCircuitMaker(int par1)
	{
		super(par1, Material.iron);
		this.setCreativeTab(SciMachinery.tab);
		this.setHardness(0.7F);
		this.setStepSound(Block.soundMetalFootstep);
		this.setUnlocalizedName("tube");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity == null || player.isSneaking()) { return false; }
		player.openGui(SciMachinery.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileCircuitMaker();
	}
}