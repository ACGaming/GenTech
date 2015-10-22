package com.gendeathrow.gentech.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IShouldLoop 
{
	boolean continueLoopingAudio();
	
	@SideOnly(Side.CLIENT)
	public abstract boolean shouldPlay();
}
