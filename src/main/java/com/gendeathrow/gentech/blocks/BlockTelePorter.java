package com.gendeathrow.gentech.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gendeathrow.gentech.tileentity.TileTelePorter;

public class BlockTelePorter extends BlockGenBase
{

	public BlockTelePorter(Material material) 
	{
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileTelePorter();
	}
}
