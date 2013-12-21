package com.sci.machinery.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import com.sci.machinery.SciMachinery;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.block.tube.TravellingItem;
import com.sci.machinery.block.tube.TubeBase;
import com.sci.machinery.block.tube.TubeCobble;
import com.sci.machinery.block.tube.TubeDetector;
import com.sci.machinery.block.tube.TubePump;
import com.sci.machinery.block.tube.TubeStone;
import com.sci.machinery.block.tube.TubeValve;
import com.sci.machinery.block.tube.TubeVoid;
import com.sci.machinery.core.BlockCoord;
import com.sci.machinery.core.Utils;
import com.sci.machinery.lib.Reference;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class RenderTube extends TileEntitySpecialRenderer implements IItemRenderer
{
	private static final ResourceLocation tubeIcon = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "blocks/tube.png");

	private static final TubeBase PUMP = new TubePump();
	private static final TubeBase STONE = new TubeStone();
	private static final TubeBase COBBLE = new TubeCobble();
	private static final TubeBase VOID = new TubeVoid();
	private static final TubeBase DETECTOR = new TubeDetector();
	private static final TubeBase VALVE = new TubeValve();

	public static final double O = 0.2D;
	public static final double OA = 0.05D;
	public static final double OF = 0.4D;
	public static final double OO = O * 2;

	private final RenderItem customRenderItem;
	private final EntityItem dummyEntityItem = new EntityItem(null);

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
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
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

			this.bindTexture(tubeIcon);

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

			tess.addVertex(x + O, y + O - OA, z + O);
			GL11.glEnable(GL11.GL_LIGHTING);
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
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f)
	{
		if(!(t instanceof TileTube)) { throw new RuntimeException("Got a non-ITubeConnectable tile in RenderTube!"); }

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

		TileEntity[] tiles = Utils.getAdjacentTiles(tube.worldObj, new BlockCoord(tube.xCoord, tube.yCoord, tube.zCoord));
		for(int i = 0; i < 6; i++)
		{
			if(tube.canConnectTube(tiles[i]))
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

	private void setColor(int itemID, int b)
	{
		if(itemID == SciMachinery.instance.pumpTube.blockID)
		{
			setColor(PUMP, b);
		}
		else if(itemID == SciMachinery.instance.detectorTube.blockID)
		{
			setColor(DETECTOR, b);
		}
		else if(itemID == SciMachinery.instance.voidTube.blockID)
		{
			setColor(VOID, b);
		}
		else if(itemID == SciMachinery.instance.stoneTube.blockID || itemID == SciMachinery.instance.fastStoneTube.blockID)
		{
			setColor(STONE, b);
		}
		else if(itemID == SciMachinery.instance.cobbleTube.blockID || itemID == SciMachinery.instance.fastCobbleTube.blockID)
		{
			setColor(COBBLE, b);
		}
		else if(itemID == SciMachinery.instance.tubeValve.blockID)
		{
			setColor(VALVE, b);
		}
	}

	private void setColor(TileTube t)
	{
		setColor(t.getTube(), t.isPowering() ? 1 : 0);
	}

	private void setColor(TubeBase mat, int b)
	{
		Tessellator tess = Tessellator.instance;
		tess.setColorRGBA(mat.getR(b), mat.getG(b), mat.getB(b), mat.getA(b));
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
}