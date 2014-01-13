package com.sci.machinery.block.computer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;
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

	private Globals globals;
	private LuaValue assert_;
	private LuaValue loadString;
	private LuaValue coroutineCreate;
	private LuaValue coroutineResume;
	private LuaValue coroutineYield;
	private LuaValue mainRoutine;

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

		initLUA();
	}

	private void initLUA()
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			return;

		this.globals = JsePlatform.debugGlobals();

		this.assert_ = this.globals.get("assert");
		this.loadString = this.globals.get("load");

		this.globals.set("collectgarbage", LuaValue.NIL);
		this.globals.set("dofile", LuaValue.NIL);
		this.globals.set("load", LuaValue.NIL);
		this.globals.set("loadfile", LuaValue.NIL);
		this.globals.set("module", LuaValue.NIL);
		this.globals.set("require", LuaValue.NIL);
		this.globals.set("package", LuaValue.NIL);
		this.globals.set("io", LuaValue.NIL);
		this.globals.set("os", LuaValue.NIL);
		this.globals.set("print", LuaValue.NIL);
		this.globals.set("luajava", LuaValue.NIL);
		this.globals.set("debug", LuaValue.NIL);
		this.globals.set("newproxy", LuaValue.NIL);

		LuaValue coroutine = this.globals.get("coroutine");
		this.coroutineCreate = coroutine.get("create");
		this.coroutineResume = coroutine.get("resume");
		this.coroutineYield = coroutine.get("yield");

		try
		{
			Class.forName("org.apache.bcel.util.Repository");
			LuaJC.install(this.globals);
		}
		catch(ClassNotFoundException e)
		{
		}

		// OSAPI.install(this.globals);
	}

	public void boot()
	{
		if(this.mainRoutine != null) { return; }
		try
		{
			String bios = null;
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(Computer.class.getResourceAsStream("/assets/scimachinery/lua/bios.lua")));
				StringBuilder fileText = new StringBuilder("");
				String line = reader.readLine();
				while(line != null)
				{
					fileText.append(line);
					line = reader.readLine();
					if(line != null)
					{
						fileText.append("\n");
					}
				}
				bios = fileText.toString();
			}
			catch(IOException e)
			{
				throw new LuaError("Could not read file");
			}

			LuaValue p = this.loadString.call(LuaValue.valueOf(bios), LuaValue.valueOf("bios"));
			System.out.println(p);

			// LuaValue program =
			// this.assert_.call(this.loadString.call(LuaValue.valueOf(bios),
			// LuaValue.valueOf("bios")));
			// this.mainRoutine = this.coroutineCreate.call(program);
		}
		catch(LuaError e)
		{
			if(this.mainRoutine != null)
			{
				this.mainRoutine = null;
			}
		}
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