package com.gendeathrow.gentech.network.packet;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;

import com.gendeathrow.gentech.common.entityplayer.GTPlayerData;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketGenTech implements IMessage 
{
	int id;
	int gravityForce;
	boolean infield;
	
	public PacketGenTech(){}

	
	public PacketGenTech(int id, boolean infield)
	{
		this.id = id;
		this.infield = infield;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.id = ByteBufUtils.readVarInt(buf, 1);
		this.infield = ByteBufUtils.readVarShort(buf) == 1 ? true : false;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeVarInt(buf, id, 1);
		ByteBufUtils.writeVarShort(buf, this.infield == true ? 1 : 0);
	}

	
	public static class HandlerServer implements IMessageHandler<PacketGenTech,IMessage>
	{
		@Override
		public IMessage onMessage(PacketGenTech packet, MessageContext ctx)
		{

			return null;
		}
		
	}
	
	public static class HandlerClient implements IMessageHandler<PacketGenTech,IMessage>
	{
		@Override
		public IMessage onMessage(PacketGenTech packet, MessageContext ctx)
		{
			
			switch(packet.id)
			{
				case 1: 
					UpdateHud(packet);
					break;
			}
			
	
			return packet;
		}
		
		@SideOnly(Side.CLIENT)
		private void UpdateHud(PacketGenTech packet)
		{
			GTPlayerData player = GTPlayerData.get(Minecraft.getMinecraft().thePlayer);
			
			player.inGravField = packet.infield;
			
		}
		
	}
	
	
}
