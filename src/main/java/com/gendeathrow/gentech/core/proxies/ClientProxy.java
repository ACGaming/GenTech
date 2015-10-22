package com.gendeathrow.gentech.core.proxies;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cofh.core.render.IconRegistry;

import com.gendeathrow.gentech.client.gui.hud.HudHandler;
import com.gendeathrow.gentech.core.GenTech;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public boolean isOpenToLAN()
	{
		return false;
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) 
	{

		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) 
	{
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		super.postInit(event);
	}

	@Override
	public void registerTickHandlers() 
	{
		//FMLCommonHandler.instance().bus().register(new ServerTick());
		super.registerTickHandlers();
	}

	@Override
	public void registerEventHandlers() 
	{
//		EventHandler eventManager = new EventHandler();
//		MinecraftForge.EVENT_BUS.register(eventManager);
//		FMLCommonHandler.instance().bus().register(eventManager);
//		
//		HudHandler HudHandler = new HudHandler();
//		
//		MinecraftForge.EVENT_BUS.register(HudHandler);
//		FMLCommonHandler.instance().bus().register(HudHandler);
		
		super.registerEventHandlers();
	}
	
	
	public void registerRenders()
	{
		
	}
	
	public IIcon iconGravGen;
	
	  @SideOnly(Side.CLIENT)
	  @SubscribeEvent
	  public void registerIcons(TextureStitchEvent.Pre paramPre)
	  {
	    if (paramPre.map.getTextureType() == 1) 
	    {
	      IconRegistry.addIcon("IconGravGen", GenTech.MODID +":icons/Icon_Config_GravGen", paramPre.map);
	    }
 
	  }
	  
}
