package com.sci.machinery.block.computer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.World;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.OrphanedThread;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;
import com.sci.machinery.api.ILuaAPI;
import com.sci.machinery.api.ILuaContext;
import com.sci.machinery.api.IPacketHandler;
import com.sci.machinery.block.computer.apis.FSAPI;
import com.sci.machinery.block.computer.apis.OSAPI;
import com.sci.machinery.block.computer.apis.TermAPI;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class Computer implements IPacketHandler, ILuaContext
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
	private String eventFilter;
	private LuaThread mainRoutine;

	private List<ILuaAPI> apis;

	private File root;

	private State state;
	private Queue<Runnable> tasks;

	public Computer(World world, TileEntityComputer tile)
	{
		this(world, CompLib.assignID(), false, tile);
	}

	private Computer(World world, int id, boolean claim, TileEntityComputer tile)
	{
		this.world = world;
		this.id = id;

		this.apis = new ArrayList<ILuaAPI>();
		this.tasks = new LinkedList<Runnable>();

		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			if(claim)
				CompLib.assignID(this.id);

			if(FMLCommonHandler.instance().getEffectiveSide().isServer())
			{
				try
				{
					root = new File(CompLib.getSMCFolder(this.world), String.valueOf(this.id));
					if(!root.exists())
						root.mkdirs();

					copyFS();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		this.state = State.OFF;
		this.tile = tile;
	}

	private void copyFS() throws IOException
	{
		String[] files = new String[]
		{ "kernel.lua", "lib/os.lua" };

		for(String file : files)
		{
			File fFile = new File(root, file);
			if(!fFile.exists())
			{
				if(!fFile.getParentFile().exists())
				{
					fFile.getParentFile().mkdirs();
				}
				fFile.createNewFile();
			}

			InputStream in = Computer.class.getResourceAsStream("/assets/scimachinery/lua/os/" + file);
			OutputStream out = new FileOutputStream(fFile);
			int readBytes = 0;
			byte[] buffer = new byte[4096];
			while((readBytes = in.read(buffer)) > 0)
			{
				out.write(buffer, 0, readBytes);
			}
			in.close();
			out.close();
		}
	}

	public void boot()
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			return;

		if(state != State.OFF)
			return;

		this.tasks.clear();
		this.apis.clear();

		this.state = State.STARTING;

		this.globals = JsePlatform.debugGlobals();

		this.assert_ = this.globals.get("assert");
		this.loadString = this.globals.get("loadstring");

		LuaValue coroutine = this.globals.get("coroutine");
		final LuaValue nativeCoroutineCreate = coroutine.get("create");

		LuaValue debug = this.globals.get("debug");
		final LuaValue setHook = debug.get("sethook");

		coroutine.set("create", new OneArgFunction()
		{
			@Override
			public LuaValue call(LuaValue value)
			{
				final LuaThread thread = nativeCoroutineCreate.call(value).checkthread();
				setHook.invoke(new LuaValue[]
				{ thread, new ZeroArgFunction()
				{
					@Override
					public LuaValue call()
					{
						Computer.this.coroutineYield.invoke(LuaValue.varargsOf(new LuaValue[]
						{ LuaValue.NIL }));
						return LuaValue.NIL;
					}
				}, LuaValue.NIL, LuaValue.valueOf(1000000) });
				return thread;
			}
		});
		
		this.globals.set("collectgarbage", LuaValue.NIL);
		this.globals.set("dofile", LuaValue.NIL);
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

		this.coroutineCreate = coroutine.get("create");
		this.coroutineResume = coroutine.get("resume");
		this.coroutineYield = coroutine.get("yield");

		this.apis.add(new OSAPI(this));
		this.apis.add(new FSAPI(this));
		this.apis.add(new TermAPI(this));

		for(final ILuaAPI api : apis)
		{
			this.globals.set(api.getName(), LUALib.toLuaObject(this, api));
		}

		try
		{
			String bootloader = null;
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(Computer.class.getResourceAsStream("/assets/scimachinery/lua/bootloader.lua")));
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
				bootloader = fileText.toString();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}

			for(ILuaAPI api : apis)
			{
				api.onStartup();
			}

			this.state = State.RUNNING;
			this.sendPacketUpdate(Side.CLIENT);

			LuaValue program = this.assert_.call(this.loadString.call(LuaValue.valueOf(bootloader), LuaValue.valueOf("bootloader")));
			this.mainRoutine = (LuaThread) this.coroutineCreate.call(program);
		}
		catch(LuaError e)
		{
			if(this.mainRoutine != null)
			{
				this.mainRoutine = null;
			}
			e.printStackTrace();
		}

		handleEvent(null, null);
	}

	public void tick()
	{
		TermAPI term = null;
		for(ILuaAPI api : apis)
		{
			if(api instanceof TermAPI)
			{
				term = (TermAPI) api;
				break;
			}
		}
		if(term != null)
		{
			if(term.isDirty())
			{
				this.sendPacketUpdate(Side.CLIENT);
			}
		}

		if(!this.tasks.isEmpty())
			this.tasks.poll().run();

		for(ILuaAPI api : apis)
			api.tick();
	}

	private void handleEvent(String name, Object[] args)
	{
		if(this.mainRoutine == null)
			return;

		if((this.eventFilter != null) && (name != null) && (!name.equals(this.eventFilter)) && (!name.equals("terminate")))
			return;

		LuaValue[] rArgs;
		if(name != null)
		{
			rArgs = LuaJValues.toValues(args, 2);
			rArgs[0] = this.mainRoutine;
			rArgs[1] = LuaValue.valueOf(name);
		}
		else
		{
			rArgs = new LuaValue[1];
			rArgs[0] = this.mainRoutine;
		}
		Varargs res = this.coroutineResume.invoke(LuaValue.varargsOf(rArgs));
		if(!res.arg1().checkboolean()) { throw new LuaError(res.arg(2).checkstring().toString()); }
		LuaValue filter = res.arg(2);
		if(filter.isstring())
			this.eventFilter = filter.toString();
		else
			this.eventFilter = null;
		if(this.mainRoutine.getStatus().equals("dead"))
			this.mainRoutine = null;
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
			shutdown();
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
			this.state = State.valueOf(din.readUTF());
			this.world.markBlockForRenderUpdate(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord);
			for(int x = 0; x < 40; x++)
			{
				for(int y = 0; y < 29; y++)
				{
					this.tile.setTermCharacter(x, y, din.readChar());
				}
			}
		}
		else
		{
		}
	}

	@Override
	public void writePacket(DataOutputStream dout, Side side) throws IOException
	{
		if(side == Side.CLIENT) // sending to client
		{
			dout.writeUTF(this.state.name());
			TermAPI term = null;
			for(ILuaAPI api : apis)
			{
				if(api instanceof TermAPI)
				{
					term = (TermAPI) api;
					break;
				}
			}
			if(term != null)
			{
				for(int x = 0; x < 40; x++)
				{
					for(int y = 0; y < 29; y++)
					{
						dout.writeChar(term.getCharacter(x, y));
					}
				}
			}
		}
		else
		{

		}
	}

	public void keyPressed(char c, int i)
	{
		if(this.state == State.RUNNING)
		{
			if(i >= 0)
				handleEvent("key", new Object[]
				{ i });
			if(ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0)
				handleEvent("char", new Object[]
				{ c });
		}
	}

	@Override
	public void sendPacketUpdate(Side side)
	{
		this.tile.sendPacketUpdate(side);
	}

	public void shutdown()
	{
		this.state = State.STOPPING;

		this.state = State.OFF;
		this.sendPacketUpdate(Side.CLIENT);
	}

	public void reboot()
	{
		shutdown();
		boot();
	}

	public int getID()
	{
		return this.id;
	}

	public void queueEvent(final String event, final Object[] args)
	{
		Runnable task = new Runnable()
		{
			@Override
			public void run()
			{
				Computer.this.handleEvent(event, args);
			}
		};
		this.tasks.add(task);
	}

	@Override
	public Object[] pullEvent(String filter) throws Exception, InterruptedException
	{
		Object[] res = pullEventRaw(filter);
		if((res.length >= 1) && (res[0].equals("terminate")))
			throw new Exception("Terminated");
		return res;
	}

	@Override
	public Object[] pullEventRaw(String filter) throws InterruptedException
	{
		return yield(new Object[]
		{ filter });
	}

	@Override
	public Object[] yield(Object[] args) throws InterruptedException
	{
		try
		{
			LuaValue[] vArgs = LuaJValues.toValues(args, 0);
			Varargs res = Computer.this.coroutineYield.invoke(LuaValue.varargsOf(vArgs));
			return LuaJValues.toObjects(res, 1);
		}
		catch(OrphanedThread e)
		{
			throw new InterruptedException();
		}
	}

	public File getRoot()
	{
		return this.root;
	}

	public State getState()
	{
		return this.state;
	}

	public enum State
	{
		RUNNING, STARTING, STOPPING, OFF;
	}

	public class Pair<K, V>
	{
		private K k;
		private V v;

		public Pair(K k, V v)
		{
			this.k = k;
			this.v = v;
		}

		public void setK(K k)
		{
			this.k = k;
		}

		public void setV(V v)
		{
			this.v = v;
		}

		public K getK()
		{
			return this.k;
		}

		public V getV()
		{
			return this.v;
		}
	}
}