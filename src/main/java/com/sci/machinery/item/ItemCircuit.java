package com.sci.machinery.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import com.sci.machinery.SciMachinery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCircuit extends Item
{
	private static final String[] CIRCUIT_NAMES = new String[]
	{ "basic", "timing", "alu", "control", "cpu", "ram64" };

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public ItemCircuit(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setCreativeTab(SciMachinery.tab);
		this.setUnlocalizedName("circuit");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		StringBuilder unlocalizedName = new StringBuilder("item.");
		int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, 15);
		
		unlocalizedName.append(CIRCUIT_NAMES[meta]).append("Circuit");

		return unlocalizedName.toString();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		int i = MathHelper.clamp_int(par1, 0, 15);
		return this.icons[i];
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	// come on minecraft..
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flags)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
		}
		else
		{
			if(hasDetails(stack))
				list.add("Hold §eShift§r for Details");
		}
	}

	public boolean hasDetails(ItemStack stack)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	// come on minecraft..
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < CIRCUIT_NAMES.length; i++)
		{
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		this.icons = new Icon[CIRCUIT_NAMES.length];

		for(int i = 0; i < CIRCUIT_NAMES.length; i++)
		{
			icons[i] = iconRegister.registerIcon("scimachinery:circuit_" + CIRCUIT_NAMES[i]);
		}
	}
}