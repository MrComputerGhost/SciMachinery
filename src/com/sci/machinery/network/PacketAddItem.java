package com.sci.machinery.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.core.TravellingItem;
import cpw.mods.fml.common.network.Player;

public class PacketAddItem extends PacketSci
{
	private int x, y, z;
	private int id;
	private int count;

	public PacketAddItem()
	{

	}

	public PacketAddItem(int x, int y, int z, int id, int count)
	{
		super(PacketTypeHandler.ADD_ITEM);
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.count = count;
	}

	@Override
	public void read(DataInputStream data) throws IOException
	{
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.id = data.readInt();
		this.count = data.readInt();
	}

	@Override
	public void write(DataOutputStream data) throws IOException
	{
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		data.writeInt(this.id);
		data.writeInt(this.count);
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
				((TileTube) t).addItem(new TravellingItem(new ItemStack(Item.itemsList[id], count)));
			}
		}
	}
}