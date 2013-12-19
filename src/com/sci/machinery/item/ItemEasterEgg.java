package com.sci.machinery.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import com.sci.machinery.SciMachinery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class ItemEasterEgg extends ItemRecord
{
	@SideOnly(Side.CLIENT)
	public static ItemEasterEgg getRecord(String par)
	{
		return SciMachinery.instance.easterEgg;
	}

	public ItemEasterEgg(int par1)
	{
		super(par1, "chicken");
		this.setMaxStackSize(1);
		this.setCreativeTab(SciMachinery.tab);
		this.setUnlocalizedName("chickenEasterEgg");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" }) //minecraft, y u no generics?
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(this.getRecordTitle());
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass)
	{
		if(Item.record11 != null) { return Item.record11.getIconFromDamage(0); }
		return super.getIcon(stack, pass);
	}

	@Override
	public Icon getIconFromDamage(int par1)
	{
		if(Item.record11 != null) { return Item.record11.getIconFromDamage(par1); }
		return super.getIconFromDamage(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.rare;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordTitle()
	{
		return "This is not an easter egg";
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(world.getBlockId(x, y, z) == Block.jukebox.blockID && world.getBlockMetadata(x, y, z) == 0)
			{
				((BlockJukeBox) Block.jukebox).insertRecord(world, x, y, z, itemstack);
				world.playAuxSFXAtEntity((EntityPlayer) null, 1005, x, y, z, this.itemID);
				--itemstack.stackSize;
				return true;
			}
		}
		return false;
	}
}