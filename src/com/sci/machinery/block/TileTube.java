package com.sci.machinery.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import com.sci.machinery.core.TileSci;

public class TileTube extends TileSci
{
	private List<ItemStack> items;

	public TileTube()
	{
		items = new ArrayList<ItemStack>();
		
		items.add(new ItemStack(Item.diamond));
	}

	public List<ItemStack> getItems()
	{
		return items;
	}
	
	@Override
	public void updateEntity()
	{
	}

	public TileEntity[] getAdjacentTiles(IBlockAccess world, int x, int y, int z)
	{
		TileEntity[] t = new TileEntity[6];
		t[4] = world.getBlockTileEntity(x + 1, y, z);
		t[5] = world.getBlockTileEntity(x - 1, y, z);
		t[1] = world.getBlockTileEntity(x, y + 1, z);
		t[0] = world.getBlockTileEntity(x, y - 1, z);
		t[2] = world.getBlockTileEntity(x, y, z + 1);
		t[3] = world.getBlockTileEntity(x, y, z - 1);
		return t;
	}
}