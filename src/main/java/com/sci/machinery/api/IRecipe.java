package com.sci.machinery.api;

import net.minecraft.item.ItemStack;

public interface IRecipe
{
	public ItemStack getIngredient(int i);

	public ItemStack getResult();

	public ItemStack[] getIngredients();
}