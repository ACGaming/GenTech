package com.gendeathrow.gentech.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.gendeathrow.gentech.core.GenTech;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemGlueGun extends Item
{

	public static Item glueGun;
	public static String id = "glueGun";
	
	public ItemGlueGun()
	    {
	        this.setCreativeTab(GenTech.genTechTab);
	        this.setUnlocalizedName(GenTech.MODID +"_"+ id);
		    this.setTextureName(GenTech.MODID +":"+ id);   
	    }
	   
	   public static void init()
	   {
		  // glueGun = new ItemGlueGun();
		  // GameRegistry.registerItem(glueGun, id);
	   }

	    /**
	     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	     */
	    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World worldObj, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	    {
	        Block block = worldObj.getBlock(x, y, z);
	        int i1 = worldObj.getBlockMetadata(x, y, z);

//	        if (p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && block == Blocks.end_portal_frame && !BlockEndPortalFrame.isEnderEyeInserted(i1))
//	        {
	            if (worldObj.isRemote)
	            {
	                return true;
	            }
	            else
	            {
	            	System.out.println("Make this unbreakable");
	            	//block.setBlockUnbreakable();
	            	
	                return true;
	            }
//	        }
//	        else
//	        {
//	            return false;
//	        }
	    }

	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	    {
	        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, false);

	        if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && p_77659_2_.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.end_portal_frame)
	        {
	            return p_77659_1_;
	        }
	        else
	        {
	            if (!p_77659_2_.isRemote)
	            {

	            }

	            return p_77659_1_;
	        }
	    }
}
