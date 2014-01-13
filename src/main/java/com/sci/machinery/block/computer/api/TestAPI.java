package com.sci.machinery.block.computer.api;

import com.sci.machinery.block.computer.api.OSAPI.OSAPIMethod;

public class TestAPI
{
	@OSAPIMethod(apiMain = "test")
	public void println(String str)
	{
		System.out.println(str);
	}
}