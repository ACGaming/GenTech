package com.gendeathrow.gentech.world.chunk;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;

import com.gendeathrow.gentech.handlers.LockedBlock;

public class GTChunkData 
{
	
	public static List<LockedBlock> lockedBlock = new ArrayList<LockedBlock>(); 
	
	private Chunk chunk; 
	
	public GTChunkData(Chunk chunk)
	{
		this.chunk = chunk;
	}
	
	public void loadChunkData(NBTTagCompound data)
	{
		NBTTagCompound genTech = (NBTTagCompound) data.getTag("GenTech");
		NBTTagCompound levelData = (NBTTagCompound) data.getTag("levelData");
		
		byte[] lockedByte = levelData.getByteArray("locked");
		
	
	}
	
	public void saveChunkData(NBTTagCompound gentechData)
	{
		
	}
	
	public void loadChunk()
	{
		
	}
	
	public void UnloadChunk()
	{
		
	}

	public boolean addLockedBlock(LockedBlock locked, World worldObj)
	{
		this.lockedBlock.add(locked);
		return true;
	}
	
	public void loadLockedArray()
	{
		NibbleArray test = new NibbleArray(null, 0);
	}

}
