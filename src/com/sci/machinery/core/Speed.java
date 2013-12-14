package com.sci.machinery.core;

public enum Speed
{
	FAST(5), MEDIUM(10), SLOW(20);

	public final int delay;

	Speed(final int delay)
	{
		this.delay = delay;
	}
}