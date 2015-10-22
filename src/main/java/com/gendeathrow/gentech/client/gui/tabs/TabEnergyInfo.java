package com.gendeathrow.gentech.client.gui.tabs;

import org.lwjgl.opengl.GL11;

import cofh.api.tileentity.IEnergyInfo;
import cofh.core.gui.element.TabEnergy;
import cofh.core.render.IconRegistry;
import cofh.lib.gui.GuiBase;
import cofh.lib.util.helpers.StringHelper;

public class TabEnergyInfo extends TabEnergy{

	public TabEnergyInfo(GuiBase gui, IEnergyInfo container, boolean isProducer)
	{
		super(gui, container, isProducer);
		this.myContainer = container;
		this.isProducer = isProducer;
	}

	IEnergyInfo myContainer;
	
	boolean isProducer;

	@Override
	public void draw() {

		drawBackground();
		gui.drawIcon(IconRegistry.getIcon("IconEnergy"), posXOffset(), posY + 3, 1);
		if (!isFullyOpened()) {
			return;
		}
		String powerDirection = this.isProducer ? "info.cofh.energyProduce" : "info.cofh.energyConsume";

		getFontRenderer().drawStringWithShadow(StringHelper.localize("info.cofh.energy"), posXOffset() + 20, posY + 6, headerColor);
		getFontRenderer().drawStringWithShadow(StringHelper.localize(powerDirection) + ":", posXOffset() + 6, posY + 18, subheaderColor);
		getFontRenderer().drawString(myContainer.getInfoEnergyPerTick() + " RF/t", posXOffset() + 14, posY + 30, textColor);
		getFontRenderer().drawStringWithShadow(StringHelper.localize("info.cofh.maxEnergyPerTick") + ":", posXOffset() + 6, posY + 42, subheaderColor);
		getFontRenderer().drawString(myContainer.getInfoMaxEnergyPerTick() + " RF/t", posXOffset() + 14, posY + 54, textColor);
		getFontRenderer().drawStringWithShadow(StringHelper.localize("info.cofh.energyStored") + ":", posXOffset() + 6, posY + 66, subheaderColor);
		getFontRenderer().drawString(myContainer.getInfoEnergyStored() + " RF", posXOffset() + 14, posY + 78, textColor);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
