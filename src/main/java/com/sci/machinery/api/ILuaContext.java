package com.sci.machinery.api;

public interface ILuaContext
{
	public Object[] pullEvent(String filter) throws Exception, InterruptedException;

	public Object[] pullEventRaw(String filter) throws InterruptedException;

	public Object[] yield(Object[] args) throws InterruptedException;
}