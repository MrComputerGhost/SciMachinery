package com.sci.machinery.block.computer;

public interface ILuaContext
{
	public abstract Object[] pullEvent(String s) throws Exception, InterruptedException;

	public abstract Object[] pullEventRaw(String s) throws InterruptedException;

	public abstract Object[] yield(Object[] param) throws InterruptedException;
}