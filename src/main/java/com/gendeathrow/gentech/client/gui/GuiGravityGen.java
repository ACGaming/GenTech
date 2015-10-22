package com.gendeathrow.gentech.client.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementButton;

import com.gendeathrow.gentech.client.gui.tabs.TabEnergyInfo;
import com.gendeathrow.gentech.client.gui.tabs.TabGravityGen;
import com.gendeathrow.gentech.client.gui.tabs.TabInfoGen;
import com.gendeathrow.gentech.client.gui.tabs.TabRedstoneGen;
import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.inventory.ContainerGravityGenerator;
import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;
import com.gendeathrow.gentech.utils.RenderAssist;

public class GuiGravityGen extends GuiBase
{
	private static final ResourceLocation guiTextures = new ResourceLocation(GenTech.MODID, "textures/gui/gravity_generator_gui.png");
	
	TileEntityGravityGenerator generator;
	
	public GuiGravityGen(EntityPlayer player, TileEntityGravityGenerator tile) 
	{
		super(new ContainerGravityGenerator(player, tile));
		this.generator = tile;
	}

	
	@Override
	public void initGui()
	{
		super.initGui();
		
		addTab(new TabGravityGen(this, this.generator, this.inventorySlots));
		addTab(new TabEnergyInfo(this, generator, false));
		addTab(new TabInfoGen(this, 0, StatCollector.translateToLocalFormatted("gui.gentech.info.gravity_generator.text", new Object[0]))).setVisible(true);

		//addTab(new TabRedstoneGen(this, generator));
		this.addElement(new ElementButton(this, 70, 49, 16, 16, 16, 0, 16, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("13"));
		this.addElement(new ElementButton(this, 132, 49, 16, 16, 0, 0,  0, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("14"));

	}
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mx, int my)
    {
    	super.drawGuiContainerForegroundLayer(mx, my);
    	
        String header = StatCollector.translateToLocalFormatted("tile.gentech.gravity_generator.name", new Object[0]);
        
        this.fontRendererObj.drawString(header, 8 + (this.xSize/2) - (this.fontRendererObj.getStringWidth(header)/2) , 4 , 4210752);
        
        //String Runtime = StatCollector.translateToLocalFormatted("Run Time: ", new Object[0]);
        
        //this.fontRendererObj.drawString(Runtime + this.generator.getTimeLeft(),70, 25 - (getFontRenderer().FONT_HEIGHT/2), 4210752);
        
        String curGrav = StatCollector.translateToLocalFormatted("Current Gravity", new Object[0]);
        
        String overloaded = StatCollector.translateToLocalFormatted("System Overloaded", new Object[0]);
        
       // String forceGrav = String.format("%.2f", this.generator.gravityForce) + "g";
        boolean flag = false;
        boolean overload = false;
        if(this.generator.isPowered() && (this.generator.getInfoEnergyPerTick() > this.generator.getInfoMaxEnergyPerTick()))
        {
        	overload = true;
        	if((Minecraft.getMinecraft().getSystemTime() % 1500) > 500)
        	{
        		flag = true;
        		this.fontRendererObj.drawString(overloaded, 90 + 20 - (getFontRenderer().getStringWidth(overloaded)/2), 25 - (getFontRenderer().FONT_HEIGHT/2), RenderAssist.getColorFromRGBA(255, 0, 0, 255));
        	}
        }
        
        String forceGrav = ((double)((double)this.generator.gravityForce/100)) +"g";
        
        this.fontRendererObj.drawString(curGrav, 90 + 20 - (getFontRenderer().getStringWidth(curGrav)/2), 42 - (getFontRenderer().FONT_HEIGHT/2), 4210752);
        this.getFontRenderer().drawString(forceGrav , 90 + 20 - (getFontRenderer().getStringWidth(forceGrav)/2), 50 + 8 - (getFontRenderer().FONT_HEIGHT/2), 4210752);
        
        if(isWithin(mx, my, 11 , 13, 13, 56))
    	{
    		ArrayList<String> info = new ArrayList<String>();
    		info.add(generator.getInfoEnergyStored() +"/" + this.generator.getMaxEnergyStored(ForgeDirection.NORTH)+ " RF");
    		
    		info.add((flag ? EnumChatFormatting.RED : "") +""+ this.generator.getInfoEnergyPerTick() + EnumChatFormatting.WHITE +" RF/Tick");
    		
    		info.add(this.generator.getInfoMaxEnergyPerTick() + " Max Input RF/Tick");
    		
    		if(overload) info.add(EnumChatFormatting.RED +"Overloaded: Reduce Power");
    		
    		this.drawHoveringText(info, mx - this.guiLeft, my - this.guiTop, this.fontRendererObj);
    	}
     		
    }
    
 	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) 
	{
		
	       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	       
	        this.mc.getTextureManager().bindTexture(guiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        
	        if(generator.power > 0)
	        {
	        	this.drawTexturedModalRect(k + 11,  l + 13 + MathHelper.floor_float(56F * MathHelper.abs(((float)generator.power/(float)generator.getMaxEnergyStored(ForgeDirection.NORTH) - 1))), 0, 168, 11, 56 - MathHelper.floor_float(56F * MathHelper.abs(((float)generator.power/(float)generator.getMaxEnergyStored(ForgeDirection.NORTH) - 1))));
	        }
	       
			this.mouseX = x - guiLeft;
			this.mouseY = y - guiTop;

			GL11.glPushMatrix();
			GL11.glTranslatef(guiLeft, guiTop, 0.0F);
			drawElements(partialTick, false);
			drawTabs(partialTick, false);
			GL11.glPopMatrix();
	
	}
 	
	
	@Override
	protected void mouseClicked(int mX, int mY, int mouseButton) {

		int mXm = mX - guiLeft;
		int mYm = mY - guiTop;

		for (int i = elements.size(); i-- > 0;) {
			ElementBase c = elements.get(i);
			if (!c.isVisible() || !c.isEnabled() || !c.intersectsWith(mXm, mYm)) {
				continue;
			}
			if (c.onMousePressed(mXm, mYm, mouseButton)) 
			{
				String elementName = c.getName();

				Minecraft.getMinecraft().playerController.sendEnchantPacket(this.inventorySlots.windowId, Integer.parseInt(elementName));
			}
		}
		
		super.mouseClicked(mX, mY, mouseButton);
		
	}
//	
	
    public boolean isWithin(int mx, int my, int startX, int startY, int sizeX, int sizeY)
    {
    	return mx - this.guiLeft >= startX && my - this.guiTop >= startY && mx - this.guiLeft < startX + sizeX && my - this.guiTop < startY + sizeY;
    }
    

}
