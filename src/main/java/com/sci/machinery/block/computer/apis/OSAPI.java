package com.sci.machinery.block.computer.apis;

import com.sci.machinery.api.ILuaContext;
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
	public void queueEvent(ILuaContext context, Object[] args)
	{
		if(args.length == 0)
			throw new IllegalArgumentException("Invalid number of arguments!");
		computer.queueEvent((String) args[0], args);
	}

	@APIMethod
	public Object[] pullEvent(ILuaContext context, Object[] args)
	{
		try
		{
			return context.pullEvent(args.length == 1 ? (String) args[0] : null);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@APIMethod
	public Object[] pullEventRaw(ILuaContext context, Object[] args)
	{
		try
		{
			return context.pullEventRaw(args.length == 1 ? (String) args[0] : null);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@APIMethod
	public void reboot(ILuaContext context, Object[] args)
	{
		computer.reboot();
	}

	@APIMethod
	public void shutdown(ILuaContext context, Object[] args)
	{
		computer.shutdown();
	}

	@APIMethod
	public int getComputerID(ILuaContext context, Object[] args)
	{
		return computer.getID();
	}
}