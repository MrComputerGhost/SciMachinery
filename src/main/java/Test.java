import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;

public class Test
{
	public static void main(String[] args)
	{
		Globals globals = JsePlatform.debugGlobals();

		LuaValue assert_ = globals.get("assert");
		LuaValue loadString = globals.get("load");

		globals.set("collectgarbage", LuaValue.NIL);
		globals.set("dofile", LuaValue.NIL);
		globals.set("load", LuaValue.NIL);
		globals.set("loadfile", LuaValue.NIL);
		globals.set("module", LuaValue.NIL);
		globals.set("require", LuaValue.NIL);
		globals.set("package", LuaValue.NIL);
		globals.set("io", LuaValue.NIL);
		globals.set("os", LuaValue.NIL);
		globals.set("print", LuaValue.NIL);
		globals.set("luajava", LuaValue.NIL);
		globals.set("debug", LuaValue.NIL);
		globals.set("newproxy", LuaValue.NIL);

		LuaValue coroutine = globals.get("coroutine");
		LuaValue coroutineCreate = coroutine.get("create");
		LuaValue coroutineResume = coroutine.get("resume");
		LuaValue coroutineYield = coroutine.get("yield");

		try
		{
			Class.forName("org.apache.bcel.util.Repository");
			LuaJC.install(globals);
		}
		catch(ClassNotFoundException e)
		{
		}

		LuaTable table = new LuaTable();

		table.set("test", new VarArgFunction()
		{
			public Varargs invoke(Varargs args)
			{
				System.out.println(args.arg1());
				return LuaValue.varargsOf(new LuaValue[]
				{ LuaValue.NIL });
			}
		});

		globals.set("testAPI", table);

		System.out.println(loadString.call(LuaValue.valueOf("testAPI.test('test?')"), LuaValue.valueOf("bios")));

		LuaValue program = assert_.call(loadString.call(LuaValue.valueOf("testAPI.test('lol?')"), LuaValue.valueOf("bios")));
		program.call();

		while(true)
		{
		}
	}
}