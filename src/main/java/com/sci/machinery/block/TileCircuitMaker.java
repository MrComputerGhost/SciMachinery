package com.sci.machinery.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.api.IRecipeRegistry;
import com.sci.machinery.core.CircuitMakerRecipe;

public class TileCircuitMaker extends TileEntity implements IInventory
{
	private boolean crafting;
	private ItemStack[] inventory;

	private ItemStack[][] recipeStacks;

	private int totalTime;
	private int timer;

	public TileCircuitMaker()
	{
		inventory = new ItemStack[16];
		recipeStacks = new ItemStack[5][3];
		totalTime = 1;
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			if(stack.stackSize <= amt)
			{
				setInventorySlotContents(slot, null);
			}
			else
			{
				stack = stack.splitStack(amt);
				if(stack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if(slot >= 0 && slot < inventory.length)
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for(int i = 0; i < inventory.length; i++)
		{
			ItemStack stack = inventory[i];
			if(stack != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}

	@Override
	public String getInvName()
	{
		return "container.circuitMaker";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i == 15 ? false : true;
	}

	@Override
	public void updateEntity()
	{
		if(crafting && !worldObj.isRemote)
		{
			timer--;
			if(timer == 0)
			{
				if(!worldObj.isRemote)
					this.setInventorySlotContents(15, SciMachinery.instance.circuitMakerRegistry.getRecipeResult(recipeStacks));
				crafting = false;
			}
		}
	}

	private void tryCraft()
	{
		IRecipeRegistry registry = SciMachinery.instance.circuitMakerRegistry;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				recipeStacks[j][i] = inventory[j + i * 5];
			}
		}
		if(registry.isValidRecipe(recipeStacks))
		{
			crafting = true;

			System.out.println(((CircuitMakerRecipe) registry.getRecipe(recipeStacks)).getTimeToCraft());

			timer = ((CircuitMakerRecipe) registry.getRecipe(recipeStacks)).getTimeToCraft();
			totalTime = ((CircuitMakerRecipe) registry.getRecipe(recipeStacks)).getTimeToCraft();
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 5; j++)
				{
					this.setInventorySlotContents(j + i * 5, null);
				}
			}
		}
	}

	public void buttonPressed(int id)
	{
		if(!worldObj.isRemote)
		{
			switch (id)
			{
			case 1:
			{
				tryCraft();
				break;
			}
			default:
			{
				break;
			}
			}
		}
	}

	public int getProgressScaled(int i)
	{
		if(totalTime == 0)
			return 0;
		return (totalTime - timer) * i / totalTime;
	}

	public int getTotalTime()
	{
		return crafting ? totalTime : 0;
	}

	public int getTimeLeft()
	{
		return crafting ? timer : 0;
	}

	public void setTotalTime(int par2)
	{
		totalTime = par2;
	}

	public void setTimeLeft(int par2)
	{
		timer = par2;
	}
}