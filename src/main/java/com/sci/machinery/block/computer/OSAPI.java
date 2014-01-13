package com.sci.machinery.block.computer;

public class OSAPI implements ILuaAPI
{
	private Computer computer;

	public OSAPI(Computer computer)
	{
		this.computer = computer;
	}

	@Override
	public String[] getNames()
	{
		return new String[]
		{ "os" };
	}

	@Override
	public void startup()
	{

	}

	@Override
	public void advance(double a)
	{

	}

	@Override
	public void shutdown()
	{

	}

	@Override
	public String[] getMethodNames()
	{
		return new String[]{ "shutdown" };
	}

	@Override
	public Object[] callMethod(ILuaContext context, int i, Object[] param) throws Exception
	{
		switch(i)
		{
		case 0:
			System.out.println("shutdown");
			computer.shutdown();
		}
		return null;
	}
}