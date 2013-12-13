package com.sci.machinery.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderItemPump extends TileEntitySpecialRenderer
{
	public static final double OF = RenderTube.OF;
	public static final double O = RenderTube.O;
	public static final double OA = RenderTube.OA;
	public static final double OO = RenderTube.OO;

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();

		this.bindTexture(new ResourceLocation("scimachinery", "blocks/tube.png"));

		x += OF;
		y += OF;
		z += OF;

		tess.addVertex(x + 0, y + 0, z - 0.4);
		tess.addVertex(x + 0, y + O, z - 0.4);
		tess.addVertex(x + O , y + O, z - 0.4);
		tess.addVertex(x + O, y + 0, z - 0.4);

		tess.draw();
	}
}