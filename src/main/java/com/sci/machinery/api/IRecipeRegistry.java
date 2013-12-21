package com.sci.machinery.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public interface IRecipeRegistry
{
	public boolean isValidRecipe(IRecipe recipe);
	
	public void registerRecipe(IRecipe recipe);
	
	public ItemStack getRecipeResult(IRecipe recipe);
}