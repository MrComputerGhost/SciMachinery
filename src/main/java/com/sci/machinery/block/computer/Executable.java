package com.sci.machinery.block.computer;

import java.io.File;

public abstract class Executable
{
	private File file;
	
	public Executable(File file)
	{
		this.file = file;
	}
	
	public abstract void execute(String cmd);
	
	public File getFile()
	{
		return this.file;
	}
}