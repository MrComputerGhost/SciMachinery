package com.sci.machinery.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileCircuitMaker;
import cpw.mods.fml.common.network.Player;

public class PacketItemCrafted extends PacketSci
{
	private int count;
	private int id;
	private int damage;
	private int x, y, z;

	public PacketItemCrafted()
	{

	}

	public PacketItemCrafted(int x, int y, int z, int id, int count, int damage)
	{
		super(PacketTypeHandler.ITEM_CRAFT);
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.count = count;
		this.damage = damage;
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
				((TileCircuitMaker) t).itemCrafted(new ItemStack(id, count, damage));
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
		this.count = data.readInt();
		this.damage = data.readInt();
	}

	@Override
	public void write(DataOutputStream data) throws IOException
	{
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		data.writeInt(this.id);
		data.writeInt(this.count);
		data.writeInt(this.damage);
	}
}