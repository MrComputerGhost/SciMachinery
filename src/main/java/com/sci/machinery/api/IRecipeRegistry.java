package com.sci.machinery.api;

import net.minecraft.item.ItemStack;

public interface IRecipeRegistry
{
	public boolean isValidRecipe(ItemStack[][] recipe);
	
	public void registerRecipe(IRecipe recipe);
	
	public ItemStack getRecipeResult(ItemStack[][] recipe);

	public IRecipe getRecipe(ItemStack[][] recipe);
}