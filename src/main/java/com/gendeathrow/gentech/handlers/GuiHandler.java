package com.gendeathrow.gentech.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gendeathrow.gentech.client.gui.GuiGravityGen;
import com.gendeathrow.gentech.inventory.ContainerGravityGenerator;
import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) 
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(ID == 0 && tile instanceof TileEntityGravityGenerator)
		{
			return new ContainerGravityGenerator(player, (TileEntityGravityGenerator)tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(ID == 0 && tile instanceof TileEntityGravityGenerator)
		{
			return new GuiGravityGen(player, (TileEntityGravityGenerator)tile);
		}
		return null;
	}

}
