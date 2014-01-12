package com.sci.machinery.block.computer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import net.minecraft.nbt.NBTTagCompound;
import com.sci.machinery.block.TileSci;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileEntityComputer extends TileSci
{
	private Computer computer;

	@Override
	public void validate()
	{
		super.validate();

		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			if(this.computer == null)
				this.computer = new Computer(this.worldObj, this);

			this.computer.boot();
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
			this.computer = Computer.fromNBT(this.worldObj, par1NBTTagCompound, this);
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

	@Override
	public void readPacket(DataInputStream din, Side side)
	{
		this.computer.readPacket(din, side);
	}

	@Override
	public void writePacket(DataOutputStream dout, Side side)
	{
		this.computer.writePacket(dout, side);
	}

	public Computer getComputer()
	{
		return this.computer;
	}
}