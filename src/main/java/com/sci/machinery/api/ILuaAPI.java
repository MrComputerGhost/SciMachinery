package com.sci.machinery.api;

public interface ILuaAPI extends ILuaObject
{
	public void onStartup();

	public void onShutdown();

	public void tick();

	public String getName();
}