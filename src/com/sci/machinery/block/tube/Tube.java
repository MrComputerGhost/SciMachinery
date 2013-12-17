package com.sci.machinery.block.tube;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import com.sci.machinery.block.TileTube;

public abstract class Tube implements ITubeConnectable
{
	private TileTube tile;

	public static final Class<? extends Tube> NORMAL = TubeNormal.class;
	public static final Class<? extends Tube> PUMP = TubePump.class;
	public static final Class<? extends Tube> DETECTOR = TubeDetector.class;
	public static final Class<? extends Tube> VOID = TubeVoid.class;

	public void setTile(TileTube tile)
	{
		this.tile = tile;
	}
	
	public TileTube getTile()
	{
		return tile;
	}

	public abstract void breakTube();

	public void validate()
	{
	}

	public void invalidate()
	{
	}

	public final boolean isValid()
	{
		return tile != null;
	}

	public abstract void writeToNBT(NBTTagCompound tag);

	public abstract void readFromNBT(NBTTagCompound tag);

	public abstract void update();

	public abstract List<TravellingItem> getItems();

	public abstract boolean isPowering();

	public abstract Material getMaterial();
}