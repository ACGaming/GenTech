package com.gendeathrow.gentech.core;

import java.util.ArrayList;
import java.util.List;

import com.gendeathrow.gentech.blocks.BlockGravityGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GenTechTab extends CreativeTabs
{
	ArrayList<ItemStack> rawStacks = new ArrayList<ItemStack>();
	
	public GenTechTab(String lable) 
	{
		super(lable);
	}

	@Override
	public Item getTabIconItem() 
	{
		return Item.getItemFromBlock(BlockGravityGenerator.gravityGen);
	}
	
	public void addRawStack(ItemStack stack)
	{
		rawStacks.add(stack);
	}
	
    /**
     * only shows items which have tabToDisplayOn == this
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List list)
    {
        super.displayAllReleventItems(list);
        
        list.addAll(rawStacks);
    }
    
}
