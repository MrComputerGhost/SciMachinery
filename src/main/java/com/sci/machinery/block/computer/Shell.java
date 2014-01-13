package com.sci.machinery.block.computer;

public class Shell
{
	public static final int WIDTH = 40;
	public static final int HEIGHT = 18;

	private Computer computer;

	private int cursorX;
	private int cursorY;

	public Shell(Computer computer)
	{
		this.computer = computer;
		this.cursorX = 0;
		this.cursorY = 0;
	}

	public void tick()
	{
	}

	public void keyPressed(char c, boolean tryCMD)
	{
	}

	public int getCursorX()
	{
		return cursorX;
	}

	public void setCursorX(int cursorX)
	{
		this.cursorX = cursorX;
	}

	public int getCursorY()
	{
		return cursorY;
	}

	public void setCursorY(int cursorY)
	{
		this.cursorY = cursorY;
	}

	public static int index(int x, int y)
	{
		return x + y * WIDTH;
	}
}