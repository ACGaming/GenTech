package com.gendeathrow.gentech.world.chunk;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
public class GTChunkHandler 
{
	
	public static final HashMap<Chunk, GTChunkData> loadedChunks = new HashMap<Chunk, GTChunkData>();


	@SubscribeEvent
	public void loading(ChunkDataEvent.Load event)
	{
		// read your stuff out of the event.data NBT and put into event.getChunk()
		NBTTagCompound data = event.getData();
		
		Chunk chunk = event.getChunk();
		GTChunkData GTChunk = new GTChunkData(chunk);
		
		GTChunk.loadChunkData(data);
		
		loadedChunks.put(chunk, GTChunk);
		
	//	System.out.println("Chunk Loaded Data:"+ event.getChunk().getChunkCoordIntPair().chunkXPos + " "+ event.getChunk().getChunkCoordIntPair().chunkXPos);

	   	return;
	}	

	@SubscribeEvent
	public void saving(ChunkDataEvent.Save event)
	{
		// 	read your stuff out of the event.getChunk() and put into event.data NBT
		NBTTagCompound data = event.getData();
		NBTTagCompound gentechData = new NBTTagCompound();
		NBTTagCompound levelData = new NBTTagCompound();
			  
			  
		Chunk chunk = event.getChunk();
		chunk.getChunkCoordIntPair();
		
		GTChunkData GTChunk = new GTChunkData(chunk);
		
		GTChunk.loadChunkData(gentechData);
		
		gentechData.setTag("level", levelData);
		data.setTag("GenTech", gentechData);
	}
		  
	@SubscribeEvent
	public void loadChunk(ChunkEvent.Load event)
	{

	}
	
	@SubscribeEvent
	public void UnloadChunk(ChunkEvent.Unload event)
	{
		//System.out.println("Chunk UnLoad:"+ event.getChunk().getChunkCoordIntPair().chunkXPos + " "+ event.getChunk().getChunkCoordIntPair().chunkXPos);
		
		loadedChunks.remove(event.getChunk());
	}
	
	public static GTChunkData getGTChunk( World worldObj, int x, int z)
	{
		return(getGTChunkbyChunk(worldObj.getChunkFromBlockCoords(x, z)));
	}
	
	public static GTChunkData getGTChunkbyChunk(Chunk chunk)
	{
		return loadedChunks.get(chunk);
	}
	
}
