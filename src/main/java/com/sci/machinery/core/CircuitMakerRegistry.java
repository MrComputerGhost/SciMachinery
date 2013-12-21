package com.sci.machinery.core;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import com.sci.machinery.api.IRecipeRegistry;

public class CircuitMakerRegistry implements IRecipeRegistry
{
	private List<CircuitMakerRecipe> recipes;
	
	@Override
	public boolean isValidRecipe(IRecipe recipe)
	{
		
		return false;
	}

	@Override
	public void registerRecipe(IRecipe recipe)
	{
		
	}

	@Override
	public ItemStack getRecipeResult(IRecipe recipe)
	{
		return null;
	}
}