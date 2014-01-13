package com.sci.machinery.block.computer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public final class LuaJValues
{
	private LuaJValues()
	{
	}

	private static HashMap<Map<Object, Object>, LuaValue> processingValue;
	private static HashMap<Object, Object> processing;
	private static ArrayList<LuaValue> tree;

	@SuppressWarnings("unchecked")
	public LuaValue toValue(Object object)
	{
		if(object == null) { return LuaValue.NIL; }
		if((object instanceof Number))
		{
			double n = ((Number) object).doubleValue();
			return LuaValue.valueOf(n);
		}
		if((object instanceof Boolean))
		{
			boolean b = ((Boolean) object).booleanValue();
			return LuaValue.valueOf(b);
		}
		if((object instanceof String))
		{
			String s = (String) object;
			return LuaValue.valueOf(s);
		}
		if((object instanceof Map))
		{
			boolean clearProcessing = false;
			if(processingValue == null)
			{
				processingValue = new HashMap<Map<Object, Object>, LuaValue>();
				clearProcessing = true;
			}
			if(processingValue.containsKey(object)) { return (LuaValue) processingValue.get(object); }
			LuaValue table = new LuaTable();
			processingValue.put((Map<Object, Object>) object, table);

			Iterator<Entry<Object, Object>> it = ((Map<Object, Object>) object).entrySet().iterator();
			while(it.hasNext())
			{
				Entry<Object, Object> pair = (Entry<Object, Object>) it.next();
				table.set(toValue(pair.getKey()), toValue(pair.getValue()));
			}
			if(clearProcessing)
			{
				processingValue = null;
			}
			return table;
		}
		return LuaValue.NIL;
	}

	public LuaValue[] toValues(Object[] objects, int leaveEmpty)
	{
		if((objects == null) || (objects.length == 0)) { return new LuaValue[leaveEmpty]; }
		LuaValue[] values = new LuaValue[objects.length + leaveEmpty];
		for(int i = 0; i < values.length; i++)
		{
			if(i < leaveEmpty)
			{
				values[i] = null;
			}
			else
			{
				Object object = objects[(i - leaveEmpty)];
				values[i] = toValue(object);
			}
		}
		return values;
	}

	public static Object toObject(LuaValue value)
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
			boolean clearProcessing = false;
			if(processing == null)
			{
				processing = new HashMap<Object, Object>();
				clearProcessing = true;
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
			if(clearProcessing)
			{
				processing = null;
			}
			return ret;
		}
		return null;
	}

	public static Object[] toObjects(Varargs values, int startIdx)
	{
		int count = values.narg();
		Object[] objects = new Object[count - startIdx + 1];
		for(int n = startIdx; n <= count; n++)
		{
			int i = n - startIdx;
			LuaValue value = values.arg(n);
			objects[i] = toObject(value);
		}
		return objects;
	}
}