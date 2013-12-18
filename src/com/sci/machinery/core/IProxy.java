package com.sci.machinery.core;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public interface IProxy
{
	public void init(FMLInitializationEvent e);

	public void postInit(FMLPostInitializationEvent e);

	public void preInit(FMLPreInitializationEvent e);
}