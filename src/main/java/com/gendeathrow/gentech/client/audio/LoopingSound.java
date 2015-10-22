package com.gendeathrow.gentech.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.core.Settings;
import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;

public class LoopingSound extends PositionedSound implements ITickableSound 
{

	TileEntityGravityGenerator parent;
	private int x,y,z;
	private boolean shouldBePlaying = true;
	private float volumeD = 1F;
	
	public LoopingSound(ResourceLocation resource, TileEntityGravityGenerator parent,float volume)
	{
		this(resource,parent);
		this.volume = volume;
		this.volumeD = volume;
	}
	
	public LoopingSound(ResourceLocation resource, TileEntityGravityGenerator parent)
	{
		super(resource);
		this.parent = parent;
		repeat = true;
		xPosF = parent.xCoord+0.5f;
		yPosF = parent.yCoord+0.5f;
		zPosF = parent.zCoord+0.5f;
		x = parent.xCoord;
		y = parent.yCoord;
		z = parent.zCoord;
	}
	
	@Override
	public void update() 
	{
		if(this.parent != null)
		{
			if(parent.shouldPlay())
			{
				this.volume = this.volumeD;
			}
			else
			{
				this.volume = 0;
			}
			
		}
	
	}

	@Override
	public boolean isDonePlaying() 
	{
		if (shouldBePlaying)
		{
			// we should only be playing if parent still exists and says we are playing - assume not
			shouldBePlaying = false;
			// should never be here on the server, but just in case
			World world = Minecraft.getMinecraft().theWorld;
			if (world.isRemote)
			{
				TileEntity parent = world.getTileEntity(x, y, z);
				if (parent != null)
				{
					if (parent instanceof IShouldLoop)
					{
						IShouldLoop iShouldLoop = (IShouldLoop)parent;
						shouldBePlaying = iShouldLoop.continueLoopingAudio();
					}
				}
			}
		}
		return !shouldBePlaying;
	}

}
