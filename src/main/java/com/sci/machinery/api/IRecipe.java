package com.sci.machinery.api;

import net.minecraft.item.ItemStack;

public interface IRecipe
{
	public int getWidth();
	
	public int getHeight();
	
	public ItemStack getIngredient(int x, int y);
	
	public ItemStack getResult();
}