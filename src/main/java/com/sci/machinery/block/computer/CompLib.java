package com.sci.machinery.block.computer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;

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

	private static boolean init = false;
	private static int nextID = 1;
	private static BitSet usedIDs = new BitSet();

	public static int assignID()
	{
		int id = usedIDs.nextClearBit(nextID);
		nextID = id + 1;
		usedIDs.set(id);
		writeFile();
		return id;
	}

	public static void assignID(int id)
	{
		if(usedIDs.get(id))
			throw new RuntimeException("Cannot steal id " + id);

		usedIDs.set(id);
		nextID = usedIDs.nextClearBit(0);

		writeFile();
	}

	public static void releaseID(int id)
	{
		usedIDs.clear(id);
		if(id < nextID)
			nextID = id;

		writeFile();
	}

	public static void initIfNeeded()
	{
		if(!init)
		{
			File folder = getSMCFolder(DimensionManager.getWorld(0));
			if(!folder.exists())
				folder.mkdirs();

			File ids = new File(folder, "ids.txt");
			System.out.println(ids);
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

				String[] spl = sb.toString().split(",");

				for(String i : spl)
				{
					assignID(Integer.valueOf(i));
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
			init = true;
		}
	}

	private static void writeFile()
	{
		File folder = getSMCFolder(DimensionManager.getWorld(0));
		if(!folder.exists())
			folder.mkdirs();

		try
		{
			File ids = new File(folder, "ids.txt");
			if(!ids.exists())
				ids.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(ids));

			for(int i = 0; i < usedIDs.size(); i++)
				if(usedIDs.get(i))
					writer.write(i + (i == usedIDs.size() - 1 ? "" : ","));

			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static File getSMCFolder(World world)
	{
		return new File(getWorldFolder(world), "SciMachinery/Computer");
	}

	public static File getWorldFolder(World world)
	{
		return new File(FMLCommonHandler.instance().getMinecraftServerInstance().getFile(""), DimensionManager.getWorld(0).getSaveHandler().getWorldDirectoryName());
	}
}