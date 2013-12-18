package com.sci.machinery.block.tube;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.sci.machinery.core.BlockCoord;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TravellingItem
{
	private List<BlockCoord> lastCoord;
	private float rotation;
	private ItemStack stack;

	public TravellingItem(ItemStack stack)
	{
		this.stack = stack;
		this.lastCoord = new ArrayList<BlockCoord>();
	}

	public List<BlockCoord> getLastCoord()
	{
		return lastCoord;
	}

	public float getRotation()
	{
		return rotation;
	}

	public ItemStack getStack()
	{
		return stack;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public void setStack(ItemStack stack)
	{
		this.stack = stack;
	}
}