package com.gendeathrow.gentech.client.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import com.gendeathrow.gentech.client.gui.hud.HudItem;
import com.gendeathrow.gentech.core.GenTech;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SaveControler 
{
	
	/**
	 * Configuration version number. If changed the version file will be reset to defaults to prevent glitches
	 */
	static final String CONFIG_VERSION = "1.0.0";
	
	/**
	 * The version of the configs last loaded from file. This will be compared to the version number above when determining whether a reset is necessary
	 */
	static String LOADED_VERSION = "1.0.0";
	
    protected static final String dirName = Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "gentech";
    
    protected static File dir = new File(dirName);
    
    public static String SettingsData = "User_Settings";
    
    
    
    public static boolean loadConfig(String name) {
        return loadConfig(name, null);
    }

    public static boolean loadConfig(String name, String dirName) 
    {
        if (dirName != null) {

        	dir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + dirName);
        }

        String fileName = name + ".dat";
        File file = new File(dir, fileName);

        if (!file.exists()) 
        {
            GenTech.logger.warn("Config load canceled, file ("+ file.getAbsolutePath()  +")does not exist. This is normal for first run.");
            return false;
        } else {
        	GenTech.logger.info("Config load successful.");
        }
        
        
        try {
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));
            
            if (nbt.hasNoTags() || !nbt.hasKey(SettingsData))
            {
            	return false;
            }

            ClientSettings.readFromNBT(nbt.getCompoundTag(SettingsData));
//            HUDRegistry.readFromNBT(nbt.getCompoundTag(UISettingsData));
            LOADED_VERSION = nbt.getCompoundTag(SettingsData).getString("CONFIG_VERSION");
//            UpdateNotification.readFromNBT(nbt.getCompoundTag("Notifications"));
            // New HUD Settings will be here
            
//            for (HudItem item : HUDRegistry.getHudItemList()) {
//                NBTTagCompound itemNBT = nbt.getCompoundTag(item.getName());
//                item.loadFromNBT(itemNBT);
//            }       
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return LOADED_VERSION.equals(CONFIG_VERSION);
    }
}
