package com.sci.machinery.entity;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class Nuke
{
	private int x, y, z;
	private World world;
	private int tmr;
	private int power;

	public Nuke(World world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.power = 50;
	}

	public void explode()
	{
		this.world.spawnParticle("hugeexplosion", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);

		int radius = power + this.world.rand.nextInt(10);

		for(int x = 0; x < radius; x++)
		{
			for(int y = 0; y < radius; y++)
			{
				for(int z = 0; z < radius; z++)
				{
					if(fv(x, y, z) <= (radius * radius))
					{
						setBlock((x + this.x), (y + this.y), (z + this.z), 0);
						setBlock(-(x + this.x), (y + this.y), (z + this.z), 0);
						setBlock((x + this.x), -(y + this.y), (z + this.z), 0);
						setBlock((x + this.x), (y + this.y), -(z + this.z), 0);
						setBlock(-(x + this.x), -(y + this.y), (z + this.z), 0);
						setBlock(-(x + this.x), (y + this.y), -(z + this.z), 0);
						setBlock(-(x + this.x), -(y + this.y), -(z + this.z), 0);
					}
				}
			}
		}

		this.world.spawnParticle("largeexplode", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
	}

	private int fv(int x, int y, int z)
	{
		return x * x + y * y + z * z;
	}

	private void setBlock(int x, int y, int z, int id)
	{
		Block block = Block.blocksList[this.world.getBlockId(x, y, z)];

		if(block == null || block.blockID == Block.bedrock.blockID)
			return;

		if(block.canDropFromExplosion(null))
		{
			block.dropBlockAsItemWithChance(this.world, x, y, z, this.world.getBlockMetadata(x, y, z), 1.0F / this.power, 0);
			block.onBlockExploded(this.world, x, y, z, null);
		}

		tmr++;
		if(tmr == 5)
		{
			tmr = 0;
			double d3 = this.world.rand.nextDouble();
			double d4 = this.world.rand.nextDouble();
			double d5 = this.world.rand.nextDouble();
			this.world.spawnParticle("explode", (this.x * 1.0D) / 2.0D, (this.y * 1.0D) / 2.0D, (this.z * 1.0D) / 2.0D, d3, d4, d5);
			this.world.spawnParticle("smoke", x, y, z, d3, d4, d5);
			this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		}
	}
}