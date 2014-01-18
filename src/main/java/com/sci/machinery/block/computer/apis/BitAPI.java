package com.sci.machinery.block.computer.apis;

import com.sci.machinery.api.ILuaContext;
import com.sci.machinery.block.computer.Computer;

public class BitAPI extends LuaAPI
{
	public BitAPI(Computer computer)
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
		return "bit";
	}

	@APIMethod
	public int not(ILuaContext context, Object[] args)
	{
		if(args.length != 1)
			throw new IllegalArgumentException("Expected argument a");
		int a = Integer.valueOf(String.valueOf(args[0]));

		return a ^ 0xFFFFFFFF;
	}

	@APIMethod
	public int and(ILuaContext context, Object[] args)
	{
		if(args.length != 2)
			throw new IllegalArgumentException("Expected arguments a, b");
		int a = Integer.valueOf(String.valueOf(args[0]));
		int b = Integer.valueOf(String.valueOf(args[1]));
		
		return a & b;
	}
	
	@APIMethod
	public int or(ILuaContext context, Object[] args)
	{
		if(args.length != 2)
			throw new IllegalArgumentException("Expected arguments a, b");
		int a = Integer.valueOf(String.valueOf(args[0]));
		int b = Integer.valueOf(String.valueOf(args[1]));
		
		return a | b;
	}
	
	@APIMethod
	public int xor(ILuaContext context, Object[] args)
	{
		if(args.length != 2)
			throw new IllegalArgumentException("Expected arguments a, b");
		int a = Integer.valueOf(String.valueOf(args[0]));
		int b = Integer.valueOf(String.valueOf(args[1]));
		
		return a ^ b;
	}
	
	@APIMethod
	public int rshift(ILuaContext context, Object[] args)
	{
		if(args.length != 2)
			throw new IllegalArgumentException("Expected arguments a, b");
		int a = Integer.valueOf(String.valueOf(args[0]));
		int b = Integer.valueOf(String.valueOf(args[1]));
		
		return a >> b;
	}
	
	@APIMethod
	public int lshift(ILuaContext context, Object[] args)
	{
		if(args.length != 2)
			throw new IllegalArgumentException("Expected arguments a, b");
		int a = Integer.valueOf(String.valueOf(args[0]));
		int b = Integer.valueOf(String.valueOf(args[1]));
		
		return a << b;
	}
	
	@APIMethod
	public int logicalRshift(ILuaContext context, Object[] args)
	{
		if(args.length != 2)
			throw new IllegalArgumentException("Expected arguments a, b");
		int a = Integer.valueOf(String.valueOf(args[0]));
		int b = Integer.valueOf(String.valueOf(args[1]));
		
		return a >>> b;
	}
}