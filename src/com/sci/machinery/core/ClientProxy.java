package com.sci.machinery.core;

import com.sci.machinery.block.TileTube;
import com.sci.machinery.render.RenderTube;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
		ClientRegistry.bindTileEntitySpecialRenderer(TileTube.class, new RenderTube());
	}

	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}