import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;
import com.sci.machinery.block.computer.Computer;
import com.sci.machinery.block.computer.api.OSAPI;
import com.sci.machinery.block.computer.api.OSAPI.OSAPIMethod;

public class Test
{
	public static void main(String[] args)
	{
		LuaValue globals = JsePlatform.debugGlobals();

		LuaValue assert_ = globals.get("assert");
		LuaValue loadString = globals.get("loadstring");

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
			LuaJC.install();
		}
		catch(ClassNotFoundException e)
		{
		}

		OSAPI.registerMethods(Test.class);
		OSAPI.install(globals);

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
			throw new LuaError("Could not read file");
		}

		LuaValue program = assert_.call(loadString.call(LuaValue.valueOf(bios), LuaValue.valueOf("bios")));
		program.call();
	}

	@OSAPIMethod(apiMain = "test")
	public static void println(String str)
	{
		System.out.println(str);
	}
}