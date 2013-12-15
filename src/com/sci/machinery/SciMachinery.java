package com.sci.machinery;

import java.io.File;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import com.sci.machinery.block.BlockDetectorTube;
import com.sci.machinery.block.BlockPumpTube;
import com.sci.machinery.block.BlockTube;
import com.sci.machinery.block.TileDetectorTube;
import com.sci.machinery.block.TilePumpTube;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.CreativeTabSM;
import com.sci.machinery.core.IProxy;
import com.sci.machinery.item.ItemEasterEgg;
import com.sci.machinery.lib.Reference;
import com.sci.machinery.network.PacketHandler;
import cpw.mods.fml.client.FMLClientHandler;
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
@NetworkMod(channels =
{ Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class SciMachinery
{
	@Instance(Reference.MOD_ID)
	public static SciMachinery instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;	

	public static CreativeTabs tab = new CreativeTabSM(CreativeTabs.getNextID(), Reference.MOD_ID);

	public int easterEggId;
	public ItemEasterEgg easterEgg;
	
	public int tubeId;
	public BlockTube tube;	

	public int pumpTubeId;		
	public BlockPumpTube pumpTube;
		
	public int detectorTubeId;	
	public BlockDetectorTube detectorTube;
	
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
			pumpTubeId = cfg.getBlock("pumpTube", 421).getInt();
			detectorTubeId = cfg.getBlock("detectorTube", 422).getInt();
			
			easterEggId = cfg.getItem("easterEgg", 423).getInt();
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
 
		easterEgg = new ItemEasterEgg(easterEggId);
		
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(easterEgg), 1, 1, 1));
		
		tube = new BlockTube(tubeId);
		GameRegistry.registerBlock(tube, "SciMachinery_TileTube");
		GameRegistry.registerTileEntity(TileTube.class, "SciMachinery_TileTube");

		pumpTube = new BlockPumpTube(pumpTubeId);
		GameRegistry.registerBlock(pumpTube, "SciMachinery_TilePumpTube");
		GameRegistry.registerTileEntity(TilePumpTube.class, "SciMachinery_TilePumpTube");

		detectorTube = new BlockDetectorTube(detectorTubeId);
		GameRegistry.registerBlock(detectorTube, "SciMachinery_TileDetectorTube");
		GameRegistry.registerTileEntity(TileDetectorTube.class, "SciMachinery_TileDetectorTube");

		GameRegistry.addRecipe(new ItemStack(tube, 16), new Object[]
		{ "sss", "gpg", "sss", 's', Block.stoneSingleSlab, 'g', Block.glass, 'p', Block.pistonBase });
		GameRegistry.addRecipe(new ItemStack(pumpTube, 4), new Object[]
		{ "rhr", "hth", "rhr", 'r', Item.redstone, 'h', Block.hopperBlock, 't', tube });
		GameRegistry.addRecipe(new ItemStack(detectorTube, 3), new Object[]	
		{ "rgr", "ttt", "rgr", 'r', Item.redstone, 'g', Item.ingotGold, 't', tube });
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}

	@ForgeSubscribe
	public void onPlayStreaming(PlayStreamingEvent event)
	{
		if(event.name.equals("chicken"))
		{
			FMLClientHandler.instance().getClient().sndManager.playStreaming("scimachinery:chicken", event.x + 0.5F, event.y + 0.5F, event.z + 0.5F);
		}
	}

	@ForgeSubscribe
	public void loadSounds(SoundLoadEvent e)
	{
		e.manager.soundPoolStreaming.addSound("scimachinery:chicken.ogg");
	}
}