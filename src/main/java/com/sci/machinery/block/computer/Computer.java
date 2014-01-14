package com.sci.machinery.block.computer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.jruby.embed.ScriptingContainer;
import com.sci.machinery.api.IPacketHandler;
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

	public Computer(World world, TileEntityComputer tile)
	{
		this(world, CompLib.assignID(), false, tile);
	}

	private Computer(World world, int id, boolean claim, TileEntityComputer tile)
	{
		this.world = world;
		this.id = id;

		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			if(claim)
				CompLib.assignID(this.id);

			if(FMLCommonHandler.instance().getEffectiveSide().isServer())
			{
				try
				{
					File root = new File(CompLib.getSMCFolder(this.world), String.valueOf(this.id));
					if(!root.exists())
						root.mkdirs();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		this.tile = tile;
	}

	public void boot()
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			return;

		ScriptingContainer container = new ScriptingContainer();
		container.runScriptlet(Computer.class.getResourceAsStream("/assets/scimachinery/ruby/bios.rb"), "bios.rb");
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

		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			CompLib.releaseID(this.id);
		}

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
	public void readPacket(DataInputStream din, Side side) throws IOException
	{
		if(side == Side.CLIENT)
		{
			int packetType = din.readInt();
			switch (packetType)
			{
			default:
				System.out.println("Received unknown packet!");
				break;
			}
		}
		else
		{
			int packetType = din.readInt();
			switch (packetType)
			{
			default:
				System.out.println("Received unknown packet!");
				break;
			}
		}
	}

	@Override
	public void writePacket(DataOutputStream dout, Side side) throws IOException
	{
		if(side == Side.CLIENT) // sending to client
		{

		}
		else
		{

		}
	}

	public void keyPressed(char c, int i)
	{
	}

	@Override
	public void sendPacketUpdate(Side side)
	{
		this.tile.sendPacketUpdate(side);
	}
}