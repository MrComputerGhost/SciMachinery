package com.sci.machinery.core;

import net.minecraftforge.client.MinecraftForgeClient;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.block.TilePumpTube;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.render.RenderTube;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
		tubeRenderer = new RenderTube();
		ClientRegistry.bindTileEntitySpecialRenderer(TileTube.class, tubeRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TilePumpTube.class, tubeRenderer);
		
		MinecraftForgeClient.registerItemRenderer(SciMachinery.instance.tubeId, tubeRenderer);
		MinecraftForgeClient.registerItemRenderer(SciMachinery.instance.pumpTubeId, tubeRenderer);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}