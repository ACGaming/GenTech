package com.gendeathrow.gentech.client.gui.elements;

import static cofh.lib.gui.element.ElementButtonBase.DISABLED;
import static cofh.lib.gui.element.ElementButtonBase.ENABLED;
import static cofh.lib.gui.element.ElementButtonBase.HOVER;

import org.lwjgl.opengl.GL11;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementSlider;

public class GravSliders extends ElementSlider
{

	public GravSliders(GuiBase containerScreen, int x, int y, int width,	int height, int maxValue) 
	{
		super(containerScreen, x, y, width, height, maxValue);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void dragSlider(int x, int y) 
	{
		System.out.println(x +"-"+y);
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {

		drawModalRect(posX - 1, posY - 1, posX + sizeX + 1, posY + sizeY + 1, borderColor);
		drawModalRect(posX, posY, posX + sizeX, posY + sizeY, backgroundColor);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void drawSlider(int mouseX, int mouseY, int sliderX, int sliderY) {

		int sliderMidX = _sliderWidth / 2;
		int sliderMidY = _sliderHeight / 2;
		int sliderEndX = _sliderWidth - sliderMidX;
		int sliderEndY = _sliderHeight - sliderMidY;

		if (!isEnabled()) {
			gui.bindTexture(DISABLED);
		} else if (isHovering(mouseX, mouseY)) {
			gui.bindTexture(HOVER);
		} else {
			gui.bindTexture(ENABLED);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(sliderX, sliderY, 0, 0, sliderMidX, sliderMidY);
		drawTexturedModalRect(sliderX, sliderY + sliderMidY, 0, 256 - sliderEndY, sliderMidX, sliderEndY);
		drawTexturedModalRect(sliderX + sliderMidX, sliderY, 256 - sliderEndX, 0, sliderEndX, sliderMidY);
		drawTexturedModalRect(sliderX + sliderMidX, sliderY + sliderMidY, 256 - sliderEndX, 256 - sliderEndY, sliderEndX, sliderEndY);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {

		int sliderX = posX + getSliderX();
		int sliderY = posY + getSliderY();

		drawSlider(mouseX, mouseY, sliderX, sliderY);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

		_isDragging = mouseButton == 0;
		update(mouseX, mouseY);
		return true;
	}

	@Override
	public void onMouseReleased(int mouseX, int mouseY) {

		if (_isDragging) {
			onStopDragging();
		}
		_isDragging = false;
	}

	@Override
	public void update(int mouseX, int mouseY) {

		if (_isDragging) {
			dragSlider(mouseX - posX, mouseY - posY);
		}
	}
}
