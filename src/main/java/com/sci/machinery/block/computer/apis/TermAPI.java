package com.sci.machinery.block.computer.apis;

import com.sci.machinery.api.ILuaContext;
import com.sci.machinery.block.computer.Computer;

public class TermAPI extends LuaAPI
{
	private char[][] screen;

	public TermAPI(Computer computer)
	{
		super(computer);
		this.screen = new char[60][29];
	}

	@Override
	public void onStartup()
	{

	}

	@Override
	public void onShutdown()
	{

	}

	@Override
	public void tick()
	{

	}

	@Override
	public String getName()
	{
		return "term";
	}

	@APIMethod
	public void setCharacter(ILuaContext context, Object[] args)
	{
		if(args.length != 3)
			throw new IllegalArgumentException("Expected arguments: x, y, character");
		if(String.valueOf(args[2]).length() != 1)
			throw new IllegalArgumentException("Got string expected character");

		screen[Integer.valueOf(String.valueOf(args[0]))][Integer.valueOf(String.valueOf(args[1]))] = String.valueOf(args[2]).charAt(0);
	}
}