package com.sci.machinery.block.computer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.SaveHandler;
import com.sci.machinery.lib.Reference;

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

	public static UUID cpuidFromStack(ItemStack stack)
	{
		return cpuidFromTag(stack.getTagCompound());
	}

	public static UUID cpuidFromTag(NBTTagCompound tag)
	{
		return cpuidFromString(tag.getString(Reference.NBT_CPUID_TAG));
	}

	public static UUID cpuidFromString(String str)
	{
		return UUID.fromString(str.replaceAll("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
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