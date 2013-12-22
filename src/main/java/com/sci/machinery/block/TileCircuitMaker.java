package com.sci.machinery.block;

import net.minecraft.entity.item.EntityItem;
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

	private int totalTime;
	private int timer;

	private ItemStack[] currentRecipe;

	private Runnable cb;

	public TileCircuitMaker()
	{
		inventory = new ItemStack[16];
		currentRecipe = new ItemStack[15];
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

		this.crafting = tagCompound.getBoolean("crafting");
		this.totalTime = tagCompound.getInteger("totalTime");
		this.timer = tagCompound.getInteger("timeLeft");

		NBTTagList tagList2 = tagCompound.getTagList("CurrentRecipe");
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList2.tagAt(i);
			byte slot = tag.getByte("Slot");
			if(slot >= 0 && slot < inventory.length)
			{
				currentRecipe[slot] = ItemStack.loadItemStackFromNBT(tag);
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

		tagCompound.setBoolean("crafting", crafting);
		tagCompound.setInteger("totalTime", totalTime);
		tagCompound.setInteger("timeLeft", timer);

		NBTTagList itemList2 = new NBTTagList();
		for(int i = 0; i < inventory.length; i++)
		{
			ItemStack stack = inventory[i];
			if(stack != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList2.appendTag(tag);
			}
		}
		tagCompound.setTag("CurrentRecipe", itemList2);
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
				crafting = false;

				IRecipeRegistry registry = SciMachinery.instance.circuitMakerRegistry;
				ItemStack res = registry.getRecipe(currentRecipe).getResult();

				if(inventory[15] == null)
				{
					this.setInventorySlotContents(15, res);
				}
				else
				{
					ItemStack stackInSlot = inventory[15];
					stackInSlot.stackSize++;
					this.setInventorySlotContents(15, stackInSlot);
				}
			}
		}

		if(cb != null)
			cb.run();
	}

	private void tryCraft()
	{
		if(worldObj.isRemote)
			return;
		if(crafting)
			return;
		IRecipeRegistry registry = SciMachinery.instance.circuitMakerRegistry;
		for(int i = 0; i < 15; i++)
		{
			currentRecipe[i] = inventory[i];
		}
		if(registry.isValidRecipe(currentRecipe))
		{
			if(inventory[15] != null)
			{
				ItemStack item = registry.getRecipe(currentRecipe).getResult();
				if(inventory[15].itemID != item.itemID || inventory[15].getItemDamage() != item.getItemDamage())
					return;

				if(inventory[15].stackSize == 64)
					return;
			}
			timer = ((CircuitMakerRecipe) registry.getRecipe(currentRecipe)).getTimeToCraft();
			totalTime = ((CircuitMakerRecipe) registry.getRecipe(currentRecipe)).getTimeToCraft();
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 5; j++)
				{
					this.decrStackSize(j + i * 5, 1);
				}
			}

			crafting = true;
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

	public int canCraft()
	{
		if(crafting)
			return 0;
		IRecipeRegistry registry = SciMachinery.instance.circuitMakerRegistry;
		for(int i = 0; i < 15; i++)
			currentRecipe[i] = inventory[i];
		return registry.isValidRecipe(currentRecipe) ? 1 : 0;
	}

	public void setButtonUpdateCallback(Runnable cb)
	{
		this.cb = cb;
	}

	public void breakBlock()
	{
		if(worldObj.isRemote)
			return;
		if(crafting)
		{
			for(int i = 0; i < currentRecipe.length; i++)
			{
				if(currentRecipe[i] != null)
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, currentRecipe[i]));
			}
		}
		else
		{
			for(int i = 0; i < inventory.length; i++)
			{
				if(inventory[i] != null)
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory[i]));
			}
		}
	}
}