package com.sci.machinery.block.computer.emulation;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class Memory extends Device
{
	private boolean readOnly;
	private int[] memory;

	public Memory(int startAddress, int endAddress) throws MemoryException
	{
		this(startAddress, endAddress, false);
	}

	public Memory(int startAddress, int endAddress, boolean readOnly) throws MemoryException
	{
		super(startAddress, endAddress, readOnly ? "ROM" : "RAM");
		this.readOnly = readOnly;
		this.memory = new int[getSize()];
	}

	@Override
	public void write(int address, int data) throws MemoryException
	{
		if(this.readOnly)
			throw new MemoryException("Cannot write to read-only memory at address " + address);
		else
			this.memory[address] = data;
	}

	@Override
	public int read(int address) throws MemoryException
	{
		return this.memory[address];
	}
}