package com.sci.machinery.block.computer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileEntityComputer extends TileEntity
{
	private Computer computer;

	@Override
	public void validate()
	{
		super.validate();

		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			if(this.computer == null)
				this.computer = new Computer(this.worldObj);

			this.computer.init();
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(this.computer != null)
			this.computer.tick();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			this.computer = Computer.fromNBT(this.worldObj, par1NBTTagCompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			this.computer.writeToNBT(par1NBTTagCompound);
		}
	}

	public void breakBlock()
	{
		this.computer.decomission();
	}
}