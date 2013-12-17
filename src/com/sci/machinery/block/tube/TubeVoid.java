package com.sci.machinery.block.tube;

import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.network.PacketRemoveItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TubeVoid extends TubeNormal
{
	public TubeVoid()
	{
		super(false);
		this.material = Material.VOID;
	}

	@Override
	public void update()
	{
		if(!items.isEmpty())
		{
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveItem(getTile().xCoord, getTile().yCoord, getTile().zCoord, 0)));
			items.remove(0);
		}
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube) { return !(((TileTube) e).getTube() instanceof TubeVoid); }
		return false;
	}
}