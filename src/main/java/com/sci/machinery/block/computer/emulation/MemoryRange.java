package com.sci.machinery.block.computer.emulation;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class MemoryRange implements Comparable<MemoryRange>
{
	private int startAddress;
	private int endAddress;

	public MemoryRange(int startAddress, int endAddress) throws MemoryException
	{
		if(startAddress < 0 || endAddress < 0)
			throw new MemoryException("Addresses cannot be less than 0");

		if(startAddress >= endAddress)
			throw new MemoryException("End address must be greater than start address");

		this.startAddress = startAddress;
		this.endAddress = endAddress;
	}

	public int getStartAddress()
	{
		return this.startAddress;
	}

	public int getEndAddress()
	{
		return this.endAddress;
	}

	public boolean includes(int address)
	{
		return (address <= this.endAddress) && (address >= this.startAddress);
	}

	public boolean overlaps(MemoryRange other)
	{
		return this.includes(other.getStartAddress()) || other.includes(this.startAddress);
	}

	@Override
	public int compareTo(MemoryRange other)
	{
		if(other == null)
			throw new NullPointerException("Cannot compare to null");
		if(this == other)
			return 0;

		Integer thisStartAddr = new Integer(this.startAddress);
		Integer thatStartAddr = new Integer(other.getStartAddress());
		return thisStartAddr.compareTo(thatStartAddr);
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("@");
		sb.append(String.format("0x%04x", this.startAddress));
		sb.append('-');
		sb.append(String.format("0x%04x", this.endAddress));
		return sb.toString();
	}
}