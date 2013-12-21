package com.sci.machinery.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import com.sci.machinery.block.TileCircuitMaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCircuitMaker extends Container
{
	private TileCircuitMaker tile;
	
	private int totalTime;
	private int timer;

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

	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.getTotalTime());
		par1ICrafting.sendProgressBarUpdate(this, 1, this.tile.getTimeLeft());
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for(int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if(this.totalTime != this.tile.getTotalTime())
			{
				icrafting.sendProgressBarUpdate(this, 0, this.tile.getTotalTime());
			}

			if(this.timer != this.tile.getTimeLeft())
			{
				icrafting.sendProgressBarUpdate(this, 1, this.tile.getTimeLeft());
			}
		}

		this.totalTime = this.tile.getTotalTime();
		this.timer = this.tile.getTimeLeft();
	}
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
        	this.tile.setTotalTime(par2);
        }

        if (par1 == 1)
        {
        	this.tile.setTimeLeft(par2);
        }
    }

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return tile.isUseableByPlayer(entityPlayer);
	}
}