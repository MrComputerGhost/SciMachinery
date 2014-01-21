package com.sci.machinery.block.computer.emulation;
/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class BitUtils
{
	private BitUtils()
	{
	}
	
	public static int set(int bits, int position, boolean value)
	{
		if(value)
			return set(bits, position);
		else
			return clear(bits, position);
	}

	public static int clear(int bits, int position)
	{
		return bits & ~(1 << position);
	}

	public static int set(int bits, int position)
	{
		return bits | (1 << position);
	}

	public static int toggle(int bits, int position)
	{
		return bits ^ (1 << position);
	}

	public static boolean get(int bits, int position)
	{
		return (bits & (1 << position)) != 0;
	}
}