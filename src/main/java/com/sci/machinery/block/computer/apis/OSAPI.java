package com.sci.machinery.block.computer.apis;

import com.sci.machinery.block.computer.Computer;

public class OSAPI extends LuaAPI
{
	public OSAPI(Computer computer)
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
		return "os";
	}

	@APIMethod
	public void reboot()
	{
		computer.reboot();
	}

	@APIMethod
	public void shutdown()
	{
		computer.shutdown();
	}
	
	@APIMethod
	public int getComputerID()
	{
		return computer.getID();
	}
}