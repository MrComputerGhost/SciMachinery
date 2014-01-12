package com.sci.machinery.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.computer.TileEntityComputer;
import cpw.mods.fml.common.network.Player;

public class PacketKeyPress extends PacketSci
{
	private int x, y, z;
	private char c;
	private int i;

	public PacketKeyPress()
	{
		super(PacketTypeHandler.KEY_PRESS);
	}

	public PacketKeyPress(int x, int y, int z, char c, int i)
	{
		super(PacketTypeHandler.KEY_PRESS);
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		this.i = i;
	}

	@Override
	public void execute(INetworkManager manager, Player player)
	{
		EntityPlayer thePlayer = (EntityPlayer) player;
		TileEntity t = thePlayer.worldObj.getBlockTileEntity(x, y, z);
		if(t != null && t instanceof TileEntityComputer)
		{
			TileEntityComputer comp = (TileEntityComputer) t;
			comp.getComputer().keyPressed(c, i);
		}
	}

	@Override
	public void read(DataInputStream data) throws IOException
	{
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.c = data.readChar();
		this.i = data.readInt();
	}

	@Override
	public void write(DataOutputStream data) throws IOException
	{
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeChar(c);
		data.writeInt(i);
	}
}