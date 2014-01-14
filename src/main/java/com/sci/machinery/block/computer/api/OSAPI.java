package com.sci.machinery.block.computer.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import com.sci.machinery.block.computer.LuaJValues;

/**
 * SciMachinery
 * 
 * @author hawks008
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class OSAPI
{
	private static final Map<String, Map<String, Method>> apiList = new HashMap<String, Map<String, Method>>();

	public static void registerMethods(Class<?>... clazzes) throws IllegalArgumentException
	{
		for(Class<?> clazz : clazzes)
		{
			for(Method method : clazz.getMethods())
			{
				if(method.isAnnotationPresent(OSAPIMethod.class))
				{
					String main = method.getAnnotation(OSAPIMethod.class).apiMain();
					if(!main.isEmpty())
					{
						Map<String, Method> methodList = apiList.get(main);
						if(methodList == null)
						{
							methodList = new HashMap<String, Method>();
						}
						methodList.put(method.getName().toLowerCase(), method);
						apiList.put(main, methodList);
					}
					else
					{
						throw new IllegalArgumentException("You must specifiy a method main for System API Methods");
					}
				}
			}
		}
	}

	public static void install(LuaValue g)
	{
		Iterator<String> apis = OSAPI.apiList.keySet().iterator();
		while(apis.hasNext())
		{
			String api = apis.next();
			Map<String, Method> apiMethods = OSAPI.apiList.get(api);
			LuaTable table = new LuaTable();

			Iterator<String> methods = apiMethods.keySet().iterator();
			while(methods.hasNext())
			{
				final String methodName = methods.next();
				final Method method = apiMethods.get(methodName);
				table.set(methodName, new VarArgFunction()
				{
					public Varargs invoke(Varargs args)
					{
						Object[] rParams = LuaJValues.toObjects(args, 1);

						Object ret = null;
						try
						{
							ret = method.invoke(null, rParams);
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

			g.set(api, table);
		}
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface OSAPIMethod
	{
		String apiMain() default "";
	}
}