package com.sci.machinery.block.computer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import com.sci.machinery.block.TileSci;
import cpw.mods.fml.relauncher.Side;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileEntityComputer extends TileSci
{
	@Override
	public void validate()
	{
		super.validate();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
	}

	public void boot()
	{
	}

	public void breakBlock()
	{
	}

	@Override
	public void readPacket(DataInputStream din, Side side) throws IOException
	{
	}

	@Override
	public void writePacket(DataOutputStream dout, Side side) throws IOException
	{
	}
}