package com.gendeathrow.gentech.common.entityplayer;

import com.gendeathrow.gentech.core.GenTech;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GTPlayerData implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "GenTech";
	
	public EntityPlayer player;
	public boolean inGravField;
	public int gravity;

	
	public GTPlayerData(){}
	
	public GTPlayerData(EntityPlayer player)
	{
		this.player = player;
		this.inGravField = false;
		this.gravity = 0;
	}
	
	@Override
	public void init(Entity entity, World world) {}
	
	public static void register(EntityPlayer player) throws InstantiationException, ReflectiveOperationException, Exception, Throwable
	{
		player.registerExtendedProperties(GTPlayerData.EXT_PROP_NAME, new GTPlayerData(player));
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{
		
	}
	
	/**
	 * Returns ExtendedPlayer properties for player
	 */
	public static final GTPlayerData get(EntityPlayer player) 
	{
		return (GTPlayerData) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) throws InstantiationException, ReflectiveOperationException, Exception, Throwable {
	
		if (event.entity instanceof EntityPlayer) 
		{
			if (GTPlayerData.get((EntityPlayer) event.entity) == null) 
			{
				GenTech.logger.info("Registering skill properties for player");
				GTPlayerData.register((EntityPlayer) event.entity);
			}			
		}
	}
	

}
