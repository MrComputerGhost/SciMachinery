package com.sci.machinery.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import com.sci.machinery.lib.Reference;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public enum PacketTypeHandler
{
	ADD_ITEM(PacketAddItem.class), REMOVE_ITEM(PacketRemoveItem.class);

	public static PacketSci buildPacket(byte[] data)
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int selector = bis.read();
		DataInputStream dis = new DataInputStream(bis);

		PacketSci packet = null;

		try
		{
			packet = values()[selector].clazz.newInstance();
		}
		catch(Exception e)
		{
		}

		packet.populate(dis);
		return packet;
	}

	public static PacketSci buildPacket(PacketTypeHandler type)
	{
		PacketSci packet = null;

		try
		{
			packet = values()[type.ordinal()].clazz.newInstance();
		}
		catch(Exception e)
		{
		}

		return packet;
	}

	public static Packet populatePacket(PacketSci packet)
	{
		byte[] data = packet.populate();
		Packet250CustomPayload p250 = new Packet250CustomPayload();
		p250.channel = Reference.CHANNEL_NAME;
		p250.data = data;
		p250.length = data.length;
		p250.isChunkDataPacket = false;
		return p250;
	}

	private Class<? extends PacketSci> clazz;

	PacketTypeHandler(Class<? extends PacketSci> clazz)
	{
		this.clazz = clazz;
	}
}