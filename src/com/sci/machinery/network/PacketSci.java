package com.sci.machinery.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public abstract class PacketSci
{
	public PacketTypeHandler packetType;

	public PacketSci()
	{
		
	}
	
	public PacketSci(PacketTypeHandler packetType)
	{
		this.packetType = packetType;
	}

	public byte[] populate()
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);

		try
		{
			dout.writeByte(packetType.ordinal());
			this.write(dout);
		}
		catch(Exception e)
		{
		}

		return out.toByteArray();
	}

	public void populate(DataInputStream dis)
	{
		try
		{
			this.read(dis);
		}
		catch(IOException e)
		{
		}
	}

	public abstract void read(DataInputStream data) throws IOException;

	public abstract void write(DataOutputStream data) throws IOException;

	public abstract void execute(INetworkManager manager, Player player);
}