package com.sci.machinery.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ILuaAPI
{
	public void onStartup();

	public void onShutdown();

	public void tick();

	public String getName();

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface APIMethod
	{
	}
}