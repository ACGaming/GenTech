package com.gendeathrow.gentech.client.render.block;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gendeathrow.gentech.client.model.GravityGeneratorModel;
import com.gendeathrow.gentech.core.GenTech;

public class RenderGravityGenerator extends TileEntitySpecialRenderer 
{
	private GravityGeneratorModel model;

	private static final ResourceLocation texture = new ResourceLocation(GenTech.MODID, "textures/blocks/blank.png");


	public RenderGravityGenerator()
	{
		this.model = new GravityGeneratorModel();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8) 
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);
		this.bindTexture(texture);
		GL11.glPushMatrix();
		this.model.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
