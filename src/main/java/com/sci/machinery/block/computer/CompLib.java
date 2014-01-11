package com.sci.machinery.block.computer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import net.minecraft.world.World;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class CompLib
{
	private CompLib()
	{
	}

	private static int nextID = 1;
	private static BitSet usedIDs = new BitSet();

	static
	{
		try
		{
			File ids = new File(getSMCFolder(DimensionManager.getWorld(0)), "ids.txt");
			if(!ids.exists())
				ids.createNewFile();

			if(ids.exists())
			{
				StringBuilder sb = new StringBuilder();

				try
				{
					BufferedReader reader = new BufferedReader(new FileReader(ids));

					String line;
					while((line = reader.readLine()) != null)
					{
						sb.append(line);
					}

					reader.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}

				if(!sb.toString().isEmpty())
				{
					String[] spl = sb.toString().split(",");
					for(String i : spl)
					{
						assignID(Integer.valueOf(i));
					}
				}
			}
			else
			{
				try
				{
					ids.createNewFile();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static int assignID()
	{
		int id = usedIDs.nextClearBit(nextID);
		nextID = id + 1;
		usedIDs.set(id);
		save();
		return id;
	}

	public static void assignID(int id)
	{
		if(usedIDs.get(id))
			throw new RuntimeException("Cannot steal id " + id);

		usedIDs.set(id);
		nextID = usedIDs.nextClearBit(0);
		save();
	}

	public static void releaseID(int id)
	{
		usedIDs.clear(id);
		if(id < nextID)
			nextID = id;
		save();
	}

	private static void save()
	{
		try
		{
			File file = new File(getSMCFolder(DimensionManager.getWorld(0)), "ids.txt");
			if(!file.exists())
				file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < usedIDs.length(); i++)
			{
				if(usedIDs.get(i))
				{
					sb.append(i);
					sb.append(',');
				}
			}

			writer.write(sb.toString().substring(0, sb.toString().length() - 1));

			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static File getSMCFolder(World world) throws IOException
	{
		SaveHandler sh = (SaveHandler) world.getSaveHandler();
		File ret = new File(sh.getWorldDirectory().getCanonicalFile(), "SciMachinery/Computer");
		if(!ret.exists())
			ret.mkdirs();
		return ret;
	}
}