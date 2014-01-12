package com.sci.machinery;

import java.io.File;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.event.ForgeSubscribe;
import com.sci.machinery.api.IProxy;
import com.sci.machinery.api.IRecipeRegistry;
import com.sci.machinery.block.BlockCircuitMaker;
import com.sci.machinery.block.TileCircuitMaker;
import com.sci.machinery.block.computer.BlockComputer;
import com.sci.machinery.block.computer.GUIComputerTerminal;
import com.sci.machinery.block.computer.TileEntityComputer;
import com.sci.machinery.block.tube.BlockTube;
import com.sci.machinery.block.tube.TileTube;
import com.sci.machinery.block.tube.TubeBase;
import com.sci.machinery.block.tube.TubeModifier;
import com.sci.machinery.core.CircuitMakerRecipe;
import com.sci.machinery.core.CircuitMakerRegistry;
import com.sci.machinery.core.CreativeTabSM;
import com.sci.machinery.gui.ContainerCircuitMaker;
import com.sci.machinery.gui.GUICircuitMaker;
import com.sci.machinery.item.ItemCircuit;
import com.sci.machinery.item.ItemEasterEgg;
import com.sci.machinery.item.ItemSuicide;
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
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME)
@NetworkMod(channels =
{ Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class SciMachinery implements IGuiHandler
{
	@Instance(Reference.MOD_ID)
	public static SciMachinery instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;

	public static CreativeTabs tab = new CreativeTabSM(CreativeTabs.getNextID(), Reference.MOD_ID);

	public ItemEasterEgg easterEgg;
	public int easterEggId;

	public BlockTube cobbleTube;
	public int cobbleTubeId;

	public BlockTube detectorTube;
	public int detectorTubeId;

	public int fastCobbleId;
	public BlockTube fastCobbleTube;

	public int fastStoneId;
	public BlockTube fastStoneTube;

	public BlockTube pumpTube;
	public int pumpTubeId;

	public BlockTube stoneTube;
	public int stoneTubeId;

	public BlockTube voidTube;
	public int voidTubeId;

	public BlockTube tubeValve;
	public int valveTubeId;

	public BlockCircuitMaker circuitMaker;
	public int circuitMakerId;

	public ItemCircuit circuit;
	public int circuitId;

	public BlockComputer computer;
	public int computerId;

	public ItemSuicide suicide;
	public int suicideId;

	public IRecipeRegistry circuitMakerRegistry;

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init(e);

		circuitMakerRegistry = new CircuitMakerRegistry();

		circuit = new ItemCircuit(circuitId);
		GameRegistry.registerItem(circuit, "SciMachinery-Circuit");

		NetworkRegistry.instance().registerGuiHandler(instance, instance);

		easterEgg = new ItemEasterEgg(easterEggId);
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(easterEgg), 1, 1, 1));

		GameRegistry.registerTileEntity(TileTube.class, "SciMachinery_TileTube");
		GameRegistry.registerTileEntity(TileCircuitMaker.class, "SciMachinery_TileCircuitMaker");
		GameRegistry.registerTileEntity(TileEntityComputer.class, "SciMachinery_TileComputer");

		stoneTube = new BlockTube(stoneTubeId, TubeBase.STONE);
		stoneTube.setUnlocalizedName("stoneTube");
		GameRegistry.registerBlock(stoneTube, "SciMachinery_TileStoneTube");

		cobbleTube = new BlockTube(cobbleTubeId, TubeBase.COBBLE, TubeModifier.SPEED_DOWN);
		cobbleTube.setUnlocalizedName("cobbleTube");
		GameRegistry.registerBlock(cobbleTube, "SciMachinery_TileCobbleTube");

		fastStoneTube = new BlockTube(fastStoneId, TubeBase.STONE, TubeModifier.SPEED_UP);
		fastStoneTube.setUnlocalizedName("fastStoneTube");
		GameRegistry.registerBlock(fastStoneTube, "SciMachinery_TileFastStoneTube");

		fastCobbleTube = new BlockTube(fastCobbleId, TubeBase.COBBLE, TubeModifier.SPEED_NORMAL);
		fastCobbleTube.setUnlocalizedName("fastCobbleTube");
		GameRegistry.registerBlock(fastCobbleTube, "SciMachinery_TileFastCobbleTube");

		pumpTube = new BlockTube(pumpTubeId, TubeBase.PUMP);
		pumpTube.setUnlocalizedName("pumpTube");
		GameRegistry.registerBlock(pumpTube, "SciMachinery_TilePumpTube");

		detectorTube = new BlockTube(detectorTubeId, TubeBase.DETECTOR);
		detectorTube.setUnlocalizedName("detectorTube");
		GameRegistry.registerBlock(detectorTube, "SciMachinery_TileDetectorTube");

		voidTube = new BlockTube(voidTubeId, TubeBase.VOID);
		voidTube.setUnlocalizedName("voidTube");
		GameRegistry.registerBlock(voidTube, "SciMachinery_TileVoidTube");

		tubeValve = new BlockTube(valveTubeId, TubeBase.VALVE);
		tubeValve.setUnlocalizedName("valveTube");
		GameRegistry.registerBlock(tubeValve, "SciMachinery_TileValveTube");

		circuitMaker = new BlockCircuitMaker(circuitMakerId);
		circuitMaker.setUnlocalizedName("circuitMaker");
		GameRegistry.registerBlock(circuitMaker, "SciMachinery_TileCircuitMaker");

		computer = new BlockComputer(computerId);
		computer.setUnlocalizedName("computer");
		GameRegistry.registerBlock(computer, "SciMachinery_TileComputer");

		suicide = new ItemSuicide(suicideId);

		GameRegistry.addRecipe(new ItemStack(stoneTube, 16), new Object[]
		{ "sss", "gpg", "sss", 's', Block.stone, 'g', Block.glass, 'p', Block.pistonBase });
		GameRegistry.addRecipe(new ItemStack(cobbleTube, 16), new Object[]
		{ "sss", "gpg", "sss", 's', Block.cobblestone, 'g', Block.glass, 'p', Block.pistonBase });
		GameRegistry.addRecipe(new ItemStack(pumpTube, 4), new Object[]
		{ "rhr", "hth", "rhr", 'r', Item.redstone, 'h', Block.hopperBlock, 't', stoneTube });
		GameRegistry.addRecipe(new ItemStack(detectorTube, 3), new Object[]
		{ "rgr", "ttt", "rgr", 'r', Item.redstone, 'g', Item.ingotGold, 't', stoneTube });
		GameRegistry.addRecipe(new ItemStack(voidTube, 1), new Object[]
		{ "lgl", "epe", "lgl", 'l', new ItemStack(Item.dyePowder, 4), 'g', Item.glowstone, 'e', Item.enderPearl, 'p', stoneTube });

		GameRegistry.addRecipe(new ItemStack(circuitMaker), new Object[]
		{ "ici", "ipi", "iri", 'i', Item.ingotIron, 'c', Item.comparator, 'p', Block.pistonBase, 'r', Item.redstone });

		GameRegistry.addRecipe(new ItemStack(suicide), new Object[]
		{ "cfc", "cfc", "csc", 'c', Block.cobblestone, 'f', Item.flint, 's', Item.stick });

		GameRegistry.addShapelessRecipe(new ItemStack(fastStoneTube), new Object[]
		{ new ItemStack(Item.ingotGold, 1), new ItemStack(fastStoneTube, 1) });
		GameRegistry.addShapelessRecipe(new ItemStack(fastCobbleTube), new Object[]
		{ new ItemStack(Item.ingotGold, 1), new ItemStack(fastStoneTube, 1) });

		GameRegistry.addShapelessRecipe(new ItemStack(tubeValve), new Object[]
		{ new ItemStack(Item.redstone, 1), new ItemStack(stoneTube, 1) });
		GameRegistry.addShapelessRecipe(new ItemStack(tubeValve), new Object[]
		{ new ItemStack(Item.redstone, 1), new ItemStack(cobbleTube, 1) });

		ItemStack[] stacks = new ItemStack[15];
		stacks[0] = new ItemStack(Item.redstone);
		stacks[1] = new ItemStack(Item.redstone);
		stacks[2] = new ItemStack(Item.redstone);
		stacks[3] = new ItemStack(Item.redstone);
		stacks[4] = new ItemStack(Item.redstone);

		stacks[5] = new ItemStack(Item.ingotIron);
		stacks[6] = new ItemStack(Item.dyePowder, 1, 4);
		stacks[7] = new ItemStack(Item.dyePowder, 1, 4);
		stacks[8] = new ItemStack(Item.dyePowder, 1, 4);
		stacks[9] = new ItemStack(Item.ingotIron);

		stacks[10] = new ItemStack(Item.redstone);
		stacks[11] = new ItemStack(Item.redstone);
		stacks[12] = new ItemStack(Item.redstone);
		stacks[13] = new ItemStack(Item.redstone);
		stacks[14] = new ItemStack(Item.redstone);
		circuitMakerRegistry.registerRecipe(new CircuitMakerRecipe(100, new ItemStack(circuit, 1, 0), stacks));

		stacks = new ItemStack[15];
		stacks[0] = new ItemStack(Item.redstone);
		stacks[1] = new ItemStack(Item.redstone);
		stacks[2] = new ItemStack(Item.redstone);
		stacks[3] = new ItemStack(Item.redstone);
		stacks[4] = new ItemStack(Item.redstone);

		stacks[5] = new ItemStack(Item.ingotIron);
		stacks[6] = new ItemStack(Item.dyePowder, 1, 4);
		stacks[7] = new ItemStack(Item.pocketSundial);
		stacks[8] = new ItemStack(Item.dyePowder, 1, 4);
		stacks[9] = new ItemStack(Item.ingotIron);

		stacks[10] = new ItemStack(Item.redstone);
		stacks[11] = new ItemStack(Item.redstone);
		stacks[12] = new ItemStack(Item.redstone);
		stacks[13] = new ItemStack(Item.redstone);
		stacks[14] = new ItemStack(Item.redstone);
		circuitMakerRegistry.registerRecipe(new CircuitMakerRecipe(120, new ItemStack(circuit, 1, 1), stacks));

		stacks = new ItemStack[15];
		stacks[0] = new ItemStack(Item.redstone);
		stacks[1] = new ItemStack(Item.redstone);
		stacks[2] = new ItemStack(Item.redstone);
		stacks[3] = new ItemStack(Item.redstone);
		stacks[4] = new ItemStack(Item.redstone);

		stacks[5] = new ItemStack(Item.ingotIron);
		stacks[6] = new ItemStack(Item.comparator);
		stacks[7] = new ItemStack(circuit, 1, 0);
		stacks[8] = new ItemStack(Item.comparator);
		stacks[9] = new ItemStack(Item.ingotIron);

		stacks[10] = new ItemStack(Item.redstone);
		stacks[11] = new ItemStack(Item.redstone);
		stacks[12] = new ItemStack(Item.redstone);
		stacks[13] = new ItemStack(Item.redstone);
		stacks[14] = new ItemStack(Item.redstone);
		circuitMakerRegistry.registerRecipe(new CircuitMakerRecipe(120, new ItemStack(circuit, 1, 2), stacks));

		stacks = new ItemStack[15];
		stacks[0] = new ItemStack(Item.redstone);
		stacks[1] = new ItemStack(Item.redstone);
		stacks[2] = new ItemStack(Item.redstone);
		stacks[3] = new ItemStack(Item.redstone);
		stacks[4] = new ItemStack(Item.redstone);

		stacks[5] = new ItemStack(Item.ingotIron);
		stacks[6] = new ItemStack(Item.redstoneRepeater);
		stacks[7] = new ItemStack(circuit, 1, 0);
		stacks[8] = new ItemStack(Item.redstoneRepeater);
		stacks[9] = new ItemStack(Item.ingotIron);

		stacks[10] = new ItemStack(Item.redstone);
		stacks[11] = new ItemStack(Item.redstone);
		stacks[12] = new ItemStack(Item.redstone);
		stacks[13] = new ItemStack(Item.redstone);
		stacks[14] = new ItemStack(Item.redstone);
		circuitMakerRegistry.registerRecipe(new CircuitMakerRecipe(120, new ItemStack(circuit, 1, 3), stacks));

		stacks = new ItemStack[15];
		stacks[0] = new ItemStack(Item.redstone);
		stacks[1] = new ItemStack(Item.redstone);
		stacks[2] = new ItemStack(Item.redstone);
		stacks[3] = new ItemStack(Item.redstone);
		stacks[4] = new ItemStack(Item.redstone);

		stacks[5] = new ItemStack(Item.ingotIron);
		stacks[6] = new ItemStack(circuit, 1, 1);
		stacks[7] = new ItemStack(circuit, 1, 2);
		stacks[8] = new ItemStack(circuit, 1, 3);
		stacks[9] = new ItemStack(Item.ingotIron);

		stacks[10] = new ItemStack(Item.redstone);
		stacks[11] = new ItemStack(Item.redstone);
		stacks[12] = new ItemStack(Item.redstone);
		stacks[13] = new ItemStack(Item.redstone);
		stacks[14] = new ItemStack(Item.redstone);
		circuitMakerRegistry.registerRecipe(new CircuitMakerRecipe(120, new ItemStack(circuit, 1, 4), stacks));
	}

	@ForgeSubscribe
	public void loadSounds(SoundLoadEvent e)
	{
		e.manager.soundPoolStreaming.addSound("scimachinery:chicken.ogg");
	}

	@ForgeSubscribe
	public void onPlayStreaming(PlayStreamingEvent event)
	{
		if(event.name.equals("chicken"))
		{
			FMLClientHandler.instance().getClient().sndManager.playStreaming("scimachinery:chicken", event.x + 0.5F, event.y + 0.5F, event.z + 0.5F);
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		e.getModMetadata().version = Reference.MOD_VERSION;

		proxy.preInit(e);

		File folder = new File(e.getModConfigurationDirectory(), "sci4me");
		if(!folder.exists())
			folder.mkdirs();
		Configuration cfg = new Configuration(new File(folder, "machinery.cfg"));
		try
		{
			cfg.load();
			easterEggId = cfg.getItem("easterEgg", 419).getInt();

			stoneTubeId = cfg.getBlock("stoneTube", 420).getInt();
			cobbleTubeId = cfg.getBlock("cobbleTube", 424).getInt();
			pumpTubeId = cfg.getBlock("pumpTube", 421).getInt();
			detectorTubeId = cfg.getBlock("detectorTube", 422).getInt();
			voidTubeId = cfg.getBlock("voidTube", 423).getInt();

			fastStoneId = cfg.getBlock("fastStoneTube", 425).getInt();
			fastCobbleId = cfg.getBlock("fastCobbleTube", 426).getInt();

			valveTubeId = cfg.getBlock("valveTube", 427).getInt();

			circuitMakerId = cfg.getBlock("circuitMaker", 428).getInt();
			circuitId = cfg.getItem("circuit", 429).getInt();

			computerId = cfg.getBlock("computer", 430).getInt();

			suicideId = cfg.getItem("suicide", 431).getInt();
		}
		finally
		{
			cfg.save();
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileCircuitMaker) { return new ContainerCircuitMaker(player.inventory, (TileCircuitMaker) tileEntity); }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileCircuitMaker) { return new GUICircuitMaker(player.inventory, (TileCircuitMaker) tileEntity); }
		if(tileEntity instanceof TileEntityComputer) { return new GUIComputerTerminal((TileEntityComputer) tileEntity); }
		return null;
	}
}