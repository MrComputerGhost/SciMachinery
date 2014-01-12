package com.sci.machinery.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import cpw.mods.fml.relauncher.Side;

public interface IPacketHandler
{
	/**
	 * Read a packet sent from side
	 * 
	 * @param din
	 * @param side
	 */
	public void readPacket(DataInputStream din, Side side) throws IOException;

	/**
	 * Write a packet going to side from the opposite of side
	 * 
	 * @param din
	 * @param side
	 */
	public void writePacket(DataOutputStream din, Side side) throws IOException;

	public void sendPacketUpdate(Side side);
}