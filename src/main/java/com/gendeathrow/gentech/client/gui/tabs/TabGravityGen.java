package com.gendeathrow.gentech.client.gui.tabs;

import java.util.regex.Pattern;

import com.gendeathrow.gentech.client.gui.elements.GravSliders;
import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.network.Packet;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.core.render.IconRegistry;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementSlider;
import cofh.lib.gui.element.ElementTextFieldFiltered;
import cofh.lib.gui.element.TabBase;
import cofh.lib.gui.element.listbox.SliderHorizontal;

public class TabGravityGen extends TabBase{
	
	TileEntityGravityGenerator generator;
	Container container;

	public TabGravityGen(GuiBase gui, TileEntityGravityGenerator generator, Container container) 
	{

		super(gui);
		this.maxHeight = 160;
		this.maxWidth = 90;
		this.generator = generator;
		this.container = container;

		int yOffset = 0;
		this.addElement(new ElementButton(gui, posXOffset() + 22 + 16, 33, 16, 16, 16, 0, 16, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("9"));
		this.addElement(new ElementButton(gui, posXOffset() + 40 + 16, 33, 16, 16,  0, 0, 0, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("10"));

		this.addElement(new ElementButton(gui, posXOffset() + 22 + 16, 33 + (18 * 1), 16, 16, 16, 0, 16, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("11"));
		this.addElement(new ElementButton(gui, posXOffset() + 40 + 16, 33 + (18 * 1), 16, 16,  0, 0, 0, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("12"));

		this.addElement(new ElementButton(gui, posXOffset() + 22 + 16, 33 + (18 * 2), 16, 16, 16, 0, 16, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("5"));
		this.addElement(new ElementButton(gui, posXOffset() + 40 + 16, 33 + (18 * 2), 16, 16,  0, 0, 0, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("6"));
		
		this.addElement(new ElementButton(gui, posXOffset() + 22 + 16, 33 + (18 * 3), 16, 16, 16, 0, 16, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("7"));
		this.addElement(new ElementButton(gui, posXOffset() + 40 + 16, 33 + (18 * 3), 16, 16,  0, 0, 0, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("8"));

		this.addElement(new ElementButton(gui, posXOffset() + 22 + 16, 33 + (18 * 4), 16, 16, 16, 0, 16, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("1"));
		this.addElement(new ElementButton(gui, posXOffset() + 40 + 16, 33 + (18 * 4), 16, 16,  0, 0, 0, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("2"));

		this.addElement(new ElementButton(gui, posXOffset() + 22 + 16, 33 + (18 * 5), 16, 16, 16, 0, 16, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("3"));
		this.addElement(new ElementButton(gui, posXOffset() + 40 + 16, 33 + (18 * 5), 16, 16,  0, 0, 0, 16, GenTech.MODID + ":textures/gui/widgets.png").setName("4"));

	}
	

	protected void drawForeground() 
	{
		gui.drawIcon(IconRegistry.getIcon("IconGravGen"), posXOffset(), posY + 3,1);
	    
	    if (!isFullyOpened()) 
	    {
	      return;
	    }
	    
	    getFontRenderer().drawString("Gravity Field", posXOffset() + 18 , 10, 4210752);
	    
	    getFontRenderer().drawString("Range in Blocks:", posXOffset() + 3 , 24, 4210752);

	    getFontRenderer().drawString(" N: "+ generator.getGravityFieldRange(ForgeDirection.NORTH), posXOffset() + 5 , 42, 4210752);
	    getFontRenderer().drawString(" S: "+ generator.getGravityFieldRange(ForgeDirection.SOUTH), posXOffset() + 5 , 42 + (18 * 1), 4210752);
	    
	    getFontRenderer().drawString(" E: "+ generator.getGravityFieldRange(ForgeDirection.EAST), posXOffset() + 5 , 42  + (18 * 2), 4210752);
	    getFontRenderer().drawString(" W: "+ generator.getGravityFieldRange(ForgeDirection.WEST), posXOffset() + 5 , 42 + (18 * 3), 4210752);
	    
	    getFontRenderer().drawString("Up: "+ generator.getGravityFieldRange(ForgeDirection.UP), posXOffset() + 5 , 42 + (18 * 4), 4210752);
	    getFontRenderer().drawString("DN: "+ generator.getGravityFieldRange(ForgeDirection.DOWN), posXOffset() + 5 , 42 + (18 * 5), 4210752);

	    
//	    getFontRenderer().drawString(" N: "+ generator.northRange, posXOffset() + 5 , 42, 4210752);
//	    getFontRenderer().drawString(" S: "+ generator.southRange, posXOffset() + 5 , 42 + (18 * 1), 4210752);
//	    
//	    getFontRenderer().drawString(" E: "+ generator.eastRange, posXOffset() + 5 , 42  + (18 * 2), 4210752);
//	    getFontRenderer().drawString(" W: "+ generator.westRange, posXOffset() + 5 , 42 + (18 * 3), 4210752);
//	    
//	    getFontRenderer().drawString("Up: "+ generator.upRange, posXOffset() + 5 , 42 + (18 * 4), 4210752);
//	    getFontRenderer().drawString("DN: "+ generator.downRange, posXOffset() + 5 , 42 + (18 * 5), 4210752);

	    super.drawForeground();
	} 
	

	@Override
	/**
	 * @return Whether the tab should stay open or not.
	 */
	public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) 
	{
		mouseX -= this.posX();
		mouseY -= this.posY;

		boolean shouldStayOpen = false;

		for (int i = 0; i < this.elements.size(); i++) {
			
			ElementBase c = elements.get(i);
			
			if (!c.isVisible() || !c.isEnabled() || !c.intersectsWith(mouseX, mouseY)) 
			{
				continue;
			}

			shouldStayOpen = true;

			if (c.onMousePressed(mouseX, mouseY, mouseButton)) 
			{
				String elementName = c.getName();
				
				Minecraft.getMinecraft().playerController.sendEnchantPacket(this.container.windowId, Integer.parseInt(elementName));
			
				return true;
			}
		}

		return shouldStayOpen;
	}
	
}
