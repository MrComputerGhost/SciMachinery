package com.sci.machinery.block.computer.api;

import com.sci.machinery.api.ILuaAPI;
import com.sci.machinery.api.ILuaAPI.APIMethod;

public class OSAPI implements ILuaAPI
{
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
	}

	@APIMethod
	public void shutdown()
	{
	}
}