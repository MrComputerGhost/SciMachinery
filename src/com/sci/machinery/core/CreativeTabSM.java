package com.sci.machinery.core;

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
        return Item.redstone.itemID; //TODO icon
    }
}