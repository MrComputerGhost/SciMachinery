package com.sci.machinery.block.computer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import com.sci.machinery.block.TileSci;
import com.sci.machinery.block.computer.emulation.Bus;
import com.sci.machinery.block.computer.emulation.CPU;
import com.sci.machinery.block.computer.emulation.Memory;
import com.sci.machinery.block.computer.emulation.MemoryException;
import cpw.mods.fml.relauncher.Side;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileCase extends TileSci
{
	private Bus bus;
	private CPU cpu;
	private Memory ram;

	public TileCase()
	{
		try
		{
			this.bus = new Bus();
			this.cpu = new CPU(this.bus);
			this.ram = new Memory(0x0000, 0x8000); // 32k of ram (for now)
			
			this.bus.addDevice(this.ram);
		}
		catch(MemoryException e)
		{
			e.printStackTrace();
		}
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

	@Override
	public void readPacket(DataInputStream din, Side side) throws IOException
	{
	}

	@Override
	public void writePacket(DataOutputStream dout, Side side) throws IOException
	{
	}
}