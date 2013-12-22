package com.sci.machinery.core;

import net.minecraft.item.ItemStack;
import com.sci.machinery.api.IRecipe;

public class CircuitMakerRecipe implements IRecipe
{
	private ItemStack result;
	private ItemStack[][] ingredients;
	private int timeToCraft;

	public CircuitMakerRecipe(int timeToCraft, ItemStack result, ItemStack[][] ingredients)
	{
		if(!areIngredientsValid(ingredients))
			throw new IllegalArgumentException("Invalid ingredients!");
		this.timeToCraft = timeToCraft;
		this.result = result;
		this.ingredients = ingredients;
	}

	private boolean areIngredientsValid(ItemStack[][] ingredients)
	{
		boolean ret = ingredients.length == getWidth();
		for(int x = 0; x < getWidth(); x++)
		{
			if(ingredients[x].length != getHeight())
				ret = false;
		}
		return ret;
	}

	@Override
	public int getWidth()
	{
		return 5;
	}

	@Override
	public int getHeight()
	{
		return 3;
	}

	@Override
	public ItemStack getIngredient(int x, int y)
	{
		return this.ingredients[x][y];
	}

	@Override
	public ItemStack getResult()
	{
		System.out.println(this.result);
		return this.result;
	}

	@Override
	public ItemStack[][] getIngredients()
	{
		return ingredients;
	}
	
	public int getTimeToCraft()
	{
		return timeToCraft;
	}
}