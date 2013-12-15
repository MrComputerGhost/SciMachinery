package com.sci.machinery.block.tube;

import java.util.List;
import com.sci.machinery.block.TileTube;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Tube implements ITubeConnectable
{
	protected TileTube tile;

	public Tube(TileTube tile)
	{
		this.tile = tile;
	}

	public static final Class<? extends Tube> NORMAL = TubeNormal.class;
	public static final Class<? extends Tube> PUMP = TubePump.class;
	public static final Class<? extends Tube> DETECTOR = TubeDetector.class;

	public abstract void breakTube();

	public abstract void validate();

	public abstract void writeToNBT(NBTTagCompound tag);

	public abstract void readFromNBT(NBTTagCompound tag);

	public abstract void update();

	public abstract List<TravellingItem> getItems();

	public abstract boolean isPowering();
}