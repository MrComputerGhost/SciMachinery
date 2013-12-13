package com.sci.machinery;

import java.io.File;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import com.sci.machinery.block.BlockTube;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.CreativeTabSM;
import com.sci.machinery.core.IProxy;
import com.sci.machinery.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SciMachinery
{
	@Instance(Reference.MOD_ID)
	public static SciMachinery instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;

	public static CreativeTabs tab = new CreativeTabSM(CreativeTabs.getNextID(), Reference.MOD_ID);
	
	public int tubeId;
	public BlockTube tube;
	
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
			tubeId = cfg.getBlock("tube", 420).getInt();
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
		
		tube = new BlockTube(tubeId);
		GameRegistry.registerTileEntity(TileTube.class, "SciMachinery_TileTube");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}
}