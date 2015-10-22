package com.gendeathrow.gentech.core;

import java.io.File;

import org.apache.logging.log4j.Level;

import com.gendeathrow.gentech.client.config.ClientSettings;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler 
{

	
	public static void LoadConfig()
	{
		GenTech.logger.log(Level.INFO, "Loading Configs...");
		Configuration config;
		File file = new File("config/gentech/gentech.cfg");
		
		config = new Configuration(file);
		
		config.load();
		
		Settings.GravGen_CubicVolumeRFTick = (double) config.getFloat("CubicVolume_RF/Tick", "Gravity Generator", (float) Settings.GravGen_CubicVolumeRFTick, 0, 1, "This Sets how much RF is used per tick based on how much Cubic Meter / How much rang is set in each direction");
		Settings.GravGen_GravityForceRFTick = (double) config.getFloat("GravityForce_RF/Tick", "Gravity Generator", (float) Settings.GravGen_GravityForceRFTick, 0, 1, "This Sets how much RF is used per tick based how much of a Gravity Change for 1G.");
		Settings.GravGen_MaxRFTick = config.getInt("MaxRFTicks", "Gravity Generator", Settings.GravGen_MaxRFTick, 1, 8000, "Set Max RF that the GravityGenerator can charge per tick");
		Settings.GravGen_MaxStorage = config.getInt("MaxRFStorage", "Gravity Generator", Settings.GravGen_MaxStorage, 1, 500000, "Total Storage for GravityGenerator RF");
		Settings.GravGen_affectItems = config.getBoolean("Gravity Affects Items", "Gravity Generator", Settings.GravGen_affectItems, "May cause lag, but has not been tested enough to be sure.");
		
		Settings.GravGen_PlaySound = config.getBoolean("Play Sounds", "Gravity Generator", Settings.GravGen_PlaySound, "Play GravGens Looping Sound.");

//		if(GenTech.proxy.isClient())
//		{
//			ClientSettings.showGravHud = config.getBoolean("GUI Gravity Show", "client settings", ClientSettings.showGravHud, "Play GravGens Looping Sound.");
//			ClientSettings.hudGravY = config.getInt("GUI Gravity Yoffset", "client settings", ClientSettings.hudGravY, 0, 5000, "");
//			ClientSettings.hudGravX = config.getInt("GUI Gravity Xoffset", "client settings", ClientSettings.hudGravY, 0, 5000, "");
//		}
		config.save();
		
		
	}
}
