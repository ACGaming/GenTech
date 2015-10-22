package com.gendeathrow.gentech.handlers;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;
import solarcraft.core.SC_Settings;

import com.gendeathrow.gentech.common.entityplayer.GTPlayerData;
import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.core.Settings;
import com.gendeathrow.gentech.network.packet.PacketGenTech;
import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class GravityGenerator 
{
	
	public static final ArrayList<Entity> inGravField = new ArrayList<Entity>();
	public static final ArrayList<TileEntityGravityGenerator> activeGravGens = new ArrayList<TileEntityGravityGenerator>();
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onLivingUpdate(LivingUpdateEvent event)
	{
//		if (event.entity == null) return;
//		
//		Iterator<TileEntityGravityGenerator> itGens = activeGravGens.iterator();
//		
//		boolean playerinField = false;
//		
//		while(itGens.hasNext())
//		{
//			TileEntityGravityGenerator generator = itGens.next();
//			
//			if(generator == null) 
//			{
//				continue;
//			}
//			
//			double Gravityforce = ((double)generator.gravityForce/100)-1;
//			
//			boolean inField = generator.isEntityinGravityField(event.entity);
//			
//			if(inField && !(event.entityLiving.isInWater() || event.entityLiving.handleLavaMovement()) && !(event.entityLiving instanceof EntityFlying) && !(event.entityLiving instanceof EntityPlayer && ((EntityPlayer)event.entityLiving).capabilities.isFlying))
//			{
//				
//				if(generator.isPowered())
//				{
//					if(!event.entityLiving.onGround)
//					{
//						if(event.entityLiving.dimension == 0) //hard coded for solarcraft for now. 
//						{
//							event.entityLiving.addVelocity(0, - (SC_Settings.gravityFact * 0.07D), 0);
//						}
//
//						// 	Add new velocity
//						if(!GenTech.proxy.isClient()) event.entityLiving.addVelocity(0,- (Gravityforce * 0.07) , 0);
//					}
//				
//					if((Gravityforce+1) > 1)
//					{
//						event.entityLiving.setVelocity((event.entityLiving.motionX/(Gravityforce+1)), event.entityLiving.motionY , (event.entityLiving.motionZ/(Gravityforce+1)));
//					}
//					
//					if(event.entity instanceof EntityPlayerMP && !(event.entity.worldObj.isRemote))
//					{
//						playerinField = true;
////						System.out.println("player in field" + playerinField);
//					}
//				}
//
//
//				
//			}
//			
//			if(!event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityLiving && !event.entityLiving.onGround && event.entityLiving.worldObj.provider.dimensionId == 0)
//			{
//				EntityLiving entityLiving = (EntityLiving)event.entityLiving;
//				
//				double speed = entityLiving.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()/4D;
//				
//				if(entityLiving.getAttackTarget() != null)
//				{
//					double distX = entityLiving.getAttackTarget().posX - entityLiving.posX;
//					double distZ = entityLiving.getAttackTarget().posZ - entityLiving.posZ;
//					
//					entityLiving.motionX = MathHelper.clamp_double(entityLiving.motionX + distX, -speed, speed);
//					entityLiving.motionZ = MathHelper.clamp_double(entityLiving.motionZ + distZ, -speed, speed);
//					
//					if(!entityLiving.getNavigator().noPath())
//					{
//						entityLiving.getNavigator().clearPathEntity(); // If we don't clean out the path it will cause issues when we land somewhere other than the last pathing point
//					}
//				} else if(!entityLiving.getNavigator().noPath())
//				{
//					PathPoint point = entityLiving.getNavigator().getPath().getPathPointFromIndex(entityLiving.getNavigator().getPath().getCurrentPathIndex());
//					
//					if(point != null)
//					{
//						double distX = point.xCoord - entityLiving.posX;
//						double distZ = point.zCoord - entityLiving.posZ;
//						
//						Vec3 motion = Vec3.createVectorHelper(MathHelper.clamp_double(entityLiving.motionX + distX, -speed, speed), entityLiving.motionY, MathHelper.clamp_double(entityLiving.motionZ + distZ, -speed, speed));
//						entityLiving.motionX = motion.xCoord;
//						entityLiving.motionY = motion.yCoord;
//						entityLiving.motionZ = motion.zCoord;
//					}
//				}
//			}
			
//		}
//		
//		if(event.entity instanceof EntityPlayerMP && !(event.entity.worldObj.isRemote)	&& event.entity.worldObj.getTotalWorldTime()%30 == 0) // Culls a bit of the unnecessary processing
//		{
//			EntityPlayerMP player = (EntityPlayerMP) event.entity;
//			GTPlayerData playerExt = GTPlayerData.get(player);
//		
//			if(playerinField != playerExt.inGravField)	
//			{
//				GenTech.instance.network.sendTo(new PacketGenTech(1,playerinField), player);
//				playerExt.inGravField = playerinField;
//			}
//		}
//		


	}
	
	@SubscribeEvent
	public void worldUnload(WorldEvent.Unload event)
	{
		this.activeGravGens.clear();
	}
	

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onTickEvent(TickEvent.WorldTickEvent event)
	{
//		if(event.phase == TickEvent.Phase.START)
//		{
//			//event.type;
//			if(!Settings.GravGen_affectItems) return;
//			
//			
//			for(int i = event.world.loadedEntityList.size() - 1; i >= 0; i--)
//			{
//				Object entry = event.world.loadedEntityList.get(i);
//				
//				if(entry == null || !(entry instanceof Entity) || entry instanceof EntityLivingBase)
//				{
//					continue;
//				}
//				
//				Entity entity = (Entity)entry;
//				Iterator<TileEntityGravityGenerator> itGens = activeGravGens.iterator();
//				
//				while(itGens.hasNext())
//				{
//					TileEntityGravityGenerator generator = itGens.next();
//				
//					if(generator.isPowered() && generator.isEntityinGravityField(entity) && entity.dimension == 0 && !entity.onGround && !(entity.isInWater() || entity.handleLavaMovement()))
//					{
//						entity.motionY = MathHelper.clamp_double(entity.motionY, -3D, 3D); // Make sure entity isn't already too fast for ZeroG
//						entity.addVelocity(0D, -(SC_Settings.gravityFact * 0.037D), 0D); // Give some upward velocity to counter a portion of gravity
//						entity.motionY = MathHelper.clamp_double(entity.motionY, -3D, 3D); // Make sure entity hasn't started going too fast
//						entity.addVelocity(0,- ((((double)generator.gravityForce/100)-1) * 0.037D) , 0);
//					}
//				}
//			}
//		}
	}
	

}
