package com.sci.machinery.block;

import net.minecraft.block.BlockTNT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.entity.EntityNuke;

public class BlockNuke extends BlockTNT
{
	public BlockNuke(int par1)
	{
		super(par1);
		this.setCreativeTab(SciMachinery.tab);
		this.setTextureName("tnt");
	}

	@Override
	public void primeTnt(World par1World, int par2, int par3, int par4, int par5, EntityLivingBase par6EntityLivingBase)
	{
		if(!par1World.isRemote)
		{
			if((par5 & 1) == 1)
			{
				EntityNuke entitytntprimed = new EntityNuke(par1World, (double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), par6EntityLivingBase);
				par1World.spawnEntityInWorld(entitytntprimed);
				par1World.playSoundAtEntity(entitytntprimed, "random.fuse", 1.0F, 1.0F);
			}
		}
	}
}