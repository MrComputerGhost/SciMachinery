package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;

/**
 * SciMachinery
 *
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public interface ITubeConnectable
{
	public void addItem(TravellingItem item, TileEntity sender);

	public abstract void removeItem(int index);

	public boolean canAcceptItems();

	public boolean canConnectTube(TileEntity e);
}