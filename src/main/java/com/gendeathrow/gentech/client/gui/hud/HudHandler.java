package com.gendeathrow.gentech.client.gui.hud;

import com.gendeathrow.gentech.client.config.ClientSettings;
import com.gendeathrow.gentech.common.entityplayer.GTPlayerData;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HudHandler 
{
	
	public static int playerGravity = 0;
	public static Minecraft mc = Minecraft.getMinecraft();
	
	
	/**
	 * All Enviromine Gui and Hud Items will render here
	 * @param event
	 */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void RenderHud(RenderGameOverlayEvent.Post event)
	{
		if(event.type != ElementType.HELMET || event.isCancelable())
		{
			return;
		}
		
		GTPlayerData player = GTPlayerData.get(Minecraft.getMinecraft().thePlayer);
		
		if(ClientSettings.showGravHud && player.inGravField) mc.fontRenderer.drawString("In Gravity Field", ClientSettings.hudGravX, ClientSettings.hudGravY, 16777215);
		
	}
}
