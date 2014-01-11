package com.sci.machinery.block.computer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class Computer
{
	public static final String JAVASCRIPT = "JavaScript";

	private final int id;
	private ScriptEngine engine;
	private World world;
	private boolean isDecomissioned;

	public Computer(World world)
	{
		this(world, CompLib.assignID());
	}

	private Computer(World world, int id)
	{
		this.world = world;
		this.id = id;
		CompLib.assignID(this.id);
		ScriptEngineManager manager = new ScriptEngineManager();
		this.engine = manager.getEngineByName(JAVASCRIPT);
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
		isDecomissioned = true;
	}

	public void writeTONBT(NBTTagCompound root)
	{

	}

	public void readFromNBT(NBTTagCompound root)
	{

	}

	public static Computer fromNBT(World world, NBTTagCompound root)
	{
		int id = root.getInteger("computer-id");
		Computer comp = new Computer(world, id);
		comp.readFromNBT(root);
		return comp;
	}
}