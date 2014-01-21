package com.sci.machinery.block.computer.emulation;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class MemoryException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public MemoryException(String string)
	{
		super(string);
	}
}