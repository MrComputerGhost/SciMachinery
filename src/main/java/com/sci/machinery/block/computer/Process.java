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
			if(this.proc.isFile()) { throw new FileSystemException("Process is file!"); }
		}
		else
		{
			this.proc.mkdir();
		}
	}

	public File getProcessFile(String path)
	{
		return new File(proc, path); // TODO make sure they dont go outside of
										// proc with ..
	}
}