package com.sci.machinery.block.computer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
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

	private char[][] clientTerm;

	@Override
	public void validate()
	{
		super.validate();

		this.clientTerm = new char[61][29];
		for(int x = 0; x < this.clientTerm.length; x++)
		{
			Arrays.fill(this.clientTerm[x], ' ');
		}

		if(this.computer == null)
			this.computer = new Computer(this.worldObj, this);

		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.computer.boot();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if(this.computer != null && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.computer.tick();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.computer = Computer.fromNBT(this.worldObj, par1NBTTagCompound, this);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.computer.writeToNBT(par1NBTTagCompound);
	}

	public void boot()
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.computer.boot();
	}

	public void breakBlock()
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.computer.decomission();
	}

	@Override
	public void readPacket(DataInputStream din, Side side) throws IOException
	{
		this.computer.readPacket(din, side);
	}

	@Override
	public void writePacket(DataOutputStream dout, Side side) throws IOException
	{
		this.computer.writePacket(dout, side);
	}

	public Computer getComputer()
	{
		return this.computer;
	}

	public void setTermCharacter(int x, int y, char readChar)
	{
		this.clientTerm[x][y] = readChar;
	}

	public char getTermCharacter(int x, int y)
	{
		return this.clientTerm[x][y];
	}
}