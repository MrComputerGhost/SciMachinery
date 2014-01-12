package com.sci.machinery.block.computer;

import java.io.File;

public class Process
{
	private File proc;

	public Process(File proc) throws FileSystemException
	{
		this.proc = proc;
		
		if(this.proc.exists())
		{
			if(this.proc.isFile())
			{
				throw new FileSystemException("Process is file!");
			}
		}
		else
		{
			this.proc.mkdir();
		}
	}
}