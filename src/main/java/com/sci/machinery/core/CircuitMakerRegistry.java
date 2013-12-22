package com.sci.machinery.core;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.sci.machinery.api.IRecipe;
import com.sci.machinery.api.IRecipeRegistry;

public class CircuitMakerRegistry implements IRecipeRegistry
{
	private List<CircuitMakerRecipe> recipes;

	public CircuitMakerRegistry()
	{
		recipes = new ArrayList<CircuitMakerRecipe>();
	}

	@Override
	public boolean isValidRecipe(ItemStack[][] recipe)
	{
		boolean contains = false;
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				contains = true;
		}
		return contains;
	}

	@Override
	public void registerRecipe(IRecipe recipe)
	{
		if(!(recipe instanceof CircuitMakerRecipe))
			throw new IllegalArgumentException("Recipe is not a circuit maker!");
		boolean contains = false;
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				contains = true;
		}
		if(contains)
			throw new IllegalArgumentException("Recipe already registered!");
		recipes.add((CircuitMakerRecipe) recipe);
	}

	@Override
	public ItemStack getRecipeResult(ItemStack[][] recipe)
	{
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe)) { return rRecipe.getResult(); }
		}
		return null;
	}

	private boolean recipesEqual(IRecipe a, IRecipe b)
	{
		if(a.getWidth() != b.getWidth())
			return false;
		if(a.getHeight() != b.getHeight())
			return false;

		if(!ItemStack.areItemStacksEqual(a.getResult(), b.getResult()))
			return false;

		return recipesEqual(a.getIngredients(), b);
	}

	private boolean recipesEqual(ItemStack[][] a, IRecipe b)
	{
		for(int x = 0; x < a.length; x++)
		{
			for(int y = 0; y < a[x].length; y++)
			{
				if(!ItemStack.areItemStacksEqual(a[x][y], b.getIngredient(x, y)))
					return false;
			}
		}

		return true;
	}

	@Override
	public IRecipe getRecipe(ItemStack[][] recipe)
	{
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				return rRecipe;
		}
		return null;
	}
}