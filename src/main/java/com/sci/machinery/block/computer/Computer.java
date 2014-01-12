package com.sci.machinery.block.computer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import com.sci.machinery.api.IPacketHandler;
import com.sci.machinery.core.Utils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class Computer implements IPacketHandler
{
	private final int id;
	private World world;
	private TileEntityComputer tile;
	private boolean isDecomissioned;

	private FileSystem fileSystem;
	private Shell shell;

	public Computer(World world, TileEntityComputer tile)
	{
		this(world, CompLib.assignID(), false, tile);
	}

	private Computer(World world, int id, boolean claim, TileEntityComputer tile)
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

		this.tile = tile;

		this.fileSystem = new FileSystem(root);
		this.shell = new Shell();
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

	public static Computer fromNBT(World world, NBTTagCompound root, TileEntityComputer tile)
	{
		int id = root.getInteger("computer-id");
		Computer comp;

		if(id == 0)
			comp = new Computer(world, tile);
		else
			comp = new Computer(world, id, true, tile);

		comp.readFromNBT(root);
		return comp;
	}

	@Override
	public void readPacket(DataInputStream din, Side side)
	{

	}

	@Override
	public void writePacket(DataOutputStream dout, Side side)
	{

	}

	public void keyPressed(char c, int i)
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			System.out.println(c + " " + i);
		}
	}

	@Override
	public void sendPacketUpdate(Side side)
	{
		this.tile.sendPacketUpdate(side);
	}

	public FileSystem getFileSystem()
	{
		return this.fileSystem;
	}

	public Shell getShell()
	{
		return this.shell;
	}
}