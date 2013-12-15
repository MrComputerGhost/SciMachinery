package com.sci.machinery.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.block.tube.TravellingItem;
import com.sci.machinery.core.BlockCoord;
import cpw.mods.fml.common.network.Player;

public class PacketRemoveItem extends PacketSci
{
	private int x, y, z;
	private int index;

	public PacketRemoveItem()
	{

	}

	public PacketRemoveItem(int x, int y, int z, int index)
	{
		super(PacketTypeHandler.REMOVE_ITEM);
		this.x = x;
		this.y = y;
		this.z = z;
		this.index = index;
	}

	@Override
	public void read(DataInputStream data) throws IOException
	{
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.index = data.readInt();
	}

	@Override
	public void write(DataOutputStream data) throws IOException
	{
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		data.writeInt(this.index);
	}

	@Override
	public void execute(INetworkManager manager, Player player)
	{
		EntityPlayer thePlayer = (EntityPlayer) player;
		if(thePlayer.worldObj.isRemote)
		{
			TileEntity t = thePlayer.worldObj.getBlockTileEntity(x, y, z);
			if(t != null && t instanceof TileTube)
			{
				((TileTube) t).removeItem(index);
			}
		}
	}
}