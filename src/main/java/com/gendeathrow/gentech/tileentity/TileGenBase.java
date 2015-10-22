package com.gendeathrow.gentech.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import cofh.core.block.TileCoFHBase;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.core.network.PacketTileInfo;
import cofh.lib.util.helpers.ServerHelper;

import com.gendeathrow.gentech.core.GenTech;

import cpw.mods.fml.relauncher.Side;

public class TileGenBase extends TileCoFHBase
{

	public String tileName;
	
	public TileGenBase()
	  {
	    this.tileName = "";
	  }

	  public boolean setInvName(String paramString) 
	  {
	    if (paramString.isEmpty()) {
	      return false;
	    }
	    this.tileName = paramString;
	    return true;
	  }

	  protected boolean readPortableTagInternal(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound)
	  { 
	    return false;
	  }

	  protected boolean writePortableTagInternal(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound)
	  {
	    return false;
	  }

	  public int getInvSlotCount()
	  {
	    return 0;
	  }

	  public boolean hasGui()
	  {
	    return false;
	  }

	  public boolean openGui(EntityPlayer paramEntityPlayer)
	  {
	    if (hasGui()) 
	    {
	      paramEntityPlayer.openGui(GenTech.instance, 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	      return true;
	    }
	    return false;
	  }

	  public void sendGuiNetworkData(Container paramContainer, ICrafting paramICrafting)
	  {
	    if ((paramICrafting instanceof EntityPlayer)) 
	    {
	      PacketCoFHBase localPacketCoFHBase = getGuiPacket();
	      if (localPacketCoFHBase != null) PacketHandler.sendTo(localPacketCoFHBase, (EntityPlayer)paramICrafting);
	    }
	  }

	  public void readFromNBT(NBTTagCompound paramNBTTagCompound)
	  {
	    super.readFromNBT(paramNBTTagCompound);

	    if (paramNBTTagCompound.hasKey("Name"))
	      this.tileName = paramNBTTagCompound.getString("Name");
	  }

	  public void writeToNBT(NBTTagCompound paramNBTTagCompound)
	  {
	    super.writeToNBT(paramNBTTagCompound);
	    paramNBTTagCompound.setString("Version", "1.7.10R4.0.3B1");

	    if (!this.tileName.isEmpty())
	      paramNBTTagCompound.setString("Name", this.tileName);
	  }

	  public PacketCoFHBase getPacket()
	  {
	    PacketCoFHBase localPacketCoFHBase = super.getPacket();
	    localPacketCoFHBase.addString(this.tileName);
	    return localPacketCoFHBase;
	  }

	  public PacketCoFHBase getGuiPacket()
	  {
	    PacketTileInfo localPacketTileInfo = PacketTileInfo.newPacket(this);
//	    localPacketTileInfo.addByte(TEProps.PacketID.GUI.ordinal());
	    return localPacketTileInfo;
	  }

	  public PacketCoFHBase getFluidPacket()
	  {
	    PacketTileInfo localPacketTileInfo = PacketTileInfo.newPacket(this);
//	    localPacketTileInfo.addByte(TEProps.PacketID.FLUID.ordinal());
	    return localPacketTileInfo;
	  }

	  public PacketCoFHBase getModePacket()
	  {
	    PacketTileInfo localPacketTileInfo = PacketTileInfo.newPacket(this);
//	    localPacketTileInfo.addByte(TEProps.PacketID.MODE.ordinal());
	    return localPacketTileInfo;
	  }

	  protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase)
	  {
	  }

	  protected void handleFluidPacket(PacketCoFHBase paramPacketCoFHBase)
	  {
	  }

	  protected void handleModePacket(PacketCoFHBase paramPacketCoFHBase)
	  {
	    markChunkDirty();
	  }

	  public void sendFluidPacket()
	  {
	    PacketHandler.sendToDimension(getFluidPacket(), this.worldObj.provider.dimensionId);
	  }

	  public void sendModePacket()
	  {
	    if (ServerHelper.isClientWorld(this.worldObj))
	      PacketHandler.sendToServer(getModePacket());
	  }

	  public void handleTilePacket(PacketCoFHBase paramPacketCoFHBase, boolean paramBoolean)
	  {
	    if (ServerHelper.isClientWorld(this.worldObj))
	      this.tileName = paramPacketCoFHBase.getString();
	    else
	      paramPacketCoFHBase.getString();
	  }

	  public void handleTileInfoPacket(PacketCoFHBase paramPacketCoFHBase, boolean paramBoolean, EntityPlayer paramEntityPlayer)
	  {
//	    switch (1.$SwitchMap$cofh$thermalexpansion$core$TEProps$PacketID[TEProps.PacketID.values()[paramPacketCoFHBase.getByte()].ordinal()]) {
//	    case 1:
//	      handleGuiPacket(paramPacketCoFHBase);
//	      return;
//	    case 2:
//	      handleFluidPacket(paramPacketCoFHBase);
//	      return;
//	    case 3:
//	      handleModePacket(paramPacketCoFHBase);
//	      return;
//	    }
	  }

	  public String getDataType()
	  {
	    return getName();
	  }

	  public void readPortableData(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound)
	  {
	    if (!canPlayerAccess(paramEntityPlayer)) {
	      return;
	    }
	    if (readPortableTagInternal(paramEntityPlayer, paramNBTTagCompound)) {
	      markDirty();
	      sendUpdatePacket(Side.CLIENT);
	    }
	  }

	  public void writePortableData(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound)
	  {
	    if (!canPlayerAccess(paramEntityPlayer)) {
	      return; } if (writePortableTagInternal(paramEntityPlayer, paramNBTTagCompound)); } 
	  public static class SideConfig { public int numConfig;
	    public int[][] slotGroups;
	    public boolean[] allowInsertionSide;
	    public boolean[] allowExtractionSide;
	    public boolean[] allowInsertionSlot;
	    public boolean[] allowExtractionSlot;
	    public int[] sideTex;
	    public byte[] defaultSides; } 
	  public static class EnergyConfig 
	  {
		  public int minPower = 8;
		  public int maxPower = 80;
		  public int maxEnergy = 40000;
		  public int minPowerLevel = 1 * this.maxEnergy / 10;
		  public int maxPowerLevel = 9 * this.maxEnergy / 10;
		  public int energyRamp = this.maxPowerLevel / this.maxPower;

	    public EnergyConfig()
	    {
	    }

	    public EnergyConfig(EnergyConfig paramEnergyConfig)
	    {
	      this.minPower = paramEnergyConfig.minPower;
	      this.maxPower = paramEnergyConfig.maxPower;
	      this.maxEnergy = paramEnergyConfig.maxEnergy;
	      this.minPowerLevel = paramEnergyConfig.minPowerLevel;
	      this.maxPowerLevel = paramEnergyConfig.maxPowerLevel;
	      this.energyRamp = paramEnergyConfig.energyRamp;
	    }

	    public EnergyConfig copy()
	    {
	      return new EnergyConfig(this);
	    }

	    public boolean setParams(int paramInt1, int paramInt2, int paramInt3)
	    {
	      this.minPower = paramInt1;
	      this.maxPower = paramInt2;
	      this.maxEnergy = paramInt3;
	      this.maxPowerLevel = (paramInt3 * 8 / 10);
	      this.energyRamp = (paramInt2 > 0 ? this.maxPowerLevel / paramInt2 : 0);
	      this.minPowerLevel = (paramInt1 * this.energyRamp);

	      return true;
	    }

	    public boolean setParamsPower(int paramInt)
	    {
	      return setParams(paramInt / 4, paramInt, paramInt * 1200);
	    }

	    public boolean setParamsPower(int paramInt1, int paramInt2)
	    {
	      return setParams(paramInt1 / 4, paramInt1, paramInt1 * 1200 * paramInt2);
	    }

	    public boolean setParamsEnergy(int paramInt)
	    {
	      return setParams(paramInt / 4800, paramInt / 1200, paramInt);
	    }

	    public boolean setParamsEnergy(int paramInt1, int paramInt2)
	    {
	      paramInt1 *= paramInt2;
	      return setParams(paramInt1 / 4800, paramInt1 / 1200, paramInt1);
	    }

	    public boolean setParamsDefault(int paramInt)
	    {
	      this.maxPower = paramInt;
	      this.minPower = (paramInt / 10);
	      this.maxEnergy = (paramInt * 500);
	      this.minPowerLevel = (1 * this.maxEnergy / 10);
	      this.maxPowerLevel = (9 * this.maxEnergy / 10);
	      this.energyRamp = (this.maxPowerLevel / paramInt);

	      return true;
	    }
	  }
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
}
