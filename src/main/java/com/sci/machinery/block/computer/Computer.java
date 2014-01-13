package com.sci.machinery.block.computer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.OrphanedThread;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;
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

	private List<ILuaAPI> apis;
	private LuaValue globals;
	private LuaValue coroutineCreate;
	private LuaValue coroutineResume;
	private LuaValue coroutineYield;
	private LuaValue mainRoutine;
	private LuaValue assert_;
	private LuaValue loadString;
	private String softAbortMessage;
	private String hardAbortMessage;
	private String eventFilter;
	private static HashMap<Map<Object, Object>, LuaValue> processingValue;
	private static HashMap<LuaValue, Map<Object, Object>> processing;
	private static ArrayList<LuaValue> tree;

	public Computer(World world, TileEntityComputer tile)
	{
		this(world, CompLib.assignID(), false, tile);
	}

	private Computer(World world, int id, boolean claim, TileEntityComputer tile)
	{
		this.world = world;
		this.id = id;

		this.globals = JsePlatform.debugGlobals();

		this.assert_ = this.globals.get("assert");
		this.loadString = this.globals.get("load");

		LuaValue coroutine = this.globals.get("coroutine");
		final LuaValue nativeCoroutineCreate = coroutine.get("create");

		LuaValue debug = this.globals.get("debug");
		final LuaValue debugSethook = debug.get("sethook");

		coroutine.set("create", new OneArgFunction()
		{
			@Override
			public LuaValue call(LuaValue value)
			{
				final LuaThread thread = nativeCoroutineCreate.call(value).checkthread();

				debugSethook.invoke(new LuaValue[]
				{ thread, new ZeroArgFunction()
				{
					@Override
					public LuaValue call()
					{
						String hardAbortMessage = Computer.this.hardAbortMessage;
						if(hardAbortMessage != null)
						{
						}
						return LuaValue.NIL;
					}
				}, LuaValue.NIL, LuaValue.valueOf(100000) });

				return thread;
			}
		});

		this.coroutineCreate = coroutine.get("create");
		this.coroutineResume = coroutine.get("resume");
		this.coroutineYield = coroutine.get("yield");

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
	}

	public void loadBIOS(InputStream in)
	{
		if(this.mainRoutine != null)
			return;
		String bios = null;
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder ft = new StringBuilder();
			String line = reader.readLine();
			while(line != null)
			{
				ft.append(line);
				line = reader.readLine();
				if(line != null)
				{
					ft.append("\n");
				}
			}
			bios = ft.toString();
			reader.close();
		}
		catch(IOException e)
		{
			throw new LuaError("Could not read file");
		}
		LuaValue program = this.assert_.call(this.loadString.call(LuaValue.valueOf(bios), LuaValue.valueOf("bios")));
		this.mainRoutine = this.coroutineCreate.call(program);
	}

	public void addAPI(ILuaAPI api)
	{
		this.apis.add(api);
		LuaTable table = wrapLuaObject(api);
		String[] names = api.getNames();
		for(int i = 0; i < names.length; i++)
		{
			this.globals.set(names[i], table);
		}
	}

	private void tryAbort() throws LuaError
	{
		String abortMessage = this.softAbortMessage;
		if(abortMessage != null)
		{
			this.softAbortMessage = null;
			this.hardAbortMessage = null;
			throw new LuaError(abortMessage);
		}
	}

	public void softAbort(String abortMessage)
	{
		this.softAbortMessage = abortMessage;
	}

	public void hardAbort(String abortMessage)
	{
		this.softAbortMessage = abortMessage;
		this.hardAbortMessage = abortMessage;
	}

	public void handleEvent(String name, Object[] args)
	{
		if(this.mainRoutine == null)
			return;
		if((this.eventFilter != null) && (name != null) && name.equals(this.eventFilter) && (!name.equals("terminate")))
			return;
		try
		{
			LuaValue[] resumeArgs;
			if(name != null)
			{
				resumeArgs = toValues(args, 2);
				resumeArgs[0] = this.mainRoutine;
				resumeArgs[1] = LuaValue.valueOf(name);
			}
			else
			{
				resumeArgs = new LuaValue[1];
				resumeArgs[0] = this.mainRoutine;
			}
			Varargs results = this.coroutineResume.invoke(LuaValue.varargsOf(resumeArgs));
			if(this.hardAbortMessage != null) { throw new LuaError(results.arg(2).checkstring().toString()); }
			if(!results.arg1().checkboolean()) { throw new LuaError(results.arg(2).checkstring().toString()); }
			LuaValue filter = results.arg(2);
			if(filter.isstring())
			{
				this.eventFilter = filter.toString();
			}
			else
			{
				this.eventFilter = null;
			}
			LuaThread thread = (LuaThread) this.mainRoutine;
			if(thread.getStatus().equals("dead"))
			{
				this.mainRoutine = null;
			}
		}
		catch(LuaError e)
		{
			this.mainRoutine = null;
		}
		finally
		{
			this.softAbortMessage = null;
			this.hardAbortMessage = null;
		}
	}

	public void unload()
	{
		if(mainRoutine != null)
		{
			this.mainRoutine = null;
		}
	}

	private LuaTable wrapLuaObject(final ILuaAPI api)
	{
		LuaTable table = new LuaTable();
		String[] methods = api.getMethodNames();
		for(int i = 0; i < methods.length; i++)
		{
			if(methods[i] != null)
			{
				final int method = i;
				table.set(methods[i], new VarArgFunction()
				{
					public Varargs invoke(Varargs args)
					{
						Computer.this.tryAbort();
						Object[] arguments = Computer.toObjects(args, 1);
						Object[] results = null;
						try
						{
							results = api.callMethod(new ILuaContext()
							{
								@Override
								public Object[] pullEvent(String s) throws Exception, InterruptedException
								{
									Object[] results = pullEventRaw(s);
									if((results.length >= 1) && (results[0].equals("terminate"))) { throw new Exception("Terminated"); }
									return results;
								}

								@Override
								public Object[] pullEventRaw(String s) throws InterruptedException
								{
									return yield(new Object[]
									{ s });
								}

								@Override
								public Object[] yield(Object[] yieldArgs) throws InterruptedException
								{
									try
									{
										LuaValue[] yieldValues = Computer.this.toValues(yieldArgs, 0);
										Varargs results = Computer.this.coroutineYield.invoke(LuaValue.varargsOf(yieldValues));
										return Computer.toObjects(results, 1);
									}
									catch(OrphanedThread e)
									{
										throw new InterruptedException();
									}
								}
							}, method, arguments);
						}
						catch(InterruptedException e)
						{
							throw new OrphanedThread();
						}
						catch(Exception e)
						{
							throw new LuaError(e.getMessage());
						}
						return LuaValue.varargsOf(Computer.this.toValues(results, 0));
					}
				});
			}
		}
		return table;
	}

	public void boot()
	{
		addAPI(new OSAPI(this));
		
		for(ILuaAPI api : apis)
		{
			api.startup();
		}
		
		loadBIOS(Computer.class.getResourceAsStream("/assets/scimachinery/lua/bios.lua"));
	}

	public void tick(double dt)
	{
		Iterator<ILuaAPI> it = this.apis.iterator();
		while(it.hasNext())
		{
			ILuaAPI api = it.next();
			api.advance(dt);
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

	private LuaValue toValue(Object obj)
	{
		if(obj == null)
			return LuaValue.NIL;
		else if(obj instanceof Number)
			return LuaValue.valueOf(((Number) obj).doubleValue());
		else if(obj instanceof Boolean)
			return LuaValue.valueOf(((Boolean) obj).booleanValue());
		else if(obj instanceof String)
			return LuaValue.valueOf((String) obj);
		else if(obj instanceof Map)
		{
			boolean clear = false;
			if(processingValue == null)
			{
				processingValue = new HashMap<Map<Object, Object>, LuaValue>();
				clear = true;
			}
			if(processingValue.containsKey(obj))
				return (LuaValue) processingValue.get(obj);

			LuaTable table = new LuaTable();
			processingValue.put((Map<Object, Object>) obj, table);

			Iterator<Entry<Object, Object>> it = ((Map<Object, Object>) obj).entrySet().iterator();
			while(it.hasNext())
			{
				Entry<Object, Object> pair = (Entry<Object, Object>) it.next();
				table.set(toValue(pair.getKey()), toValue(pair.getValue()));
			}
			if(clear)
				processingValue = null;
			return table;
		}
		else if(obj instanceof ILuaAPI) { return wrapLuaObject((ILuaAPI) obj); }
		System.out.println("SciMachinery: Could not convert object of type " + obj.getClass().getName() + " to LuaValue");
		return LuaValue.NIL;
	}

	private LuaValue[] toValues(Object[] objects, int e)
	{
		if((objects == null) || (objects.length == 0)) { return new LuaValue[e]; }
		LuaValue[] values = new LuaValue[objects.length + e];
		for(int i = 0; i < values.length; i++)
		{
			if(i < e)
				values[i] = null;
			else
				values[i] = toValue(objects[(i - e)]);
		}
		return values;
	}

	private static Object toObject(LuaValue value)
	{
		switch (value.type())
		{
		case -1:
		case 0:
			return null;
		case -2:
		case 3:
			return Double.valueOf(value.todouble());
		case 1:
			return Boolean.valueOf(value.toboolean());
		case 4:
			return value.toString();
		case 5:
			boolean clear = false;
			if(processing == null)
			{
				processing = new HashMap<LuaValue, Map<Object, Object>>();
				clear = true;
			}
			if((tree != null) && (tree.contains(value))) { return null; }
			if(processing.containsKey(value)) { return processing.get(value); }
			HashMap<Object, Object> ret = new HashMap<Object, Object>();

			processing.put((LuaTable) value, ret);

			boolean clearTree = false;
			if(tree == null)
			{
				tree = new ArrayList<LuaValue>();
				clearTree = true;
			}
			tree.add(value);

			LuaValue k = LuaValue.NIL;
			for(;;)
			{
				Varargs n = value.next(k);
				if((k = n.arg1()).isnil())
				{
					break;
				}
				LuaValue v = n.arg(2);

				Object key = toObject(k);
				Object val = toObject(v);
				if((key != null) && (val != null))
				{
					ret.put(key, val);
				}
			}
			tree.remove(value);
			if(clearTree)
			{
				tree = null;
			}
			if(clear)
			{
				processing = null;
			}
			return ret;
		}
		return null;
	}

	private static Object[] toObjects(Varargs values, int start)
	{
		int count = values.narg();
		Object[] objects = new Object[count - start + 1];
		for(int i = start; i < count; i++)
		{
			LuaValue value = values.arg(i);
			objects[i - start] = toObject(value);
		}
		return objects;
	}

	public void shutdown()
	{
		unload();
	}
}