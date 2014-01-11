package com.sci.machinery.block.computer;

import java.io.File;
import java.util.BitSet;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class CompLib
{
	private CompLib(){}
	
	private static int nextID = 1;
	private static BitSet usedIDs = new BitSet();
	
	public static int assignID()
	{
		int id = usedIDs.nextClearBit(nextID);
		nextID = id + 1;
		usedIDs.set(id);
		return id;
	}

	public static void stealID(int id) 
	{
		if(usedIDs.get(id))
			throw new RuntimeException("Cannot steal id " + id);
		
		usedIDs.set(id);
		nextID = usedIDs.nextClearBit(0);
	}
	
	public static void releaseID(int id)
	{
		usedIDs.clear(id);
		if(id < nextID)
			nextID = id;
	}
	
	public static File getSMCFolder(World world)
	{
		return new File(getWorldFolder(world), "SciMachinery/Computer");
	}
	
	public static File getWorldFolder(World world)
	{
		return new File(FMLCommonHandler.instance().getMinecraftServerInstance().getFile("."), DimensionManager.getWorld(0).getSaveHandler().getWorldDirectoryName());
	}
}