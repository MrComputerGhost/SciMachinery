package com.sci.machinery.block;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.network.PacketByteArray;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public abstract class TileSci extends TileEntity
{
	/**
	 * Read a packet sent from side
	 * 
	 * @param din
	 * @param side
	 */
	public abstract void readPacket(DataInputStream din, Side side) throws IOException;

	/**
	 * Write a packet going to side from the opposite of side
	 * 
	 * @param din
	 * @param side
	 */
	public abstract void writePacket(DataOutputStream din, Side side) throws IOException;

	public final void sendPacketUpdate(Side side)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(baos);

		try
		{
			writePacket(dout, side);
		}
		catch(IOException e1)
		{
		}

		PacketByteArray pkt = new PacketByteArray(this.xCoord, this.yCoord, this.zCoord, baos.toByteArray());
		if(side == Side.SERVER)
			PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(pkt));
		else
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(pkt));

		try
		{
			dout.close();
			baos.close();
		}
		catch(IOException e)
		{
		}
	}

	public final void handlePacket(PacketByteArray packet)
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
		DataInputStream din = new DataInputStream(bais);

		try
		{
			readPacket(din, this.worldObj.isRemote ? Side.CLIENT : Side.SERVER);
		}
		catch(IOException e)
		{
		}

		try
		{
			din.close();
			bais.close();
		}
		catch(IOException e)
		{
		}
	}
}