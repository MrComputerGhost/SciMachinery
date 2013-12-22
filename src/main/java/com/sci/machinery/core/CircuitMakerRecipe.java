package com.sci.machinery.core;

import net.minecraft.item.ItemStack;
import com.sci.machinery.api.IRecipe;

public class CircuitMakerRecipe implements IRecipe
{
	private ItemStack result;
	private ItemStack[] ingredients;
	private int timeToCraft;

	public CircuitMakerRecipe(int timeToCraft, ItemStack result, ItemStack[] ingredients)
	{
		if(!areIngredientsValid(ingredients))
			throw new IllegalArgumentException("Invalid ingredients!");
		this.timeToCraft = timeToCraft;
		this.result = result;
		this.ingredients = ingredients;
	}

	private boolean areIngredientsValid(ItemStack[] ingredients)
	{
		return ingredients.length == 15;
	}

	@Override
	public ItemStack getIngredient(int i)
	{
		return this.ingredients[i];
	}

	@Override
	public ItemStack getResult()
	{
		return this.result;
	}

	@Override
	public ItemStack[] getIngredients()
	{
		return ingredients;
	}

	public int getTimeToCraft()
	{
		return timeToCraft;
	}
}