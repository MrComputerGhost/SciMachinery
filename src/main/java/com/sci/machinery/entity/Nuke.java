package com.sci.machinery.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.block.BlockNuke;

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
		this.power = 35;
	}

	public void explode()
	{
		this.world.spawnParticle("hugeexplosion", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
		this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		
		int radius = power + this.world.rand.nextInt(10);

		for(int x = -radius; x < radius; x++)
		{
			for(int y = -radius; y < radius; y++)
			{
				for(int z = -radius; z < radius; z++)
				{
					if((x * x + y * y + z * z <= radius * radius) || this.world.rand.nextInt(100) < 15)
					{
						setBlock(x + this.x, y + this.y, z + this.z, 0);
					}
				}
			}
		}

		this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		
		this.world.spawnParticle("largeexplode", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
		this.world.spawnParticle("hugeexplosion", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
		
		this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		
		for(int x = -radius; x < radius; x++)
		{
			for(int y = -radius; y < radius; y++)
			{
				for(int z = -radius; z < radius; z++)
				{
					if((x * x + y * y + z * z <= radius * radius) || this.world.rand.nextInt(100) < 15)
					{
						if(this.world.rand.nextInt(200) == 0)
						{
							EntityAcidArrow arrow = new EntityAcidArrow(this.world, this.x, this.y, this.z);
							arrow.setVelocity(-1 + this.world.rand.nextDouble() * 2, -1 + this.world.rand.nextDouble() * 2, -1 + this.world.rand.nextDouble() * 2);
							arrow.setFire(1000);
							this.world.spawnEntityInWorld(arrow);
						}
					}
				}
			}
		}
		
		this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		this.world.spawnParticle("largeexplode", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
	}

	private void setBlock(int x, int y, int z, int id)
	{
		Block block = Block.blocksList[this.world.getBlockId(x, y, z)];

		if(y < 0 || y > 256)
			return;

		if(block == null || block.blockID == Block.bedrock.blockID)
			return;

		if(block.blockID == Block.tnt.blockID)
		{
			((BlockTNT) block).primeTnt(this.world, this.x, this.y, this.z, 4, null);
		}
		else if(block.blockID == SciMachinery.instance.nukeId)
		{
			((BlockNuke) block).primeTnt(this.world, this.x, this.y, this.z, 4, null);
		}
		else if(block.blockID == Block.waterMoving.blockID || block.blockID == Block.waterStill.blockID || block.blockID == Block.lavaMoving.blockID || block.blockID == Block.lavaStill.blockID)
		{
			this.world.setBlock(x, y, z, 0); // die liquids!
		}
		else
		{
			if(block.canDropFromExplosion(null))
			{
				block.dropBlockAsItemWithChance(this.world, x, y, z, this.world.getBlockMetadata(x, y, z), 0.01f, 0);
				block.onBlockExploded(this.world, x, y, z, null);
			}
		}
	}
}