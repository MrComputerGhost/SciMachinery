package com.sci.machinery.core;

import net.minecraft.item.ItemStack;
import com.sci.machinery.api.IRecipe;

public class CircuitMakerRecipe implements IRecipe
{
	private final ItemStack result;
	private final ItemStack[] ingredients;
	private final int timeToCraft;

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

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("CR[");
		sb.append(result);
		sb.append("]");
		return sb.toString();
	}
}