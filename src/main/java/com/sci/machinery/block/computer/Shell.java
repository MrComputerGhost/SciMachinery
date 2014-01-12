package com.sci.machinery.block.computer;

import java.io.File;
import java.util.Arrays;
import cpw.mods.fml.common.FMLCommonHandler;

public class Shell extends Process
{
	public static final int WIDTH = 40;
	public static final int HEIGHT = 18;

	private Computer computer;

	private int cursorX;
	private int cursorY;
	private boolean isDirty;

	private String[] lines;

	public Shell(Computer computer) throws FileSystemException
	{
		super(computer, null);

		this.computer = computer;
		this.cursorX = 0;
		this.cursorY = 0;
		this.lines = new String[HEIGHT];
		Arrays.fill(this.lines, "");
		setCursorY(HEIGHT - 1);

		setEnv("PATH", "/bin:/sbin");
	}

	public void tick()
	{
	}

	public void keyPressed(char c, boolean tryCMD)
	{
		if(c == '\r')
		{
			for(int j = lines.length - 1; j > 0; j--)
				lines[j] = lines[j - 1];
			String s = lines[0];
			lines[0] = "";
			if(tryCMD)
				onCommandExecuted(s);
			isDirty = true;
		}
		else if(c == '\b')
		{
			int len = lines[0].length();
			if(len > 0)
			{
				--len;
				lines[0] = lines[0].substring(0, len);
			}
			isDirty = true;
		}
		else if(c == '\t')
		{
			lines[0] += "        ";
			isDirty = true;
		}
		else if(c >= 32 && c < 127)
		{
			lines[0] += c;
			isDirty = true;
		}

		if(isDirty)
		{
			int x = 0;
			for(int xx = 0; xx < lines[0].length(); xx++)
			{
				if(lines[0].charAt(xx) != 32)
					x = xx;
			}
			setCursorX(x);
		}
	}

	public void print(String s)
	{
		for(int i = 0; i < s.length(); i++)
			keyPressed(s.charAt(i), false);
	}

	public void println(String s)
	{
		for(int i = 0; i < s.length(); i++)
			keyPressed(s.charAt(i), false);
		keyPressed('\r', false);
	}

	private void onCommandExecuted(String cmd)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			return;

		String[] pathLocations = this.getEnv("PATH").split(":");
		File[] folders = new File[pathLocations.length];
		for(int i = 0; i < folders.length; i++)
		{
			folders[i] = computer.getFileSystem().getFile(pathLocations[i]);
		}

		String[] parts = cmd.split(" ");
		if(parts.length > 0)
		{
			String search = parts[0];
			File found = null;

			for(File folder : folders)
			{
				found = search(folder, search);
				if(found != null)
					break;
			}

			if(found == null)
			{
				println(search + ": command not found");
			}
			else
			{
				
			}
		}
	}

	public File search(File f, String s)
	{
		if(f == null)
			return null;
		File[] files = f.listFiles();
		if(files == null)
			return null;
		for(File file : files)
		{
			if(file.isDirectory())
			{
				return search(file, s);
			}
			else if(file.getName().equals(s)) { return file; }
		}
		return null;
	}

	public int getCursorX()
	{
		return cursorX;
	}

	public void setCursorX(int cursorX)
	{
		this.cursorX = cursorX;
	}

	public int getCursorY()
	{
		return cursorY;
	}

	public void setCursorY(int cursorY)
	{
		this.cursorY = cursorY;
	}

	public static int index(int x, int y)
	{
		return x + y * WIDTH;
	}

	public String[] getLines()
	{
		return this.lines;
	}

	public void setLines(String[] lines)
	{
		this.lines = lines;
	}

	public void setDirty()
	{
		this.isDirty = true;
	}

	public void setClean()
	{
		this.isDirty = false;
	}

	public boolean isDirty()
	{
		return this.isDirty;
	}
}