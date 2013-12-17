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
	private ItemStack stack;
	private float rotation;
	private List<BlockCoord> lastCoord;

	public ItemStack getStack()
	{
		return stack;
	}

	public void setStack(ItemStack stack)
	{
		this.stack = stack;
	}

	public float getRotation()
	{
		return rotation;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public List<BlockCoord> getLastCoord()
	{
		return lastCoord;
	}

	public TravellingItem(ItemStack stack)
	{
		this.stack = stack;
		this.lastCoord = new ArrayList<BlockCoord>();
	}
}