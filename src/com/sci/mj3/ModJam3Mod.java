package com.sci.mj3;

import java.io.File;
import net.minecraftforge.common.Configuration;
import com.sci.mj3.core.IProxy;
import com.sci.mj3.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModJam3Mod
{
	@Instance(Reference.MOD_ID)
	public static ModJam3Mod instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		proxy.preInit(e);
		File folder = new File(e.getModConfigurationDirectory(), "sci4me");
		if(!folder.exists())
			folder.mkdirs();
		Configuration cfg = new Configuration(new File(folder, "machinery.cfg"));
		try
		{
			cfg.load();
		}
		finally
		{
			cfg.save();
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}
}