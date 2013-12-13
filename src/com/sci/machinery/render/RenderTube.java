package com.sci.machinery.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
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

		x += OF;
		y += OF;
		z += OF;

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();

		this.bindTexture(new ResourceLocation("scimachinery", "blocks/tube.png"));

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

		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + 0, y + OA, z + O);
		tess.addVertex(x + 0, y + OA, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);

		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + O - OA, z + 0);
		tess.addVertex(x + 0, y + O - OA, z + O);

		tess.addVertex(x + 0, y + O, z + OA);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + OA);

		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + O - OA);
		tess.addVertex(x + 0, y + 0, z + O - OA);
		tess.addVertex(x + 0, y + 0, z + O);

		//

		tess.addVertex(x + O, y + OA, z + 0);
		tess.addVertex(x + O, y + OA, z + O);
		tess.addVertex(x + O, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + 0);

		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + O - OA, z + O);
		tess.addVertex(x + O, y + O - OA, z + 0);

		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + OA);
		tess.addVertex(x + O, y + 0, z + OA);
		tess.addVertex(x + O, y + 0, z + 0);

		tess.addVertex(x + O, y + O, z + O - OA);
		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + O - OA);

		//

		tess.addVertex(x + O, y + OA, z + O);
		tess.addVertex(x + 0, y + OA, z + O);
		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + O);

		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + O - OA, z + O);
		tess.addVertex(x + O, y + O - OA, z + O);

		tess.addVertex(x + OA, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + OA, y + 0, z + O);

		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O - OA, y + O, z + O);
		tess.addVertex(x + O - OA, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + O);

		//

		tess.addVertex(x + OA, y + O, z + O);
		tess.addVertex(x + OA, y + O, z + 0);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + O, z + O);

		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + O, z + O - OA);
		tess.addVertex(x + 0, y + O, z + O - OA);

		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + O, z + OA);
		tess.addVertex(x + O, y + O, z + OA);
		tess.addVertex(x + O, y + O, z + 0);

		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O - OA, y + O, z + 0);
		tess.addVertex(x + O - OA, y + O, z + O);

		//

		tess.addVertex(x + OA, y + 0, z + O);
		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + OA, y + 0, z + 0);

		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + 0, y + 0, z + O - OA);
		tess.addVertex(x + O, y + 0, z + O - OA);
		tess.addVertex(x + O, y + 0, z + O);

		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + OA);
		tess.addVertex(x + 0, y + 0, z + OA);

		tess.addVertex(x + O - OA, y + 0, z + O);
		tess.addVertex(x + O - OA, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + O);

		/*
		 * x += OF; y += OF; z += OF;
		 */

		renderSide(5, x, y, z);

		tess.draw();
	}

	public void renderSide(int side, double x, double y, double z)
	{
		Tessellator tess = Tessellator.instance;

		if(side == 5)
		{
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x - OO, y + 0, z + 0);
			tess.addVertex(x - OO, y + OA, z + 0);
			tess.addVertex(x + 0, y + OA, z + 0);

			tess.addVertex(x + 0, y + O - OA, z + 0);
			tess.addVertex(x - OO, y + O - OA, z + 0);
			tess.addVertex(x - OO, y + O, z + 0);
			tess.addVertex(x + 0, y + O, z + 0);

			tess.addVertex(x - OO, y + OA, z + O);
			tess.addVertex(x - OO, y + 0, z + O);
			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + 0, y + OA, z + O);

			tess.addVertex(x - OO, y + O, z + O);
			tess.addVertex(x - OO, y + O - OA, z + O);
			tess.addVertex(x + 0, y + O - OA, z + O);
			tess.addVertex(x + 0, y + O, z + O);

			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + 0, y + O, z + O - OA);
			tess.addVertex(x - OO, y + O, z + O - OA);
			tess.addVertex(x - OO, y + O, z + O);

			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x - OO, y + O, z + 0);
			tess.addVertex(x - OO, y + O, z + OA);
			tess.addVertex(x + 0, y + O, z + OA);

			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x - OO, y + 0, z + O);
			tess.addVertex(x - OO, y + 0, z + O - OA);
			tess.addVertex(x + 0, y + 0, z + O - OA);

			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y + 0, z + OA);
			tess.addVertex(x - OO, y + 0, z + OA);
			tess.addVertex(x - OO, y + 0, z + 0);
		}
		else if(side == 4)
		{

		}
	}

	private TileEntity[] getAdjacentTiles(IBlockAccess world, int x, int y, int z)
	{
		TileEntity[] t = new TileEntity[6];
		t[0] = world.getBlockTileEntity(x + 1, y, z);
		t[1] = world.getBlockTileEntity(x - 1, y, z);
		t[2] = world.getBlockTileEntity(x, y + 1, z);
		t[3] = world.getBlockTileEntity(x, y - 1, z);
		t[4] = world.getBlockTileEntity(x, y, z + 1);
		t[5] = world.getBlockTileEntity(x, y, z - 1);
		return t;
	}
}