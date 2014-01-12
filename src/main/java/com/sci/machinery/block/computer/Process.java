package com.sci.machinery.block.computer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import com.sci.machinery.core.Utils;
import cpw.mods.fml.common.FMLCommonHandler;

public class Process
{
	private Computer computer;
	private File proc;

	private Map<String, String> environment;

	public Process(Computer computer, File proc) throws FileSystemException
	{
		this.computer = computer;
		this.proc = proc;
		this.environment = new HashMap<String, String>();

		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			if(this.proc == null)
			{
				this.proc = computer.getFileSystem().getFile("/proc/" + newID());
			}

			if(this.proc.exists())
			{
				if(this.proc.isFile()) { throw new FileSystemException("Process is file!"); }
			}
			else
			{
				this.proc.mkdirs();
			}

			try
			{
				File environ = new File(this.proc, "environ");
				if(environ.exists())
				{
					if(environ.isDirectory())
					{
						Utils.delete(environ);
						environ.createNewFile();
					}
				}
				else
				{
					environ.createNewFile();
				}
			}
			catch(IOException e)
			{
			}
		}
	}

	public void setEnv(String k, String v)
	{
		this.environment.put(k, v);
		saveEnviron();
	}

	public String getEnv(String k)
	{
		return this.environment.get(k);
	}

	public File getProcessFile(String path)
	{
		return new File(proc, path); // TODO make sure they dont go outside of
										// proc with ..
	}

	private void saveEnviron()
	{
		Properties props = new Properties();
		Iterator<String> keys = this.environment.keySet().iterator();
		while(keys.hasNext())
		{
			String k = keys.next();
			String v = this.environment.get(k);
			props.setProperty(k, v);
		}
		try
		{
			props.store(new FileOutputStream(new File(this.proc, "environ")), "");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private int newID()
	{
		int ret = 1;

		while(true)
		{
			if(!computer.getFileSystem().getFile("/proc/" + ret).exists())
				break;
			ret++;
		}

		return ret;
	}
}