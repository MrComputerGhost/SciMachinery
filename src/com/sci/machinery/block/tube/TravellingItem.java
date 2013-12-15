package com.sci.machinery.block.tube;

import net.minecraft.item.ItemStack;
import com.sci.machinery.core.BlockCoord;

public class TravellingItem
{
	private ItemStack stack;
	private float rotation;
	private BlockCoord lastCoord;

	public TravellingItem(ItemStack stack)
	{
		if(stack == null)
			throw new NullPointerException();
		this.stack = stack;
		lastCoord = new BlockCoord(0, 0, 0);
	}

	public float getRotation()
	{
		return rotation;
	}

	public BlockCoord getLastCoord()
	{
		return lastCoord;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public ItemStack getStack()
	{
		return stack;
	}

	public void setStack(ItemStack stack)
	{
		this.stack = stack;
	}

	public void setLastCoord(BlockCoord lastCoord)
	{
		this.lastCoord = lastCoord;
	}
}