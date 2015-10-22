package com.gendeathrow.gentech.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cofh.api.tileentity.IRedstoneControl;
import cofh.api.tileentity.ISecurable;
import cofh.core.block.BlockCoFHBase;
import cofh.core.block.TileCoFHBase;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.RedstoneControlHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.lib.util.helpers.ServerHelper;

import com.gendeathrow.gentech.inventory.TileGenInventory;
import com.gendeathrow.gentech.tileentity.TileGenBase;
import com.gendeathrow.gentech.utils.Utils;

import cpw.mods.fml.common.eventhandler.Event;

public class BlockGenBase extends BlockCoFHBase
{

	private boolean basicGui;

	public BlockGenBase(Material material) 
	{
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return null;
	}

	public boolean initialize() 
	{
		return false;
	}

	public boolean postInit() 
	{
		return false;
	}

	public void onBlockPlacedBy(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack)
	{
		TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
		
	    if ((localTileEntity instanceof TileGenBase)) 
	    {
	      ((TileGenBase)localTileEntity).setInvName(ItemHelper.getNameFromItemStack(paramItemStack));
	    }
	    super.onBlockPlacedBy(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLivingBase, paramItemStack);
	}

	  

	public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
	{
	    PlayerInteractEvent localPlayerInteractEvent = new PlayerInteractEvent(paramEntityPlayer, PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, paramInt1, paramInt2, paramInt3, paramInt4, paramWorld);
	    if ((MinecraftForge.EVENT_BUS.post(localPlayerInteractEvent)) || (localPlayerInteractEvent.getResult() == Event.Result.DENY) || (localPlayerInteractEvent.useBlock == Event.Result.DENY)) {
	      return false;
	    }
	    if (paramEntityPlayer.isSneaking()) 
	    {
	      if (Utils.isHoldingUsableWrench(paramEntityPlayer, paramInt1, paramInt2, paramInt3)) 
	      {
	        if ((ServerHelper.isServerWorld(paramWorld)) && (canDismantle(paramEntityPlayer, paramWorld, paramInt1, paramInt2, paramInt3))) 
	        {
	          dismantleBlock(paramEntityPlayer, paramWorld, paramInt1, paramInt2, paramInt3, false);
	        }
	        Utils.usedWrench(paramEntityPlayer, paramInt1, paramInt2, paramInt3);
	        return true;
	      }
	      return false;
	    }
	    TileGenBase localTileTEBase = (TileGenBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);

	    if (localTileTEBase == null) 
	    {
	      return false;
	    }
	    if (Utils.isHoldingUsableWrench(paramEntityPlayer, paramInt1, paramInt2, paramInt3)) 
	    {
	      if (ServerHelper.isServerWorld(paramWorld)) 
	      {
	        localTileTEBase.onWrench(paramEntityPlayer, paramInt4);
	      }
	      Utils.usedWrench(paramEntityPlayer, paramInt1, paramInt2, paramInt3);
	      return true;
	    }
	    if ((this.basicGui) && (ServerHelper.isServerWorld(paramWorld))) 
	    {
	      return localTileTEBase.openGui(paramEntityPlayer);
	    }
	    return this.basicGui;
	}



	public NBTTagCompound getItemStackTag(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
	{
	    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);

	    NBTTagCompound localNBTTagCompound = null;

	    if (((localTileEntity instanceof TileGenBase)) && (!((TileGenBase)localTileEntity).tileName.isEmpty())) {
	      localNBTTagCompound = ItemHelper.setItemStackTagName(localNBTTagCompound, ((TileGenBase)localTileEntity).tileName);
	    }
	    if (((localTileEntity instanceof TileGenInventory)) && (((TileGenInventory)localTileEntity).isSecured())) {
	      localNBTTagCompound = SecurityHelper.setItemStackTagSecure(localNBTTagCompound, (ISecurable)localTileEntity);
	    }
	    if ((localTileEntity instanceof IRedstoneControl)) {
	      localNBTTagCompound = RedstoneControlHelper.setItemStackTagRS(localNBTTagCompound, (IRedstoneControl)localTileEntity);
	    }
	    return localNBTTagCompound;
	}
	  
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound, World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
	{
	    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
	    int i = paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3);

	    ItemStack localItemStack = new ItemStack(this, 1, i);

	    if (paramNBTTagCompound != null) 
	    {
	      localItemStack.setTagCompound(paramNBTTagCompound);
	    }
	    if (!paramBoolean2) 
	    {
	      if ((localTileEntity instanceof TileCoFHBase)) 
	      {
	        ((TileCoFHBase)localTileEntity).blockDismantled();
	      }
	      paramWorld.setBlockToAir(paramInt1, paramInt2, paramInt3);

	      if (!paramBoolean1) 
	      {
	        float f = 0.3F;
	        double d1 = paramWorld.rand.nextFloat() * f + (1.0F - f) * 0.5D;
	        double d2 = paramWorld.rand.nextFloat() * f + (1.0F - f) * 0.5D;
	        double d3 = paramWorld.rand.nextFloat() * f + (1.0F - f) * 0.5D;
	        EntityItem localEntityItem = new EntityItem(paramWorld, paramInt1 + d1, paramInt2 + d2, paramInt3 + d3, localItemStack);
	        localEntityItem.delayBeforeCanPickup = 10;
	        if (((localTileEntity instanceof ISecurable)) && (!((ISecurable)localTileEntity).getAccess().isPublic())) 
	        {
	          localEntityItem.func_145797_a(paramEntityPlayer.getCommandSenderName());
	        }

	        paramWorld.spawnEntityInWorld(localEntityItem);

	        if (paramEntityPlayer != null) 
	        {
	          CoreUtils.dismantleLog(paramEntityPlayer.getCommandSenderName(), this, i, paramInt1, paramInt2, paramInt3);
	        }
	      }
	    }
	    ArrayList localArrayList = new ArrayList();
	    localArrayList.add(localItemStack);
	    return localArrayList;
	}

}
