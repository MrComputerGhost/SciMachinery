package com.sci.machinery.core;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

/**
 * SciMachinery
 *
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public abstract class BlockSci extends BlockContainer
{
	public BlockSci(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        par1World.setBlockTileEntity(par2, par3, par4, this.createNewTileEntity(par1World));
    }
}