package com.sci.machinery.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import com.sci.machinery.block.computer.TileEntityComputer;
import com.sci.machinery.lib.Reference;
import com.sci.machinery.network.PacketKeyPress;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class GUIComputerTerminal extends GuiScreen
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/guis/computer_terminal.png");

	private final int xSize = 250;
	private final int ySize = 180;
	private TileEntityComputer tileEntity;

	private int timer;
	private boolean cursor;

	public GUIComputerTerminal(TileEntityComputer tileEntity)
	{
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui()
	{
	}

	@Override
	public void updateScreen()
	{
		timer++;
		if(timer == 10)
		{
			timer = 0;
			cursor = !cursor;
		}
	}

	@Override
	public void onGuiClosed()
	{
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		final float SCALE = 0.65f;
		GL11.glPushMatrix();
		GL11.glTranslated(k + 7, l + 7, 0);
		GL11.glScaled(SCALE, SCALE, SCALE);
		fontRenderer.drawString("root@smc:~$" + (cursor ? " _" : ""), 0, 0, 0x00CC00);
		GL11.glPopMatrix();

		this.drawCenteredString(this.fontRenderer, "Computer", this.width / 2, (l - 10), 16777215);
		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char c, int i)
	{
		super.keyTyped(c, i);
		PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketKeyPress(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, c, i)));
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}