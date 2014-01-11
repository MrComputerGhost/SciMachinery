package com.sci.machinery.block.computer;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sun.org.mozilla.javascript.Context;
import sun.org.mozilla.javascript.Scriptable;
import com.sci.machinery.core.Utils;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class Computer
{
	public static final String JAVASCRIPT = "JavaScript";

	private static final ScriptEngineManager manager = new ScriptEngineManager();

	private final int id;
	private World world;
	private File root;
	private boolean isDecomissioned;

	private State state;
	
	// apis
	private Terminal terminal;
	private OS os;

	private ScriptEngine engine;
	private Context jsContext;
	private Scriptable jsScope;

	public Computer(World world)
	{
		this(world, CompLib.assignID(), false);
	}

	private Computer(World world, int id, boolean claim)
	{
		this.world = world;
		this.id = id;
		if(claim)
			CompLib.assignID(this.id);

		try
		{
			root = new File(CompLib.getSMCFolder(this.world), String.valueOf(this.id));
			if(!root.exists())
				root.mkdirs();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		this.os = new OS();
		this.terminal = new Terminal();
	}

	public void init()
	{
		this.engine = manager.getEngineByName(JAVASCRIPT);
		this.jsContext = Context.enter();
		this.jsScope = this.jsContext.initStandardObjects();

		this.engine.put("os", os);
		this.engine.put("term", terminal);
		
		boot();
	}

	public void tick()
	{
		if(state == State.REBOOTING)
		{
			boot();
		}
	}
	
	public void boot()
	{
		this.state = State.RUNNING;
		
		try
		{
			this.engine.eval(new InputStreamReader(Computer.class.getResourceAsStream("/assets/scimachinery/js/bios.js")));
		}
		catch(ScriptException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isDecomissioned()
	{
		return this.isDecomissioned;
	}

	public void decomission()
	{
		if(this.isDecomissioned)
			return;
		CompLib.releaseID(this.id);
		Utils.delete(this.root);
		isDecomissioned = true;
	}

	public void writeToNBT(NBTTagCompound root)
	{

	}

	public void readFromNBT(NBTTagCompound root)
	{

	}

	public static Computer fromNBT(World world, NBTTagCompound root)
	{
		int id = root.getInteger("computer-id");
		Computer comp;

		if(id == 0)
			comp = new Computer(world);
		else
			comp = new Computer(world, id, true);

		comp.readFromNBT(root);
		return comp;
	}
	
	public enum State
	{
		RUNNING, REBOOTING, SHUTDOWN;
	}

	public class OS
	{
		public int id()
		{
			return id;
		}
		
		public void reboot()
		{
			state = State.REBOOTING;
		}
		
		public void shutdown()
		{
			state = State.SHUTDOWN;
		}
	}

	public class Terminal
	{
		private int cursorX, cursorY;

		public void write(String s)
		{
		}

		public void writeLine(String s)
		{
			System.out.println(s);
		}

		public void clear()
		{
		}

		public void clearLine()
		{
		}

		public void setCursorPos(int x, int y)
		{
			this.cursorX = x;
			this.cursorY = y;
		}

		public int[] getCursorPos()
		{
			return new int[]
			{ cursorX, cursorY };
		}
	}
}