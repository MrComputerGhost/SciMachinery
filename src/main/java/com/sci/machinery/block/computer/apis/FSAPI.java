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
import com.sci.machinery.api.ILuaContext;
import com.sci.machinery.api.ILuaObject;
import com.sci.machinery.block.computer.Computer;
import com.sci.machinery.block.computer.LUALib;

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
				return wrapBufferedReader(computer, new BufferedReader(new FileReader(file)));
			case W:
				return wrapBufferedWriter(computer, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false))));
			case A:
				return wrapBufferedWriter(computer, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true))));
			case RB:
				return wrapInputStream(computer, new FileInputStream(file));
			case WB:
				return wrapOutputStream(computer, new FileOutputStream(file, false));
			case AB:
				return wrapOutputStream(computer, new FileOutputStream(file, true));
			default:
				throw new Exception("Unsupported mode");
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	private static LuaTable wrapBufferedReader(final ILuaContext context, final BufferedReader reader)
	{
		return LUALib.toLuaObject(context, new ILuaObject()
		{
			@APIMethod
			public LuaValue readLine(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
				try
				{
					return LuaValue.valueOf(reader.readLine());
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}

			@APIMethod
			public LuaValue readAll(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
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

			@APIMethod
			public LuaValue close(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
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
	}

	private static LuaTable wrapBufferedWriter(final ILuaContext context, final BufferedWriter writer)
	{
		return LUALib.toLuaObject(context, new ILuaObject()
		{
			@APIMethod
			public LuaValue write(ILuaContext context, Object[] args)
			{
				if(args.length != 1)
					throw new IllegalArgumentException("Exptected arguments: string");
				String text = args[0].toString();
				try
				{
					writer.write(text, 0, text.length());
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}

			@APIMethod
			public LuaValue writeLine(ILuaContext context, Object[] args)
			{
				if(args.length != 1)
					throw new IllegalArgumentException("Exptected arguments: string");
				String text = args[0].toString();
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

			@APIMethod
			public LuaValue flush(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
				try
				{
					writer.flush();
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}

			@APIMethod
			public LuaValue close(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
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
	}

	private static LuaTable wrapInputStream(final ILuaContext context, final InputStream in)
	{
		return LUALib.toLuaObject(context, new ILuaObject()
		{
			@APIMethod
			public LuaValue read(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
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

			@APIMethod
			public LuaValue close(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
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
	}

	private static LuaTable wrapOutputStream(final ILuaContext context, final OutputStream out)
	{
		return LUALib.toLuaObject(context, new ILuaObject()
		{
			@APIMethod
			public LuaValue write(ILuaContext context, Object[] args)
			{
				if(args.length != 1)
					throw new IllegalArgumentException("Expected argument: int");

				try
				{
					out.write(Integer.valueOf(String.valueOf(args[0])));
				}
				catch(IOException e)
				{
				}
				return LuaValue.NIL;
			}

			@APIMethod
			public LuaValue flush(ILuaContext context, Object[] args)
			{
				if(args.length != 0)
					throw new IllegalArgumentException("Too many arguments!");
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
	}

	private enum FileMode
	{
		R, W, A, RB, WB, AB
	}
}