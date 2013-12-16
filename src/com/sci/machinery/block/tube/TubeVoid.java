package com.sci.machinery.block.tube;

import com.sci.machinery.network.PacketRemoveItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TubeVoid extends TubeNormal
{
	@Override
	public void update()
	{
		if(!items.isEmpty())
		{
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveItem(getTile().xCoord, getTile().yCoord, getTile().zCoord, 0)));
			items.remove(0);
		}
	}
}	