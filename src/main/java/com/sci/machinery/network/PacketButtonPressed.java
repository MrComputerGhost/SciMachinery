package com.sci.machinery.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileCircuitMaker;
import cpw.mods.fml.common.network.Player;

public class PacketButtonPressed extends PacketSci
{
	private int x, y, z, id;

	public PacketButtonPressed()
	{
	}

	public PacketButtonPressed(int xCoord, int yCoord, int zCoord, int id)
	{
		this.x = xCoord;
		this.y = yCoord;
		this.z = zCoord;
		this.id = id;
	}

	@Override
	public void execute(INetworkManager manager, Player player)
	{
		EntityPlayer thePlayer = (EntityPlayer) player;
		if(thePlayer.worldObj.isRemote)
		{
			TileEntity t = thePlayer.worldObj.getBlockTileEntity(x, y, z);
			if(t != null && t instanceof TileCircuitMaker)
			{
				TileCircuitMaker circuitMaker = (TileCircuitMaker) t;
				circuitMaker.buttonPressed(id);
			}
		}
	}

	@Override
	public void read(DataInputStream data) throws IOException
	{
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.id = data.readInt();
	}

	@Override
	public void write(DataOutputStream data) throws IOException
	{
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeInt(id);
	}
}