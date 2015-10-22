package com.gendeathrow.gentech.client.gui.tabs;

import org.lwjgl.opengl.GL11;

import cofh.CoFHCore;
import cofh.core.gui.element.TabScrolledText;
import cofh.core.render.IconRegistry;
import cofh.lib.gui.GuiBase;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.StringHelper;

public class TabInfoGen extends TabScrolledText {

		public static boolean enable;
		public static int defaultSide = 0;
		public static int defaultHeaderColor = 0xe1c92f;
		public static int defaultSubHeaderColor = 0xaaafb8;
		public static int defaultTextColor = 0xffffff;
		public static int defaultBackgroundColor = 0x555555;

		public static void initialize() {

			String category = "Tab.Information";
			enable = CoFHCore.configClient.get(category, "Enable", true);
			defaultSide = MathHelper.clampI(CoFHCore.configClient.get(category, "Side", defaultSide), 0, 1);
			defaultHeaderColor = MathHelper.clampI(CoFHCore.configClient.get(category, "ColorHeader", defaultHeaderColor), 0, 0xffffff);
			defaultSubHeaderColor = MathHelper.clampI(CoFHCore.configClient.get(category, "ColorSubHeader", defaultSubHeaderColor), 0, 0xffffff);
			defaultTextColor = MathHelper.clampI(CoFHCore.configClient.get(category, "ColorText", defaultTextColor), 0, 0xffffff);
			defaultBackgroundColor = MathHelper.clampI(CoFHCore.configClient.get(category, "ColorBackground", defaultBackgroundColor), 0, 0xffffff);
			CoFHCore.configClient.save();
		}

		public TabInfoGen(GuiBase gui, String infoString) {

			this(gui, defaultSide, infoString);
		}

		public TabInfoGen(GuiBase gui, int side, String infoString) {

			super(gui, side, infoString);
			setVisible(enable);

			headerColor = defaultHeaderColor;
			subheaderColor = defaultSubHeaderColor;
			textColor = defaultTextColor;
			backgroundColor = defaultBackgroundColor;
		}
		
		@Override
		public void draw() {

			if (!isVisible()) {
				return;
			}
			drawBackground();
			gui.drawIcon(IconRegistry.getIcon(getIcon()), posXOffset(), posY + 3,1);
			if (!isFullyOpened()) {
				return;
			}
			if (firstLine > 0) {
				gui.drawIcon(IconRegistry.getIcon("IconArrowUp1"), posXOffset() + maxWidth - 20, posY + 16, 1);
			} else {
				gui.drawIcon(IconRegistry.getIcon("IconArrowUp0"), posXOffset() + maxWidth - 20, posY + 16, 1);
			}
			if (firstLine < maxFirstLine) {
				gui.drawIcon(IconRegistry.getIcon("IconArrowDown1"), posXOffset() + maxWidth - 20, posY + 76, 1);
			} else {
				gui.drawIcon(IconRegistry.getIcon("IconArrowDown0"), posXOffset() + maxWidth - 20, posY + 76, 1);
			}
			getFontRenderer().drawStringWithShadow(getTitle(), posXOffset() + 18, posY + 6, headerColor);
			for (int i = firstLine; i < firstLine + numLines; i++) {
				getFontRenderer().drawString(myText.get(i), posXOffset() + 2, posY + 20 + (i - firstLine) * getFontRenderer().FONT_HEIGHT, textColor);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		

		@Override
		public String getIcon() {

			return "IconInformation";
		}

		@Override
		public String getTitle() {

			return StringHelper.localize("info.cofh.information");
		}

	}
