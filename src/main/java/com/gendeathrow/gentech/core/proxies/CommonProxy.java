package com.gendeathrow.gentech.core.proxies;

import net.minecraftforge.common.MinecraftForge;

import com.gendeathrow.gentech.common.entityplayer.GTPlayerData;
import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.handlers.GravityGenerator;
import com.gendeathrow.gentech.handlers.GuiHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public boolean isClient()
	{
		return false;
	}
	
	public boolean isOpenToLAN()
	{
		return false;
	}
	
	public void preInit(FMLPreInitializationEvent event) 
	{

	}

	public void init(FMLInitializationEvent event) 
	{



	}

	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void registerTickHandlers() 
	{
		//FMLCommonHandler.instance().bus().register(new ServerTick());
			
	}

	public void registerEventHandlers() 
	{

		//MinecraftForge.EVENT_BUS.register(new GTChunkHandler());
		
		GravityGenerator gravityGenerator = new GravityGenerator();
		GTPlayerData GTPlayerData = new GTPlayerData();
		
		MinecraftForge.EVENT_BUS.register(gravityGenerator);
		FMLCommonHandler.instance().bus().register(gravityGenerator);
		
		MinecraftForge.EVENT_BUS.register(GTPlayerData);
		FMLCommonHandler.instance().bus().register(GTPlayerData);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(GenTech.instance, new GuiHandler());
		
		
//		EventHandler eventManager = new EventHandler();
//		MinecraftForge.EVENT_BUS.register(eventManager);
//		FMLCommonHandler.instance().bus().register(eventManager);
//		

	}
	
	public void registerRenders()
	{
		
	}
}
