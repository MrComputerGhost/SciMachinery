package com.sci.machinery.core;

import net.minecraft.item.ItemStack;

public class TravellingItem
{
	private ItemStack stack;
	private float rotation;
	private int lastDir = -1;
	
	public TravellingItem(ItemStack stack)
	{
		if(stack == null)
			throw new NullPointerException();
		this.stack = stack;
	}
	
	public float getRotation()
	{
		return rotation;
	}
	
	public int getLastDir()
	{
		return lastDir;
	}

	public void setLastDir(int lastDir)
	{
		this.lastDir = lastDir;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public ItemStack getStack()
	{
		return stack;
	}

	public void setStack(ItemStack stack)
	{
		this.stack = stack;
	}
}