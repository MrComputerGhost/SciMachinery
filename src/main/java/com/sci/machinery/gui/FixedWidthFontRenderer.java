package com.sci.machinery.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FixedWidthFontRenderer
{
	private static final ResourceLocation FONT = new ResourceLocation("minecraft", "textures/font/ascii.png");
	public static int FONT_HEIGHT = 9;
	public static int FONT_WIDTH = 6;
	private int[] charWidth;
	private TextureManager textureManager;
	private int fontDisplayLists;
	private IntBuffer buffer;

	public FixedWidthFontRenderer(GameSettings gamesettings, TextureManager _textureManager)
	{
		this.textureManager = _textureManager;
		this.charWidth = new int['Ā'];
		this.buffer = GLAllocation.createDirectIntBuffer(1024);
		BufferedImage bufferedimage;
		try
		{
			bufferedimage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(FONT).getInputStream());
		}
		catch(IOException ioexception)
		{
			throw new RuntimeException(ioexception);
		}
		int i = bufferedimage.getWidth();
		int j = bufferedimage.getHeight();
		int[] ai = new int[i * j];
		bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
		for(int k = 0; k < 256; k++)
		{
			int l = k % 16;
			int k1 = k / 16;
			int j2 = 7;
			while(j2 >= 0)
			{
				int i3 = l * 8 + j2;
				boolean flag = true;
				for(int l3 = 0; (l3 < 8) && (flag); l3++)
				{
					int i4 = (k1 * 8 + l3) * i;
					int k4 = ai[(i3 + i4)] & 0xFF;
					if(k4 > 0)
					{
						flag = false;
					}
				}
				if(!flag)
				{
					break;
				}
				j2--;
			}
			if(k == 32)
			{
				j2 = 2;
			}
			this.charWidth[k] = (j2 + 2);
		}
		this.fontDisplayLists = GLAllocation.generateDisplayLists(274);
		Tessellator tessellator = Tessellator.instance;
		for(int i1 = 0; i1 < 256; i1++)
		{
			int startSpace = (FONT_WIDTH - this.charWidth[i1]) / 2;
			GL11.glNewList(this.fontDisplayLists + i1, 4864);
			GL11.glTranslatef(startSpace, 0.0F, 0.0F);
			tessellator.startDrawingQuads();
			int l1 = i1 % 16 * 8;
			int k2 = i1 / 16 * 8;
			float f = 7.99F;
			float f1 = 0.0F;
			float f2 = 0.0F;
			tessellator.addVertexWithUV(0.0D, 0.0F + f, 0.0D, l1 / 128.0F + f1, (k2 + f) / 128.0F + f2);
			tessellator.addVertexWithUV(0.0F + f, 0.0F + f, 0.0D, (l1 + f) / 128.0F + f1, (k2 + f) / 128.0F + f2);
			tessellator.addVertexWithUV(0.0F + f, 0.0D, 0.0D, (l1 + f) / 128.0F + f1, k2 / 128.0F + f2);
			tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, l1 / 128.0F + f1, k2 / 128.0F + f2);
			tessellator.draw();
			GL11.glTranslatef(FONT_WIDTH - startSpace, 0.0F, 0.0F);
			GL11.glEndList();
		}
		for(int j1 = 0; j1 < 16; j1++)
		{
			int colour = 0x00CC00;
			int r = colour >> 16 & 0xFF;
			int g = colour >> 8 & 0xFF;
			int b = colour & 0xFF;
			GL11.glNewList(this.fontDisplayLists + 256 + j1, 4864);
			GL11.glColor3f(r / 255.0F, g / 255.0F, b / 255.0F);
			GL11.glEndList();
		}
		GL11.glNewList(this.fontDisplayLists + 256 + 16 + 0, 4864);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, FONT_HEIGHT, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(FONT_WIDTH, FONT_HEIGHT, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(FONT_WIDTH, 0.0D, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glTranslatef(FONT_WIDTH, 0.0F, 0.0F);
		GL11.glEndList();

		GL11.glNewList(this.fontDisplayLists + 256 + 16 + 1, 4864);
		GL11.glTranslatef(FONT_WIDTH, 0.0F, 0.0F);
		GL11.glEndList();
	}

	public void drawString(String s, int x, int y)
	{
		if(s == null) { return; }
		this.textureManager.bindTexture(FONT);

		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0F);
		GL11.glColor3f(0, 204, 0);
		this.buffer.clear();
		for(int i = 0; i < s.length(); i++)
		{
			int j = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(i));
			if((j < 0) || (j >= 192))
			{
				j = ChatAllowedCharacters.allowedCharacters.indexOf('?');
			}
			this.buffer.put(this.fontDisplayLists + j + 32);
			if(this.buffer.remaining() == 0)
			{
				this.buffer.flip();
				GL11.glCallLists(this.buffer);
				this.buffer.clear();
			}
		}
		this.buffer.flip();
		GL11.glCallLists(this.buffer);
		GL11.glPopMatrix();
	}

	public int getStringWidth(String s)
	{
		if(s == null) { return 0; }
		return s.length() * FONT_WIDTH;
	}
}