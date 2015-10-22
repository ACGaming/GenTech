package com.gendeathrow.gentech.client.config;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientSettings {

	public static int hudGravY= 0;
	public static int hudGravX = 0;
	public static boolean showGravHud = true;
 	
	
	public static void readFromNBT(NBTTagCompound compoundTag) 
	{

		
	}

}
