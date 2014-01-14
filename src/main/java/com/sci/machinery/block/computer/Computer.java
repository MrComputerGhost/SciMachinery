package com.sci.machinery.block.computer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;
import com.sci.machinery.api.ILuaAPI;
import com.sci.machinery.api.ILuaAPI.APIMethod;
import com.sci.machinery.api.IPacketHandler;
import com.sci.machinery.block.computer.apis.OSAPI;
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

	private LuaValue globals;
	private LuaValue assert_;
	private LuaValue loadString;
	private LuaValue coroutineCreate;
	private LuaValue coroutineResume;
	private LuaValue coroutineYield;
	private LuaThread mainRoutine;

	private List<ILuaAPI> apis;

	public Computer(World world, TileEntityComputer tile)
	{
		this(world, CompLib.assignID(), false, tile);
	}

	private Computer(World world, int id, boolean claim, TileEntityComputer tile)
	{
		this.world = world;
		this.id = id;

		this.apis = new ArrayList<ILuaAPI>();

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
		this.loadString = this.globals.get("loadstring");

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
			LuaJC.install();
		}
		catch(ClassNotFoundException e)
		{
		}

		this.apis.add(new OSAPI(this));

		for(final ILuaAPI api : apis)
		{
			LuaTable apiTable = new LuaTable();
			for(final Method method : api.getClass().getMethods())
			{
				if(method.isAnnotationPresent(APIMethod.class))
				{
					apiTable.set(method.getName(), new VarArgFunction()
					{
						public Varargs invoke(Varargs args)
						{
							Object[] rParams = LuaJValues.toObjects(args, 1);

							Object ret = null;
							try
							{
								ret = method.invoke(api, rParams);
							}
							catch(IllegalAccessException e)
							{
								e.printStackTrace();
							}
							catch(IllegalArgumentException e)
							{
								e.printStackTrace();
							}
							catch(InvocationTargetException e)
							{
								e.printStackTrace();
							}

							return LuaValue.varargsOf(new LuaValue[]
							{ LuaJValues.toValue(ret) });
						}
					});
				}
			}
			this.globals.set(api.getName(), apiTable);
		}
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
				e.printStackTrace();
			}

			for(ILuaAPI api : apis)
			{
				api.onStartup();
			}

			LuaValue program = this.assert_.call(this.loadString.call(LuaValue.valueOf(bios), LuaValue.valueOf("bios")));
			program.call();
			//this.mainRoutine = (LuaThread) this.coroutineCreate.call(program);
		}
		catch(LuaError e)
		{
			if(this.mainRoutine != null)
			{
				this.mainRoutine = null;
			}
			e.printStackTrace();
		}
	}

	public void tick()
	{
		for(ILuaAPI api : apis)
		{
			api.tick();
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