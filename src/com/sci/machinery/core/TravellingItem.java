package com.sci.machinery.core;

import net.minecraft.item.ItemStack;

public class TravellingItem
{
	private ItemStack stack;
	private float rotation;
	
	public float getRotation()
	{
		return rotation;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public TravellingItem(ItemStack stack)
	{
		this.stack = stack;
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