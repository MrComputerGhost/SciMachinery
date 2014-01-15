package com.sci.machinery.block.computer.apis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.sci.machinery.api.ILuaContext;
import com.sci.machinery.block.computer.Computer;

public class OSAPI extends LuaAPI
{
	private List<Timer> timers;
	private int nextTimerToken;
	private double clock;
	
	public OSAPI(Computer computer)
	{
		super(computer);

		this.timers = new ArrayList<Timer>();
		this.nextTimerToken = 0;
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
		this.clock += 0.05D;
		
		Iterator<Timer> it = this.timers.iterator();
		while(it.hasNext())
		{
			Timer t = (Timer) it.next();
			t.timeLeft -= 0.05D;
			if(t.timeLeft <= 0.0D)
			{
				computer.queueEvent("timer", new Object[]
				{ Integer.valueOf(t.token) });
				it.remove();
			}
		}
	}

	@Override
	public String getName()
	{
		return "os";
	}

	@APIMethod
	public double clock(ILuaContext context, Object[] args)
	{
		return clock;
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

	@APIMethod
	public int startTimer(ILuaContext context, Object[] args)
	{
		if(args.length != 1)
			throw new IllegalArgumentException("Expected one argument (number)");
		this.timers.add(new Timer(Double.valueOf(String.valueOf(args[0])), nextTimerToken));
		return nextTimerToken++;
	}

	class Timer
	{
		public double timeLeft;
		public int token;

		public Timer(double timeLeft, int token)
		{
			this.timeLeft = timeLeft;
			this.token = token;
		}
	}
}