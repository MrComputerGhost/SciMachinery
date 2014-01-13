package com.sci.machinery.block.computer;

public interface ILuaAPI
{
	public abstract String[] getNames();
	
	public abstract void startup();
	
	public abstract void advance(double a);
	
	public abstract void shutdown();
	
	public abstract String[] getMethodNames();
	
	public abstract Object[] callMethod(ILuaContext context, int i, Object[] param) throws Exception;
}