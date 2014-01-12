package com.sci.machinery.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileSci;
import cpw.mods.fml.common.network.Player;

public class PacketByteArray extends PacketSci
{
	private int x, y, z;
	private byte[] data;

	public PacketByteArray()
	{
	}

	public PacketByteArray(int xCoord, int yCoord, int zCoord, byte[] data)
	{
		super(PacketTypeHandler.BYTE_ARRAY);
		this.x = xCoord;
		this.y = yCoord;
		this.z = zCoord;
		this.data = data;
	}

	@Override
	public void execute(INetworkManager manager, Player player)
	{
		EntityPlayer thePlayer = (EntityPlayer) player;
		TileEntity t = thePlayer.worldObj.getBlockTileEntity(x, y, z);
		if(t != null && t instanceof TileSci)
		{
			((TileSci) t).handlePacket(this);
		}
	}

	@Override
	public void read(DataInputStream data) throws IOException
	{
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		int len = data.readInt();
		this.data = new byte[len];
		for(int i = 0; i < len; i++)
		{
			this.data[i] = data.readByte();
		}
	}

	@Override
	public void write(DataOutputStream data) throws IOException
	{
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		data.writeInt(this.data.length);
		for(int i = 0; i < this.data.length; i++)
		{
			data.writeByte(this.data[i]);
		}
	}

	public byte[] getData()
	{
		return this.data;
	}
}