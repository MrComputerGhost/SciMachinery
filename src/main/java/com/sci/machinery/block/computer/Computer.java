package com.sci.machinery.block.computer;

import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import com.sci.machinery.core.Utils;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class Computer
{
	private final int id;
	private World world;
	private boolean isDecomissioned;

	private FileSystem fileSystem;

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

		File root = null;
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

		this.fileSystem = new FileSystem(root);
	}

	public void boot()
	{
		fileSystem.initFS();
	}

	public void tick()
	{

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

		fileSystem.close();

		Utils.delete(this.fileSystem.getRoot());
		isDecomissioned = true;
	}

	public void writeToNBT(NBTTagCompound root)
	{
		root.setInteger("computer-id", this.id);
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
}