package com.sci.machinery.block.computer.emulation;
/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public abstract class Device implements Comparable<Device>
{
	private int size;
	private MemoryRange memoryRange;
	private String name;
	private Bus bus;

	public Device(int startAddress, int endAddress, String name) throws MemoryException
	{
		this.name = name;
		this.memoryRange = new MemoryRange(startAddress, endAddress);
		this.size = endAddress - startAddress + 1;
	}

	public abstract void write(int address, int data) throws MemoryException;

	public abstract int read(int address) throws MemoryException;

	public final Bus getBus()
	{
		return this.bus;
	}

	public final void setBus(Bus bus)
	{
		this.bus = bus;
	}

	public MemoryRange getMemoryRange()
	{
		return this.memoryRange;
	}

	public int getSize()
	{
		return this.size;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(": ");
		sb.append(this.memoryRange.toString());
		return sb.toString();
	}
	
	@Override
	public int compareTo(Device other)
	{
		if(other == null)
			throw new NullPointerException("Cannot compare to null");
		if(this == other)
			return 0;
		return this.memoryRange.compareTo(other.getMemoryRange());
	}
}