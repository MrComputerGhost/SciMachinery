package com.sci.machinery.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;

public class RenderTube extends TileEntitySpecialRenderer
{
	public static final double OF = 0.4D;
	public static final double O = 0.2D;
	public static final double OA = 0.04D;
	public static final double OO = O * 2;

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f)
	{
		if(!(t instanceof TileTube)) { throw new RuntimeException("Got a non-TileTube tile in RenderTube!"); }

		TileTube tube = (TileTube) t;

		Tessellator tess = Tessellator.instance;
		tess.setColorOpaque(50, 50, 50);

		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + OA, z + 0);
		tess.addVertex(x + O, y + OA, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + O - OA, z + 0);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + O - OA, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + OA, y + O, z + 0);
		tess.addVertex(x + OA, y + 0, z + 0);
		
		tess.addVertex(x + O - OA, y + 0, z + 0);
		tess.addVertex(x + O - OA, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);
		
		//
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		//
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		//
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		//
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		//
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
	}
}