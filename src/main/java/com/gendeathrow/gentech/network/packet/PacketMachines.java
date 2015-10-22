package com.gendeathrow.gentech.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.gendeathrow.gentech.tileentity.helpers.IGuiPacket;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketMachines implements IMessage 
{
	public TileEntity tile;
	public NBTTagCompound tags;
	
	public PacketMachines(){}
	
	public PacketMachines(TileEntity tile, NBTTagCompound tags)
	{
		this.tags = tags;
		this.tile = tile;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.tags =	ByteBufUtils.readTag(buf);
		int[] cords = tags.getIntArray("cords");
		//System.out.println("read buffer"+ cords[0]+"'"+ cords[1]+"'"+ cords[2]);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{

		this.tags.setIntArray("cords", new int[]{this.tile.xCoord, this.tile.yCoord, this.tile.zCoord});
		
		ByteBufUtils.writeTag(buf, this.tags);
		
		//System.out.println("set buffer" +this.tile.xCoord +","+ this.tile.yCoord +","+ this.tile.zCoord);
	}
	
	
	public static class HandlerServer implements IMessageHandler<PacketMachines,IMessage>
	{
		@Override
		public IMessage onMessage(PacketMachines packet, MessageContext ctx)
		{

			return null;
		}
		
	}
	
	public static class HandlerClient implements IMessageHandler<PacketMachines,IMessage>
	{
		@Override
		public IMessage onMessage(PacketMachines packet, MessageContext ctx)
		{
			int[] cords = packet.tags.getIntArray("cords");
			//PacketMachines
			//System.out.println("packet cords"+ cords[0]+"'"+ cords[1]+"'"+ cords[2]);
			packet.tile = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(cords[0], cords[1], cords[2]);
		
			
			if(packet.tile == null) return null;
			//else System.out.println("tile is empty");
			if(packet.tile instanceof IGuiPacket)
			{
				((IGuiPacket)packet.tile).handleGuiPacket(packet);
			}
			return packet;
		}
	}
		

}
