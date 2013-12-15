package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;

public interface ITubeConnectable
{
	public void addItem(TravellingItem item, TileEntity sender);
	
	public boolean canAcceptItems();
}