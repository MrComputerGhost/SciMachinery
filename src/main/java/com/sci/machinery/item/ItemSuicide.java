package com.sci.machinery.item;

import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;

public class ItemSuicide extends Item
{
	private static final Random RANDOM = new Random();

	public ItemSuicide(int par1)
	{
		super(par1);
		this.setCreativeTab(SciMachinery.tab);
		this.setUnlocalizedName("suicide");
	}

	public ItemStack onItemRightClick(ItemStack s, World w, EntityPlayer p)
	{
		suicide(w, p);
		return s;
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer p, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		suicide(par3World, p);
		return true;
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass)
	{
		return Item.fishRaw.getIcon(stack, pass);
	}

	@Override
	public Icon getIconFromDamage(int par1)
	{
		return Item.fishRaw.getIconFromDamage(par1);
	}

	private void suicide(World world, EntityPlayer p)
	{
		ItemStack t = new ItemStack(Item.skull, 1, 3);
		NBTTagCompound root = new NBTTagCompound();
		root.setString("SkullOwner", p.username);
		t.setTagCompound(root);
		if(!world.isRemote)
			world.spawnEntityInWorld(new EntityItem(world, p.posX, p.posY, p.posZ, t));

		p.setLastAttacker(p);
		p.attackEntityFrom(new DamageSuicide(p), 420);
	}

	public static class DamageSuicide extends DamageSource
	{
		private EntityPlayer p;

		public DamageSuicide(EntityPlayer p)
		{
			super("suicide");
			this.p = p;
		}

		public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase)
		{
			int sel = RANDOM.nextInt(2 + 1) + 1;
			return ChatMessageComponent.createFromText(p.username + " " + StatCollector.translateToLocal("death.message" + sel));
		}
	}
}