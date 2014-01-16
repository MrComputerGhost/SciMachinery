package com.sci.machinery.block.computer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.core.BlockSci;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class BlockComputer extends BlockSci
{
	private Icon on, off;
	
	public BlockComputer(int id)
	{
		super(id, Material.iron);
		this.setCreativeTab(SciMachinery.tab);
		this.setHardness(0.7F);
		this.setStepSound(Block.soundMetalFootstep);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityComputer computer = (TileEntityComputer) par1World.getBlockTileEntity(par2, par3, par4);
		if(computer != null)
			computer.breakBlock();

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityComputer)
			((TileEntityComputer) tileEntity).boot();
		if(tileEntity == null || player.isSneaking()) { return false; }
		player.openGui(SciMachinery.instance, 1, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityComputer();
	}
	
	private boolean isOn() {
		return true;
	}
	
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return this.isOn() ? this.on : this.off;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.on = par1IconRegister.registerIcon("scimachinery:pc_on");
		this.off = par1IconRegister.registerIcon("scimachinery:pc_off");
	}
}