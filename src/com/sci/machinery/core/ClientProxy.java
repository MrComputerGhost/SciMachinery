package com.sci.machinery.core;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.render.RenderTube;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * SciMachinery
 *
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class ClientProxy extends CommonProxy
{
	public RenderTube tubeRenderer;

	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
		
		MinecraftForge.EVENT_BUS.register(SciMachinery.instance);
		
		tubeRenderer = new RenderTube();
		ClientRegistry.bindTileEntitySpecialRenderer(TileTube.class, tubeRenderer);
		
		MinecraftForgeClient.registerItemRenderer(SciMachinery.instance.stoneTubeId, tubeRenderer);
		MinecraftForgeClient.registerItemRenderer(SciMachinery.instance.cobbleTubeId, tubeRenderer);
		MinecraftForgeClient.registerItemRenderer(SciMachinery.instance.pumpTubeId, tubeRenderer);
		MinecraftForgeClient.registerItemRenderer(SciMachinery.instance.detectorTubeId, tubeRenderer);
		MinecraftForgeClient.registerItemRenderer(SciMachinery.instance.voidTubeId, tubeRenderer);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}