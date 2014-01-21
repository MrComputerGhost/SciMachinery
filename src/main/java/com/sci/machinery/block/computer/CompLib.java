package com.sci.machinery.block.computer;

import java.io.File;
import java.io.IOException;
import net.minecraft.world.World;
import net.minecraft.world.storage.SaveHandler;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class CompLib
{
	private CompLib()
	{
	}
	
	public static File getSMCFolder(World world) throws IOException
	{
		SaveHandler sh = (SaveHandler) world.getSaveHandler();
		File ret = new File(sh.getWorldDirectory().getCanonicalFile(), "SciMachinery/Computer");
		if(!ret.exists())
			ret.mkdirs();
		return ret;
	}
}