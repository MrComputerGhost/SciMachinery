package com.sci.machinery.block.computer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileEntityComputer extends TileEntity
{
	public void validate()
	{
		super.validate();
		
		if(!worldObj.isRemote)
			System.out.println(CompLib.getWorldFolder(worldObj));
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
	}
}