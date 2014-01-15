package com.sci.machinery.block.computer.apis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import com.sci.machinery.api.ILuaContext;
import com.sci.machinery.block.computer.Computer;

public class FSAPI extends LuaAPI
{
	public FSAPI(Computer computer)
	{
		super(computer);
	}

	@Override
	public void onStartup()
	{

	}

	@Override
	public void onShutdown()
	{

	}

	@Override
	public void tick()
	{

	}

	@Override
	public String getName()
	{
		return "fs";
	}

	private enum FileMode
	{
		R, W, A, RB, WB, AB
	}

	@APIMethod
	public LuaTable open(ILuaContext context, Object[] args)
	{
		if(args.length != 2)
			throw new IllegalArgumentException("Expected arguments path, mode");
		try
		{
			String path = computer.getRoot().getCanonicalPath() + "/" + args[0];
			FileMode mode = null;
			try
			{
				mode = FileMode.valueOf(((String) args[1]).toUpperCase());
			}
			catch(Exception e)
			{
				throw new Exception("Unsupported mode");
			}
			File file = new File(path);

			switch (mode)
			{
			case R:
				return wrapBufferedReader(new BufferedReader(new FileReader(file)));
			case W:
				return wrapBufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false))));
			case A:
				return wrapBufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true))));
			case RB:
				return wrapInputStream(new FileInputStream(file));
			case WB:
				return wrapOutputStream(new FileOutputStream(file, false));
			case AB:
				return wrapOutputStream(new FileOutputStream(file, true));
			default:
				throw new Exception("Unsupported mode");
			}

		}
		catch(Exception e)
		{
		}
		return null;
	}

	private static LuaTable wrapBufferedReader(final BufferedReader reader)
	{
		LuaTable table = new LuaTable();

		table.set("readLine", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					return LuaValue.valueOf(reader.readLine());
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});
		table.set("readAll", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					StringBuilder sb = new StringBuilder();
					String line = reader.readLine();
					while(line != null)
					{
						sb.append(line);
						line = reader.readLine();
						if(line != null)
							sb.append('\n');
					}
					return LuaValue.valueOf(sb.toString());
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});
		table.set("close", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					reader.close();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		return table;
	}

	private static LuaTable wrapBufferedWriter(final BufferedWriter writer)
	{
		LuaTable table = new LuaTable();

		table.set("write", new OneArgFunction()
		{
			@Override
			public LuaValue call(LuaValue arg)
			{
				String text = arg.toString();
				try
				{
					writer.write(text, 0, text.length());
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		table.set("writeLine", new OneArgFunction()
		{
			@Override
			public LuaValue call(LuaValue arg)
			{
				String text = arg.toString();
				try
				{
					writer.write(text, 0, text.length());
					writer.newLine();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		table.set("flush", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					writer.flush();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		table.set("close", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					writer.close();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		return table;
	}

	private static LuaTable wrapInputStream(final InputStream in)
	{
		LuaTable table = new LuaTable();

		table.set("read", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				int b;
				try
				{
					b = in.read();
					if(b != -1)
						return LuaValue.valueOf(b);
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		table.set("close", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					in.close();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		return table;
	}

	private static LuaTable wrapOutputStream(final OutputStream out)
	{
		LuaTable table = new LuaTable();

		table.set("write", new OneArgFunction()
		{
			@Override
			public LuaValue call(LuaValue arg)
			{
				try
				{
					out.write(arg.checkint());
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		table.set("flush", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					out.flush();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		table.set("close", new ZeroArgFunction()
		{
			@Override
			public LuaValue call()
			{
				try
				{
					out.close();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}
		});

		return table;
	}
}