package com.sci.machinery.block.computer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import com.sci.machinery.api.ILuaContext;
import com.sci.machinery.api.ILuaObject;
import com.sci.machinery.api.ILuaObject.APIMethod;

public final class LUALib
{
	private LUALib()
	{
	}

	public static LuaTable toLuaObject(final ILuaContext context, final ILuaObject obj)
	{
		LuaTable table = new LuaTable();
		for(final Method method : obj.getClass().getMethods())
		{
			if(method.isAnnotationPresent(APIMethod.class))
			{
				Class<?>[] params = method.getParameterTypes();
				if(params.length != 2)
					throw new IllegalArgumentException("Expected parameters ILuaContext, Object[]");
				if(!params[0].isAssignableFrom(ILuaContext.class))
					throw new IllegalArgumentException("First parameter of method must be ILuaContext");
				if(!params[1].isAssignableFrom(Object[].class))
					throw new IllegalArgumentException("Second parameter of method must be Object[]");

				table.set(method.getName(), new VarArgFunction()
				{
					@Override
					public Varargs invoke(Varargs args)
					{
						Object[] rrParams = new Object[]
						{ context, LuaJValues.toObjects(args, 1) };

						Object ret = null;
						try
						{
							ret = method.invoke(obj, rrParams);
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

						if(ret != null)
						{
							if(ret.getClass().isAssignableFrom(Object[].class))
								return LuaValue.varargsOf(LuaJValues.toValues((Object[]) ret, 0));
							else if(ret instanceof LuaValue)
								return LuaValue.varargsOf(new LuaValue[]
								{ (LuaValue) ret });
						}

						return LuaValue.varargsOf(new LuaValue[]
						{ LuaJValues.toValue(ret) });
					}
				});
			}
		}
		return table;
	}
}