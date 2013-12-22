package com.sci.machinery.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import com.sci.machinery.block.TileCircuitMaker;
import com.sci.machinery.lib.Reference;
import com.sci.machinery.network.PacketButtonPressed;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GUICircuitMaker extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "guis/circuit_maker.png");

	private TileCircuitMaker tileEntity;

	public GUICircuitMaker(InventoryPlayer inventoryPlayer, TileCircuitMaker tileEntity)
	{
		super(null);
		ContainerCircuitMaker cont = new ContainerCircuitMaker(inventoryPlayer, tileEntity);
		this.inventorySlots = cont;
		cont.setGUI(this);
		this.tileEntity = tileEntity;
	}
	
	@SuppressWarnings("unchecked")
	// minecraft, USE FECKING GENERICS!
	@Override
	public void initGui()
	{
		super.initGui();
		this.buttonList.add(new GuiButton(1, guiLeft + 105, guiTop + 47, 59, 20, "Craft"));
	}

	public void setCraftable(boolean c)
	{
		((GuiButton)this.buttonList.get(0)).enabled = c;
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
		case 1:
		{
			PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketButtonPressed(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, button.id)));
			break;
		}
		default:
		{
			break;
		}
		}
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
		
		int i1 = this.tileEntity.getProgressScaled(24);
        this.drawTexturedModalRect(k + 108, l + 21, 176, 0, i1 + 1, 16);
	}
}