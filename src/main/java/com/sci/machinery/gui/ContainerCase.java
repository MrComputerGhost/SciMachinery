package com.sci.machinery.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import com.sci.machinery.block.computer.TileCase;

public class ContainerCase extends Container
{
	private TileCase tile;

	public ContainerCase(InventoryPlayer inventoryPlayer, TileCase tile)
	{
		this.tile = tile;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return tile.isUseableByPlayer(entityPlayer);
	}
}