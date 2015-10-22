package com.gendeathrow.gentech.handlers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class LockedBlock
{
	private boolean isLocked;
	public Block block;
	public int x;
	public int y;
	public int z;
	
	public LockedBlock(){}
	
	public LockedBlock(int x, int y, int z, Block block)
	{
		this.block = block;
		isLocked = true;
		this.x = x;
		this.y = y;
		this.z = z;
		
	}

	public LockedBlock setLock()
	{
		this.isLocked = true;
		return this;
	}
	
	public Block getBlock()
	{
		return this.block;
	}
}
