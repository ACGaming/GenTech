package com.gendeathrow.gentech.tileentity.helpers;

import com.gendeathrow.gentech.network.packet.PacketMachines;

public interface IGuiPacket 
{
	  public abstract PacketMachines getGuiPacket();

	  abstract void handleGuiPacket(PacketMachines paramPacketMachines);
}
