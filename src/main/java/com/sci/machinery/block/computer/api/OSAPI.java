package com.sci.machinery.block.computer.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * SciMachinery
 * 
 * @author hawks008
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class OSAPI
{
	public static final Map<String, Map<String, Method>> apiList = new HashMap<String, Map<String, Method>>();

	public static void registerMethods(Class<?>... clazzes) throws IllegalArgumentException
	{
		for(Class<?> clazz : clazzes)
		{
			System.out.println("Regestering " + clazz.getName());
			for(Method method : clazz.getMethods())
			{
				System.out.println("Looking at " + method.getName());
				if(method.isAnnotationPresent(OSAPIMethod.class))
				{
					String main = method.getAnnotation(OSAPIMethod.class).apiMain();
					System.out.println("Accepted " + method.getName() + " with main='" + main + "'");
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
	
	public static String runMethod(String name)
	{
		return "";
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface OSAPIMethod
	{
		String apiMain() default "";
	}
}