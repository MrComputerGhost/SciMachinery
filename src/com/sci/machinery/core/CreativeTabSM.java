package com.sci.machinery.core;

import com.sci.machinery.SciMachinery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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