package com.gendeathrow.gentech.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;
import cofh.api.block.IDismantleable;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.tileentity.IEnergyInfo;
import cofh.api.tileentity.IRedstoneControl;

import com.gendeathrow.gentech.client.audio.IShouldLoop;
import com.gendeathrow.gentech.client.audio.LoopingSound;
import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.core.Settings;
import com.gendeathrow.gentech.network.packet.PacketMachines;
import com.gendeathrow.gentech.tileentity.helpers.IGuiPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGravityGenerator extends TileEntity implements IGuiPacket, IShouldLoop, IInventory,IEnergyReceiver, IRedstoneControl, IEnergyInfo
{
	public int power = 0;
	
	private boolean isCreative;
	
	public int machineUsage = 5;
	
	public AxisAlignedBB gravityField;
	
	public int gravityForce = 100; // 1G = 100
	
	ItemStack[] itemStacks = new ItemStack[1];
	
	public int[] gravityfieldRange = new int[] {10,10,10,10,10,10};

	public boolean isPowered = false;
	private boolean wasPowerd = false;
	
	private ControlMode controlMode = ControlMode.HIGH;

	private boolean isRecievingPower;
	
	public boolean hasChanged = true;
	
	ResourceLocation audioLoop = new ResourceLocation(GenTech.MODID,"gravity_generator");
	
	private boolean firstLoad = true;
	
	public List<Entity> inGravField = new ArrayList();
	public List<Entity> prevGravField = new ArrayList(); 
	
	private int maxPower = Settings.GravGen_MaxStorage;
	
	@Override
	public void updateEntity()
	{
//		if(firstLoad && GenTech.proxy.isClient()) 
//		{ 
//			Minecraft.getMinecraft().getSoundHandler().playSound(new LoopingSound(audioLoop,this,.5f));
//
//			this.firstLoad=false; 
//		}

//		
//		if(worldObj.isRemote)
//		{
//			if(this.isPowered())
//			{
//				handleGravity();
//			}
//			
//			return;
//		}
//		

		if(this.isCreative) this.power = this.getMaxEnergyStored(ForgeDirection.NORTH);
		
		machineUsage = updatePowerReq();

		if((power >= machineUsage && this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)))
		{
			this.changePowerState(true);
			
			handleGravity();
			
			power -= machineUsage;
			
			if(power < SC_Settings.machineUsage)
			{
				this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, SolarCraft.airEmitter);
			}
	
		}else
		{
			deactivateGenerator();
		}
		
		
		 chargeBattery();
		 
		 super.updateEntity();
			
	}
	
	
//	if(this.getWorldObj().getTotalWorldTime()%30 == 0) // Culls a bit of the unnecessary processing
//	{
//		this.inGravField.clear();
//		this.inGravField.addAll(this.worldObj.getEntitiesWithinAABB(Entity.class, this.gravityField));
//		
//		if(!GravityGenerator.activeGravGens.contains(this)) GravityGenerator.activeGravGens.add(this);
//		
//		if(this.hasChanged)
//		{
//			this.updateGravField();
//			//this.gravityField = AxisAlignedBB.getBoundingBox(this.xCoord - this.getGravityFieldRange(ForgeDirection.WEST) - 1, this.yCoord - this.getGravityFieldRange(ForgeDirection.DOWN) - 1 , this.zCoord - this.getGravityFieldRange(ForgeDirection.NORTH) - 1, this.xCoord + this.getGravityFieldRange(ForgeDirection.EAST) + 1, this.yCoord + this.getGravityFieldRange(ForgeDirection.UP) + 1, this.zCoord + this.getGravityFieldRange(ForgeDirection.SOUTH) + 1);
//			this.hasChanged = false;
//		}
//	}
	private boolean changePowerState(boolean state)
	{
		if(state != this.isPowered())
		{
			this.isPowered = state;
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, state ? 1 : 0, 2);
		}
		
		return state;
	}
	
	private void handleGravity()
	{
		if(this.gravityField == null) return;
		
		boolean flag = false;
		List<Entity> leftGravField = null;
		
		if(this.getWorldObj().getTotalWorldTime()%10 == 0) // Culls a bit of the unnecessary processing
		{
			this.inGravField = this.worldObj.getEntitiesWithinAABB(Entity.class, this.gravityField);
		}
		
		if(this.inGravField == null || this.inGravField.size() <= 0) 
		{
			return;
		}
		
		double Gravityforce = ((double)this.gravityForce/100)-1;
		
		for(Entity entity : this.inGravField)
		{
			EntityLivingBase living = null;
			
			if(entity == null) continue;
			
			if(entity instanceof EntityLivingBase)
			{
				living = (EntityLivingBase)entity;
				
				if(!(living.isInWater() || living.handleLavaMovement()) && !(living instanceof EntityFlying) && !(living instanceof EntityPlayer && ((EntityPlayer)living).capabilities.isFlying))
				{
					
			
					if(entity.dimension == 0) //hard coded for solarcraft for now. 
					{
						living.addVelocity(0, - (SC_Settings.gravityFact * 0.07D), 0);
					}

					// 	Add new velocity
					living.addVelocity(0,- (Gravityforce * 0.07) , 0);

					if((Gravityforce+1) > 1)
					{
						living.setVelocity(living.motionX/(Gravityforce+1), living.motionY ,living.motionZ/(Gravityforce+1));
					}
	
				}
			}else
			{
				entity.motionY = MathHelper.clamp_double(entity.motionY, -3D, 3D); // Make sure entity isn't already too fast for ZeroG
				entity.addVelocity(0D, -(SC_Settings.gravityFact * 0.037D), 0D); // Give some upward velocity to counter a portion of gravity
				
				
				entity.motionY = MathHelper.clamp_double(entity.motionY, -3D, 3D); // Make sure entity hasn't started going too fast
				entity.addVelocity(0,-(Gravityforce * 0.037D) , 0);
				//entity.addVelocity(0,- ((((double)gravityForce/100)-1) * 0.037D) , 0);
			}
		
		}
	}
	
	private void chargeBattery()
	{
		if(power < this.getMaxEnergyStored(ForgeDirection.NORTH) && this.getStackInSlot(0) != null)
		{
			ItemStack stack = this.getStackInSlot(0);
			
			if(stack.getItem() instanceof IEnergyContainerItem)
			{
				power += ((IEnergyContainerItem)stack.getItem()).extractEnergy(stack, (power + Settings.GravGen_MaxRFTick) <= this.getMaxEnergyStored(ForgeDirection.DOWN) ? Settings.GravGen_MaxRFTick : this.getMaxEnergyStored(ForgeDirection.DOWN) - power, false);
			}
		}	
	}
	
	public void updateGravField()
	{
		this.gravityField = AxisAlignedBB.getBoundingBox(this.xCoord - this.getGravityFieldRange(ForgeDirection.WEST) - 1, this.yCoord - this.getGravityFieldRange(ForgeDirection.DOWN) - 1 , this.zCoord - this.getGravityFieldRange(ForgeDirection.NORTH) - 1, this.xCoord + this.getGravityFieldRange(ForgeDirection.EAST) + 1, this.yCoord + this.getGravityFieldRange(ForgeDirection.UP) + 1, this.zCoord + this.getGravityFieldRange(ForgeDirection.SOUTH) + 1);
	}


	public boolean isCreative()
	{
		return this.isCreative;
	}
	
	public TileEntity setCreative(Boolean value)
	{
		this.isCreative = value;
		return this;
	}
	
	public int updatePowerReq()
	{
		int currentPowerUsage = 5;

		// Increase power based on CubicVolume of GravityField
		currentPowerUsage += (int) Math.round(this.getGravityFieldCM() * Settings.GravGen_CubicVolumeRFTick);
		
		currentPowerUsage += (int) Math.round((Math.abs((this.gravityForce-100) * Settings.GravGen_GravityForceRFTick)));
		
		return currentPowerUsage;
	}
	
	public boolean isEntityinGravityField(Entity entity)
	{
		Vec3 vector = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
		
		return this.gravityField.isVecInside(vector);
	}
	
	public final boolean redstoneControlOrDisable()
	{
		return (this.controlMode.isDisabled()) || (this.isPowered() == this.controlMode.getState());
	}
	
	public void deactivateGenerator()
	{
		this.changePowerState(false);
 	}
	
	@Override
	public void readFromNBT(NBTTagCompound tags)
	{
		super.readFromNBT(tags);
		
		this.power = tags.getInteger("power");
		
		this.isCreative = tags.getBoolean("isCreative");
		
		this.gravityForce = tags.getInteger("gravityForce");
		
		this.gravityfieldRange = tags.getIntArray("gravityfieldRange");

    	itemStacks[0] = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("RFInput"));
    	
    	updateGravField();
		
	}

	@Override
	public void writeToNBT(NBTTagCompound tags)
	{
		super.writeToNBT(tags);
		
		tags.setInteger("power", this.power);
		
		tags.setBoolean("isCreative", this.isCreative);
		
		tags.setInteger("gravityForce", this.gravityForce);
		
		tags.setIntArray("gravityfieldRange", this.gravityfieldRange);
		
		if(itemStacks[0] != null)
		{
    		NBTTagCompound stackTags = new NBTTagCompound();
    		stackTags = itemStacks[0].writeToNBT(stackTags);
    		tags.setTag("RFInput", stackTags);
		}
	}
	
	
	   @Override
	   public Packet getDescriptionPacket()
	   {
	       NBTTagCompound syncData = new NBTTagCompound();
	       this.writeToNBT(syncData);
	       return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	   }

	   @Override
	   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	   {
		   //System.out.println("update read");
	       this.readFromNBT(pkt.func_148857_g());
	   }

	   
	public void writeSyncableToNBT(NBTTagCompound tags)
	{
		tags.setInteger("power", this.power);
		
		tags.setBoolean("isCreative", this.isCreative);
		
		tags.setInteger("gravityForce", this.gravityForce);
		
		tags.setIntArray("gravityfieldRange", this.gravityfieldRange);
		
		tags.setInteger("maxPower", maxPower);
	}
	
	public void readSyncableFromNBT(NBTTagCompound tags)
	{
		this.power = tags.getInteger("power");
		
		this.isCreative = tags.getBoolean("isCreative");
		
		this.gravityForce = tags.getInteger("gravityForce");
		
		this.gravityfieldRange = tags.getIntArray("gravityfieldRange");
		
		this.maxPower = tags.getInteger("maxPower");
	}

	
	public void addToGravityFieldRange(ForgeDirection side, int value)
	{
		switch(side)
		{
			case DOWN:
				this.gravityfieldRange[0] += value;
				break;
			case UP:
				this.gravityfieldRange[1] += value;
				break;
			case NORTH:
				this.gravityfieldRange[2] += value;
				break;
			case SOUTH:
				this.gravityfieldRange[3] += value;
				break;
			case EAST:
				this.gravityfieldRange[4] += value;
				break;
			case WEST:
				this.gravityfieldRange[5] += value;
				break;
			default:
				break;
		}
		
		clampValues();
		
		updateGravField();
	}
	
	public void setGravityFieldRange(ForgeDirection side, int newValue)
	{
		switch(side)
		{
			case DOWN:
				this.gravityfieldRange[0] = newValue;
				break;
			case UP:
				this.gravityfieldRange[1] = newValue;
				break;
			case NORTH:
				this.gravityfieldRange[2] = newValue;
				break;
			case SOUTH:
				this.gravityfieldRange[3] = newValue;
				break;
			case EAST:
				this.gravityfieldRange[4] = newValue;
				break;
			case WEST:
				this.gravityfieldRange[5] = newValue;
				break;
			default:
				break;
		}
		
		clampValues();
		
		updateGravField();
		
	}
	
	public int getGravityFieldRange(ForgeDirection side)
	{
		switch(side)
		{
			case DOWN:
				return this.gravityfieldRange[0];
			case UP:
				return this.gravityfieldRange[1];
			case NORTH:
				return this.gravityfieldRange[2];
			case SOUTH:
				return this.gravityfieldRange[3];
			case EAST:
				return this.gravityfieldRange[4];
			case WEST:
				return this.gravityfieldRange[5];
			default:
				break;
		}
		
		return 0;
	}
	
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) 
	{
		return from != ForgeDirection.UP;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) 
	{
		this.isRecievingPower = false;
		if(from == ForgeDirection.UP)
		{
			return 0;
		}
		
		
		int change = 0;
		if(!simulate && power != this.getMaxEnergyStored(from))
		{
			change = Settings.GravGen_MaxRFTick;
			power += change = (power + change) <= this.getMaxEnergyStored(from)? change : (this.getMaxEnergyStored(from) - power);
			
			this.isRecievingPower = true;
		}
		return change;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) 
	{
		return power;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) 
	{
		return maxPower;
	}
	
	public int getGravityFieldCM()
	{
		return (getGravityFieldRange(ForgeDirection.UP) + getGravityFieldRange(ForgeDirection.DOWN)) * (getGravityFieldRange(ForgeDirection.NORTH) + getGravityFieldRange(ForgeDirection.SOUTH)) * (getGravityFieldRange(ForgeDirection.EAST) + getGravityFieldRange(ForgeDirection.WEST));
	}

	@Override
	public void setPowered(boolean isPowered) 
	{
		this.isPowered = isPowered;
	}

	@Override
	public boolean isPowered() 
	{
		return this.getBlockMetadata() == 1 ? true : false;
	}

	@Override
	public void setControl(ControlMode control) 
	{
		this.controlMode = control;
	}

	@Override
	public ControlMode getControl() 
	{
		return this.controlMode;
	}

	@Override
	public int getInfoEnergyPerTick() 
	{
		return this.machineUsage;
	}

	@Override
	public int getInfoMaxEnergyPerTick() 
	{
		return Settings.GravGen_MaxRFTick;
	}

	@Override
	public int getInfoEnergyStored() 
	{
		return this.power;
	}

	@Override
	public int getInfoMaxEnergyStored()
	{
		return this.getMaxEnergyStored(ForgeDirection.NORTH);
	}

	@Override
	public int getSizeInventory() 
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return slot >= 0 && slot < itemStacks.length? itemStacks[slot] : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if(slot >= 0 && slot < itemStacks.length && itemStacks[slot] != null)
		{
			if(itemStacks[slot].stackSize <= amount)
			{
				ItemStack item = itemStacks[slot];
				itemStacks[slot] = null;
				this.markDirty();
				return item;
			} else
			{
				ItemStack item = itemStacks[slot].splitStack(amount);
				
				if(itemStacks[slot].stackSize <= 0)
				{
					itemStacks[slot] = null;
				}
				this.markDirty();
				return item;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return slot >= 0 && slot < itemStacks.length? itemStacks[slot] : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if(slot >= 0 && slot < itemStacks.length)
		{
			itemStacks[slot] = stack;
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() 
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return true;
	}

	@Override
	public void openInventory() 
	{
	}

	@Override
	public void closeInventory() 
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		return true;
	}

	public String getTimeLeft() 
	{
		int rfpertick = this.isRecievingPower ? this.getInfoEnergyPerTick() <= this.getInfoMaxEnergyPerTick() ? this.getInfoEnergyPerTick() : Math.abs(this.getInfoMaxEnergyPerTick() - this.getInfoEnergyPerTick()) : this.getInfoEnergyPerTick();
		
		int totalSeconds = (int) Math.round(((this.getInfoEnergyStored()/rfpertick)/20));
	    final int MINUTES_IN_AN_HOUR = 60;
	    final int SECONDS_IN_A_MINUTE = 60;

	    int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
	    int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
	    int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
	    int hours = totalMinutes / MINUTES_IN_AN_HOUR;

	    String value = "";
	    
	    if(!(hours <= 0)) value += hours + "h ";
	    if(!(minutes <= 0)) value +=  minutes + "m ";
	    
	    return  value += seconds + "s";
	}

	@Override
	public boolean continueLoopingAudio() 
	{
		return true;
	}

	@Override
	public boolean shouldPlay()
	{
		return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 1 && Settings.GravGen_PlaySound ? true : false;
	}
	
	
	private void clampValues()
	{
		for(int i=0; i <= this.gravityfieldRange.length-1; i++)
		{
			if(this.gravityfieldRange[i] < 0) this.gravityfieldRange[i] = 0;  
		}
		
		
	}

	@Override
	public PacketMachines getGuiPacket() 
	{
		NBTTagCompound tags = new NBTTagCompound();
		this.writeSyncableToNBT(tags);
		return new PacketMachines(this, tags);
	}

	@Override
	public void handleGuiPacket(PacketMachines paramPacketMachines) 
	{
		this.readSyncableFromNBT(paramPacketMachines.tags);
	}

}

