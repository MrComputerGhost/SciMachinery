package com.sci.machinery.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.block.tube.ITubeConnectable;
import com.sci.machinery.block.tube.TravellingItem;
import com.sci.machinery.block.tube.TubeDetector;
import com.sci.machinery.block.tube.TubePump;
import com.sci.machinery.block.tube.TubeVoid;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;

public class RenderTube extends TileEntitySpecialRenderer implements IItemRenderer
{
	public static final double OF = 0.4D;
	public static final double O = 0.2D;
	public static final double OA = 0.05D;
	public static final double OO = O * 2;

	private final EntityItem dummyEntityItem = new EntityItem(null);
	private final RenderItem customRenderItem;

	public RenderTube()
	{
		customRenderItem = new RenderItem()
		{
			@Override
			public boolean shouldBob()
			{
				return false;
			}

			@Override
			public boolean shouldSpreadItems()
			{
				return false;
			}
		};
		customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f)
	{
		if(!(t instanceof ITubeConnectable)) { throw new RuntimeException("Got a non-ITubeConnectable tile in RenderTube!"); }

		TileTube tube = (TileTube) t;

		x += OF;
		y += OF;
		z += OF;

		GL11.glDisable(GL11.GL_LIGHTING);

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();

		setColor(tube);

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

		TileEntity[] tiles = Utils.getAdjacentTiles(tube.worldObj, new BlockCoord((int) tube.xCoord, (int) tube.yCoord, (int) tube.zCoord));
		for(int i = 0; i < 6; i++)
		{
			if(tiles[i] instanceof ITubeConnectable || tiles[i] instanceof IInventory)
			{
				renderSide(i, x, y, z);
			}
		}

		tess.draw();

		GL11.glEnable(GL11.GL_LIGHTING);

		for(TravellingItem item : tube.getItems())
		{
			renderItem(item, x, y, z);
		}
	}

	private void setColor(TileTube t)
	{
		if(t.getTube() instanceof TubePump)
		{
			setColor(SciMachinery.instance.pumpTubeId, 0);
		}
		else if(t.getTube() instanceof TubeDetector)
		{
			setColor(SciMachinery.instance.detectorTubeId, ((TileTube) t).isPowering() ? 1 : 0);
		}
		else if(t.getTube() instanceof TubeVoid)
		{
			setColor(SciMachinery.instance.voidTubeId, ((TileTube) t).isPowering() ? 1 : 0);
		}
	}

	private void setColor(int id, int b)
	{
		Tessellator tess = Tessellator.instance;
		if(id == SciMachinery.instance.pumpTubeId)
		{
			tess.setColorRGBA(200, 20, 20, 150);
		}
		else if(id == SciMachinery.instance.detectorTubeId)
		{
			if(b == 0)
				tess.setColorRGBA(200, 200, 20, 150);
			else if(b == 1)
				tess.setColorRGBA(250, 250, 20, 150);
		}
		else if(id == SciMachinery.instance.voidTubeId)
		{
			tess.setColorRGBA(20, 20, 200, 150);
		}
	}

	public void renderItem(TravellingItem item, double x, double y, double z)
	{
		if(item == null)
			return;
		if(item.getStack() == null)
			return;
		float renderScale = 0.3f;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.1f, (float) y - 0.175f, (float) z + 0.1f);
		GL11.glTranslatef(0, 0.25F, 0);
		GL11.glScalef(renderScale, renderScale, renderScale);
		dummyEntityItem.setEntityItemStack(item.getStack());
		item.setRotation(item.getRotation() + 0.5f % 360);
		customRenderItem.doRenderItem(dummyEntityItem, 0, 0, 0, 0, item.getRotation());
		GL11.glPopMatrix();
	}

	public void renderSide(int side, double x, double y, double z)
	{
		Tessellator tess = Tessellator.instance;

		if(side == 4)
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

		//

		if(side == 5)
		{
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y + OA, z + 0);
			tess.addVertex(x + O + OO, y + OA, z + 0);
			tess.addVertex(x + O + OO, y + 0, z + 0);

			tess.addVertex(x + O, y + O - OA, z + 0);
			tess.addVertex(x + O, y + O, z + 0);
			tess.addVertex(x + O + OO, y + O, z + 0);
			tess.addVertex(x + O + OO, y + O - OA, z + 0);

			tess.addVertex(x + O + OO, y + OA, z + O);
			tess.addVertex(x + O, y + OA, z + O);
			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O + OO, y + 0, z + O);

			tess.addVertex(x + O + OO, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O, y + O - OA, z + O);
			tess.addVertex(x + O + OO, y + O - OA, z + O);

			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O + OO, y + O, z + O);
			tess.addVertex(x + O + OO, y + O, z + O - OA);
			tess.addVertex(x + O, y + O, z + O - OA);

			tess.addVertex(x + O, y + O, z + 0);
			tess.addVertex(x + O, y + O, z + OA);
			tess.addVertex(x + O + OO, y + O, z + OA);
			tess.addVertex(x + O + OO, y + O, z + 0);

			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O, y + 0, z + O - OA);
			tess.addVertex(x + O + OO, y + 0, z + O - OA);
			tess.addVertex(x + O + OO, y + 0, z + O);

			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O + OO, y + 0, z + 0);
			tess.addVertex(x + O + OO, y + 0, z + OA);
			tess.addVertex(x + O, y + 0, z + OA);
		}

		//

		if(side == 2)
		{
			tess.addVertex(x + 0, y + 0, z - OO);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y + OA, z + 0);
			tess.addVertex(x + 0, y + OA, z - OO);

			tess.addVertex(x + 0, y + O, z - OO);
			tess.addVertex(x + 0, y + O - OA, z - OO);
			tess.addVertex(x + 0, y + O - OA, z + 0);
			tess.addVertex(x + 0, y + O, z + 0);

			tess.addVertex(x + O, y + OA, z + 0);
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y + 0, z - OO);
			tess.addVertex(x + O, y + OA, z - OO);

			tess.addVertex(x + O, y + O, z + 0);
			tess.addVertex(x + O, y + O - OA, z + 0);
			tess.addVertex(x + O, y + O - OA, z - OO);
			tess.addVertex(x + O, y + O, z - OO);

			tess.addVertex(x + OA, y + O, z - OO);
			tess.addVertex(x + 0, y + O, z - OO);
			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x + OA, y + O, z + 0);

			tess.addVertex(x + O, y + O, z - OO);
			tess.addVertex(x + O - OA, y + O, z - OO);
			tess.addVertex(x + O - OA, y + O, z + 0);
			tess.addVertex(x + O, y + O, z + 0);

			tess.addVertex(x + OA, y + 0, z - OO);
			tess.addVertex(x + OA, y + 0, z + 0);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y + 0, z - OO);

			tess.addVertex(x + O - OA, y + 0, z - OO);
			tess.addVertex(x + O, y + 0, z - OO);
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O - OA, y + 0, z + 0);
		}

		//

		if(side == 3)
		{
			tess.addVertex(x + 0, y + 0, z + O + OO);
			tess.addVertex(x + 0, y + OA, z + O + OO);
			tess.addVertex(x + 0, y + OA, z + O);
			tess.addVertex(x + 0, y + 0, z + O);

			tess.addVertex(x + 0, y + O, z + O + OO);
			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + 0, y + O - OA, z + O);
			tess.addVertex(x + 0, y + O - OA, z + O + OO);

			tess.addVertex(x + O, y + OA, z + O);
			tess.addVertex(x + O, y + OA, z + O + OO);
			tess.addVertex(x + O, y + 0, z + O + OO);
			tess.addVertex(x + O, y + 0, z + O);

			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O + OO);
			tess.addVertex(x + O, y + O - OA, z + O + OO);
			tess.addVertex(x + O, y + O - OA, z + O);

			tess.addVertex(x + OA, y + O, z + O + OO);
			tess.addVertex(x + OA, y + O, z + O);
			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + 0, y + O, z + O + OO);

			tess.addVertex(x + O, y + O, z + O + OO);
			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O - OA, y + O, z + O);
			tess.addVertex(x + O - OA, y + O, z + O + OO);

			tess.addVertex(x + OA, y + 0, z + O + OO);
			tess.addVertex(x + 0, y + 0, z + O + OO);
			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + OA, y + 0, z + O);

			tess.addVertex(x + O - OA, y + 0, z + O + OO);
			tess.addVertex(x + O - OA, y + 0, z + O);
			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O, y + 0, z + O + OO);
		}

		//

		if(side == 1)
		{
			tess.addVertex(x + OA, y + O + OO, z + O);
			tess.addVertex(x + 0, y + O + OO, z + O);
			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + OA, y + O, z + O);

			tess.addVertex(x + O, y + O + OO, z + O);
			tess.addVertex(x + O - OA, y + O + OO, z + O);
			tess.addVertex(x + O - OA, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O);

			tess.addVertex(x + O, y + O + OO, z + 0);
			tess.addVertex(x + O, y + O + OO, z + OA);
			tess.addVertex(x + O, y + O, z + OA);
			tess.addVertex(x + O, y + O, z + 0);

			tess.addVertex(x + O, y + O + OO, z + O - OA);
			tess.addVertex(x + O, y + O + OO, z + O);
			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O - OA);

			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x + 0, y + O + OO, z + 0);
			tess.addVertex(x + OA, y + O + OO, z + 0);
			tess.addVertex(x + OA, y + O, z + 0);

			tess.addVertex(x + O - OA, y + O, z + 0);
			tess.addVertex(x + O - OA, y + O + OO, z + 0);
			tess.addVertex(x + O, y + O + OO, z + 0);
			tess.addVertex(x + O, y + O, z + 0);

			tess.addVertex(x + 0, y + O + OO, z + OA);
			tess.addVertex(x + 0, y + O + OO, z + 0);
			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x + 0, y + O, z + OA);

			tess.addVertex(x + 0, y + O + OO, z + O);
			tess.addVertex(x + 0, y + O + OO, z + O - OA);
			tess.addVertex(x + 0, y + O, z + O - OA);
			tess.addVertex(x + 0, y + O, z + O);
		}

		//

		if(side == 0)
		{
			tess.addVertex(x + OA, y + 0, z + O);
			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + 0, y - OO, z + O);
			tess.addVertex(x + OA, y - OO, z + O);

			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O - OA, y + 0, z + O);
			tess.addVertex(x + O - OA, y - OO, z + O);
			tess.addVertex(x + O, y - OO, z + O);

			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y + 0, z + OA);
			tess.addVertex(x + O, y - OO, z + OA);
			tess.addVertex(x + O, y - OO, z + 0);

			tess.addVertex(x + O, y + 0, z + O - OA);
			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O, y - OO, z + O);
			tess.addVertex(x + O, y - OO, z + O - OA);

			tess.addVertex(x + 0, y - OO, z + 0);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + OA, y + 0, z + 0);
			tess.addVertex(x + OA, y - OO, z + 0);

			tess.addVertex(x + O - OA, y - OO, z + 0);
			tess.addVertex(x + O - OA, y + 0, z + 0);
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y - OO, z + 0);

			tess.addVertex(x + 0, y + 0, z + OA);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y - OO, z + 0);
			tess.addVertex(x + 0, y - OO, z + OA);

			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + 0, y + 0, z + O - OA);
			tess.addVertex(x + 0, y - OO, z + O - OA);
			tess.addVertex(x + 0, y - OO, z + O);
		}
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if(type == ItemRenderType.INVENTORY)
		{
			GL11.glPushMatrix();
			double x = 0;
			double y = 0;
			double z = 0;

			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();

			GL11.glDisable(GL11.GL_LIGHTING);

			setColor(item.itemID, 0);

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

			renderSide(2, x, y, z);
			renderSide(3, x, y, z);

			tess.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		else
		{
			double x = 0;
			double y = 0.75;
			double z = 0;

			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();

			GL11.glDisable(GL11.GL_LIGHTING);

			setColor(item.itemID, 0);

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

			renderSide(2, x, y, z);
			renderSide(3, x, y, z);

			tess.draw();

			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}
}