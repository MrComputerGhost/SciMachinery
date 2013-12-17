package com.sci.machinery.block.tube;

/**
 * SciMachinery
 *
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public enum Material
{
	STONE(200, 200, 200, 150), COBBLESTONE(125, 125, 125, 150), PUMP(200, 20, 20, 150), VOID(20, 20, 200, 150), DETECTOR(200, 200, 20, 150);

	public final int r, g, b, a;

	Material(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public int getR(int b)
	{
		switch (ordinal())
		{
		case 4:
		{
			return r + 50;
		}
		}
		return r;
	}

	public int getG(int b)
	{
		switch (ordinal())
		{
		case 4:
		{
			return r + 50;
		}
		}
		return g;
	}

	public int getB(int b)
	{
		return this.b;
	}

	public int getA(int b)
	{
		return a;
	}
}