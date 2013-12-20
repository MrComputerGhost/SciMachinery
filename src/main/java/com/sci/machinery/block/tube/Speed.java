package com.sci.machinery.block.tube;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public enum Speed
{
	FAST(5), MEDIUM(10), SLOW(20);

	public final int delay;

	Speed(final int delay)
	{
		this.delay = delay;
	}
}