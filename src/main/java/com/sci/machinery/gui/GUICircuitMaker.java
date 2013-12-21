package com.sci.machinery.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import com.sci.machinery.block.TileCircuitMaker;
import com.sci.machinery.lib.Reference;

public class GUICircuitMaker extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "guis/circuit_maker.png");

	public GUICircuitMaker(InventoryPlayer inventoryPlayer, TileCircuitMaker tileEntity)
	{
		super(new ContainerCircuitMaker(inventoryPlayer, tileEntity));
	}

	@SuppressWarnings("unchecked")
	// minecraft, USE FECKING GENERICS!
	@Override
	public void initGui()
	{
		super.initGui();
		this.buttonList.add(new GuiButton(1, guiLeft + 105, guiTop + 47, 59, 20, "Craft"));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		//TODO send packet, do stuffs
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2)
	{
		fontRenderer.drawString("Circuit Maker", 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTexture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}