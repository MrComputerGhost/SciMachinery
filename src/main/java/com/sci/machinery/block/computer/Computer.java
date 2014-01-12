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
					this.fileSystem = new FileSystem(root);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		this.tile = tile;
		
		try
		{
			this.shell = new Shell(this);
		}
		catch(FileSystemException e)
		{
		}
	}

	public void boot()
	{
		shell.println("Starting FileSystem...");
		fileSystem.initFS();
	}

	public void tick()
	{
		if(this.shell.isDirty())
		{
			this.sendPacketUpdate(Side.CLIENT);
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

		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			CompLib.releaseID(this.id);
			fileSystem.close();
			Utils.delete(this.fileSystem.getRoot());
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
			case PACKET_SHELL_RENDER_UPDATE:
				String[] screen = new String[Shell.HEIGHT];
				this.shell.setCursorX(din.readInt());
				this.shell.setCursorY(din.readInt());
				for(int i = 0; i < screen.length; i++)
					screen[i] = din.readUTF();
				this.shell.setLines(screen);
				break;
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
			if(this.shell.isDirty())
			{
				String[] screen = this.shell.getLines();
				dout.writeInt(PACKET_SHELL_RENDER_UPDATE);
				dout.writeInt(this.shell.getCursorX());
				dout.writeInt(this.shell.getCursorY());

				for(int i = 0; i < screen.length; i++)
					dout.writeUTF(screen[i]);

				this.shell.setClean();
			}
		}
	}

	public void keyPressed(char c, int i)
	{
		this.shell.keyPressed(c, true);
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

	public static final int PACKET_SHELL_RENDER_UPDATE = 0;
}