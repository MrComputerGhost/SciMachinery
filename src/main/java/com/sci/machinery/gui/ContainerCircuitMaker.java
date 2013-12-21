package com.sci.machinery.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import com.sci.machinery.block.TileCircuitMaker;

public class ContainerCircuitMaker extends Container
{
	private TileCircuitMaker tile;

	public ContainerCircuitMaker(InventoryPlayer inventoryPlayer, TileCircuitMaker tile)
	{
		this.tile = tile;

		// player inventory
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}

		//
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				addSlotToContainer(new Slot(tile, j + i * 5, 12 + j * 18, 17 + i * 18));
			}
		}

		addSlotToContainer(new SlotOutput(tile, 15, 138 + 4, 17 + 4));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return tile.isUseableByPlayer(entityPlayer);
	}
}