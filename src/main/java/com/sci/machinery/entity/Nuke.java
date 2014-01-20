package com.sci.machinery.entity;

import com.sci.machinery.SciMachinery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.world.World;

public class Nuke
{
	private int x, y, z;
	private World world;
	private int power;

	public Nuke(World world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.power = 25;
	}

	public void explode()
	{
		this.world.spawnParticle("hugeexplosion", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);

		int radius = power + this.world.rand.nextInt(10);

		for(int x = -radius; x < radius; x++)
		{
			for(int y = -radius; y < radius; y++)
			{
				for(int z = -radius; z < radius; z++)
				{
					if((x * x + y * y + z * z <= radius * radius) || this.world.rand.nextInt(100) < 10)
						setBlock(x + this.x, y + this.y, z + this.z, 0);
				}
			}
		}

		this.world.spawnParticle("largeexplode", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
	}

	private void setBlock(int x, int y, int z, int id)
	{
		Block block = Block.blocksList[this.world.getBlockId(x, y, z)];

		if(block == null || block.blockID == Block.bedrock.blockID)
			return;

		if(block.blockID == Block.tnt.blockID || block.blockID == SciMachinery.instance.nukeId)
		{
			((BlockTNT) block).primeTnt(this.world, this.x, this.y, this.z, 4, null);
		}

		if(block.canDropFromExplosion(null))
		{
			block.dropBlockAsItemWithChance(this.world, x, y, z, this.world.getBlockMetadata(x, y, z), 1.0F / this.power, 0);
			block.onBlockExploded(this.world, x, y, z, null);
		}
	}
}