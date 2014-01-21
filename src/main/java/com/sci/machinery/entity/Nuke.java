package com.sci.machinery.entity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
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
		this.power = 128;
	}

	public void explode()
	{
		this.world.spawnParticle("hugeexplosion", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
		this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

		int radius = power + this.world.rand.nextInt(10);

		@SuppressWarnings("unchecked")
		// ffs mojang use generics! friggin n00bs
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB((double) this.x - radius * 2, (double) this.y - radius * 2, (double) this.z - radius * 2, (double) this.x + radius * 2, (double) this.y + radius * 2, (double) this.z + radius * 2));
		for(Entity e : list)
		{
			e.attackEntityFrom(DamageSource.setExplosionSource(null), 18.0f);
			if(e instanceof EntityPlayer)
			{
				EntityPlayer ep = (EntityPlayer) e;
				if(!ep.capabilities.isCreativeMode)
					ep.addPotionEffect(new PotionEffect(19, 20 * 30));
			}
		}

		for(int x = -radius; x < radius; x++)
		{
			for(int y = -radius; y < radius; y++)
			{
				for(int z = -radius; z < radius; z++)
				{
					if((x * x + y * y + z * z <= radius * radius) || this.world.rand.nextInt(100) < 15)
					{
						killBlock(x + this.x, y + this.y, z + this.z, 0);
					}
				}
			}
		}

		this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

		this.world.spawnParticle("largeexplode", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
		this.world.spawnParticle("hugeexplosion", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);

		this.world.playSoundEffect(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		this.world.spawnParticle("largeexplode", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
	}

	private void killBlock(int x, int y, int z, int id)
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
		else
		{
			setBlock(x, y, z, 0);
		}
	}

	public boolean setBlock(int x, int y, int z, int id)
	{
		if(x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000 && y > 0 && y < 256)
		{
			Chunk chunk = this.world.getChunkFromChunkCoords(x >> 4, z >> 4);

			int id_ = chunk.getBlockID(x & 15, y, z & 15);

			if(id_ == id)
			{
				return false;
			}
			else
			{
				ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];

				if(extendedblockstorage == null)
				{
					if(id == 0)
						return false;

					extendedblockstorage = chunk.getBlockStorageArray()[y >> 4] = new ExtendedBlockStorage(y >> 4 << 4, !this.world.provider.hasNoSky);
				}

				extendedblockstorage.setExtBlockID(x & 15, y & 15, z & 15, id);
				this.world.notifyBlockChange(x, y, z, id_);
				this.world.markBlockForUpdate(x, y, z);
				return true;
			}
		}
		return false;
	}
}