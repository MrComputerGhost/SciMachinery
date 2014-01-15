package com.sci.machinery.block.computer.apis;

import com.sci.machinery.block.computer.Computer;

public class TermAPI extends LuaAPI
{
	public TermAPI(Computer computer)
	{
		super(computer);
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
	public void writeLine(String str)
	{
		System.out.println(str);
	}
}