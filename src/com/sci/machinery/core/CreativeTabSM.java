package com.sci.machinery.core;

import net.minecraft.creativetab.CreativeTabs;
import com.sci.machinery.SciMachinery;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class CreativeTabSM extends CreativeTabs
{
	public CreativeTabSM(int par1, String par2Str)
	{
		super(par1, par2Str);
	}

	@Override
	public int getTabIconItemIndex()
	{
		if(SciMachinery.instance.stoneTube != null)
			return SciMachinery.instance.stoneTube.blockID;
		return 0;
	}
}