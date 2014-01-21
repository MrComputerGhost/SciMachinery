package com.sci.machinery.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class EntityNuke extends EntityTNTPrimed
{
	public EntityNuke(World par1World)
	{
		super(par1World);
		this.fuse = 20 * 15;
	}

	public EntityNuke(World par1World, double par2, double par4, double par6, EntityLivingBase par8EntityLivingBase)
	{
		super(par1World, par2, par4, par6, par8EntityLivingBase);
		this.fuse = 20 * 15;
	}

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if(this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}

		if(this.fuse-- <= 0)
		{
			this.setDead();

			if(!this.worldObj.isRemote)
			{
				this.explode();
			}
		}
		else
		{
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode()
	{
		float f = 4.0F;
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
		Nuke nuke = new Nuke(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
		nuke.explode();
	}
}