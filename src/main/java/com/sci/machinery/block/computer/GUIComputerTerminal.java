package com.sci.machinery.block.computer;

import net.minecraft.client.gui.GuiScreen;

public class GUIComputerTerminal extends GuiScreen
{
	private TileEntityComputer tileEntity;

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
	}

	@Override
	public void onGuiClosed()
	{
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Computer", this.width / 2, 40, 16777215);
		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		super.keyTyped(par1, par2);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}