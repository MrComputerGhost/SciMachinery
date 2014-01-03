package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.network.PacketRemoveItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TubeVoid extends TubeBase
{
	public TubeVoid()
	{
		setR(20);
		setG(20);
		setB(200);
		setA(150);
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			if(((TileTube) e).getTube() instanceof TubeVoid)
				return false;
		}
		return super.canConnectTube(e);
	}

	@Override
	public void tick() //TODO this may need to change, we'll see
	{
		if(!items.isEmpty())
		{
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveItem(getTile().xCoord, getTile().yCoord, getTile().zCoord, 0)));
			items.remove(0);
		}
	}
}