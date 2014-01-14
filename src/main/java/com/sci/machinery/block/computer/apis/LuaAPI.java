package com.sci.machinery.block.computer.apis;

import com.sci.machinery.api.ILuaAPI;
import com.sci.machinery.block.computer.Computer;

public abstract class LuaAPI implements ILuaAPI
{
	protected Computer computer;

	public LuaAPI(Computer computer)
	{
		this.computer = computer;
	}
}