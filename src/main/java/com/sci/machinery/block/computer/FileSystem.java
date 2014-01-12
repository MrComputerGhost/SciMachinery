package com.sci.machinery.block.computer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystem
{
	private File root;

	public FileSystem(File root)
	{
		this.root = root;
	}

	public void initFS()
	{
		List<File> initFS = new ArrayList<File>();

		initFS.add(getFile("/proc"));
		initFS.add(getFile("/boot"));
		initFS.add(getFile("/dev"));
		initFS.add(getFile("/bin"));
		initFS.add(getFile("/lib"));
		initFS.add(getFile("/root"));
		initFS.add(getFile("/srv"));
		initFS.add(getFile("/sys"));
		initFS.add(getFile("/tmp"));
		initFS.add(getFile("/usr"));
		initFS.add(getFile("/var"));
		initFS.add(getFile("/mnt"));
		initFS.add(getFile("/home"));

		for(File f : initFS)
		{
			if(!f.exists())
			{
				f.mkdirs();
			}
		}
	}

	public File getRoot()
	{
		return this.root;
	}

	public File getFile(String path)
	{
		return new File(root, path); // TODO make sure they dont go outside of
										// root with ..
	}

	public void close()
	{

	}
}