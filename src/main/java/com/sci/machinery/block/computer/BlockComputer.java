package com.sci.machinery.block.computer;

import net.minecraft.block.material.Material;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import com.sci.machinery.core.BlockSci;
/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class BlockComputer extends BlockSci
{
	protected BlockComputer(int id)
	{
		super(id, Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityComputer();
	}
}