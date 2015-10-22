package com.gendeathrow.gentech.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cofh.api.block.IDismantleable;
import cofh.api.tileentity.ISecurable;
import cofh.core.block.TileCoFHBase;
import cofh.core.util.CoreUtils;

import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;
import com.gendeathrow.gentech.utils.Utils;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGravityGenerator extends BlockContainer implements IDismantleable{

    @SideOnly(Side.CLIENT)
	private IIcon topIcon_off, bottomIcon, topIcon_on, creativeIcon_side, sideIcon_on;
	
	public static Block gravityGen;
    
	public static String id = "gravity_generator";
	private static String id2 = "gravity_generator_creative";
	
	private boolean isCreative = false;

	public BlockGravityGenerator(Material p_i45386_1_) 
	{
		super(p_i45386_1_);
		this.setBlockName(GenTech.MODID +"."+ this.id);
		this.setBlockTextureName(GenTech.MODID + ":" + "gravity_generator");
		this.setCreativeTab(GenTech.genTechTab);
		this.setHardness(5f);
		this.setResistance(20f);
		this.setHarvestLevel("ItemPickaxe", 1);
		
	}
	
	public static void init()
	{
    	gravityGen = new BlockGravityGenerator(Material.iron);
    	
    	GameRegistry.registerBlock(gravityGen, BlockGravityGenerator.id);
    	GameRegistry.registerTileEntity(TileEntityGravityGenerator.class, "tileEntityGravGen");

    	BlockGravityGenerator creative = (BlockGravityGenerator) new BlockGravityGenerator(Material.iron).setCreative(true).setCreativeTab(GenTech.genTechTab).setBlockUnbreakable().setBlockName(GenTech.MODID +"."+ id2).setHardness(10F);
    	GameRegistry.registerBlock(creative, BlockGravityGenerator.id2);
    	
        GameRegistry.registerCustomItemStack("gravity_generator", new ItemStack(gravityGen, 1));
    	GameRegistry.registerCustomItemStack("gravity_generator", new ItemStack(creative, 1));
    	
   	 	//Renderer
   	 	//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGravityGenerator.class, new RenderGravityGenerator());
    	
	}
	
	public BlockGravityGenerator setCreative(boolean value)
	{
		this.isCreative = true;
		return this;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		TileEntity tile = null;
		if(isCreative) return new TileEntityGravityGenerator().setCreative(true);
		return new TileEntityGravityGenerator();
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_" + "side");
        this.sideIcon_on = p_149651_1_.registerIcon(this.getTextureName() + "_" + "sideA");
        this.creativeIcon_side = p_149651_1_.registerIcon(this.getTextureName() + "_" + "sideC");
        this.topIcon_on  = p_149651_1_.registerIcon(this.getTextureName() + "_" + "topA");
        this.topIcon_off = p_149651_1_.registerIcon(this.getTextureName() + "_" + "top");
        this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_" + "bottom");
    }

	
//	public int getRenderType()
//	{
//		return 1;
//	}
//	
//	public boolean isOpaqueCube()
//	{
//		return false;
//	}
//
//	public boolean renderAsNormalBlock()
//	{
//		return false;
//	}
	
    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {

		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("power") && tile instanceof TileEntityGravityGenerator)
		{
			((TileEntityGravityGenerator)tile).readSyncableFromNBT(stack.stackTagCompound);
		}
		super.onBlockPlacedBy(world, x, y, z, living, stack);
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		
		if (player.isSneaking()) 
		{
		      if (Utils.isHoldingUsableWrench(player, x, y, z)) 
		      {
		        if ((!world.isRemote) && (canDismantle(player, world, x, y, z))) 
		        {
		          dismantleBlock(player, world, x, y, z, true);
		        }
		        Utils.usedWrench(player, x, y, z);
		        return true;
		      }
				player.openGui(GenTech.instance, 0, world, x, y, z);
		      return false;
		}
		
		player.openGui(GenTech.instance, 0, world, x, y, z);
		
        return true;
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_)
	{
		TileEntityGravityGenerator generator = (TileEntityGravityGenerator)world.getTileEntity(x, y, z);

		if(generator != null) generator.deactivateGenerator();
		
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		switch(side)
		{

			case 0: 
				//bottom++;
				return this.bottomIcon;

			case 1: 
				//top++;
				if(meta == 1) return this.topIcon_on;
				return this.topIcon_off;
				
			default:
				if(this.isCreative) return this.creativeIcon_side; 
				else if(meta == 1) return this.sideIcon_on;
				return this.blockIcon;
//			case 2:side1++;	case 3:side2++;	case 4:side3++;	case 5:side4++; 
		}
    }

    
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {

		return dismantleBlock(null, getItemStackTag(world, x, y, z), world, x, y, z, false, true);
	}
	
    public NBTTagCompound getItemStackTag(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
    {
      NBTTagCompound localNBTTagCompound = null;
      TileEntityGravityGenerator localTileStrongbox = (TileEntityGravityGenerator)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);

      if (localTileStrongbox != null) 
      {
        if (localNBTTagCompound == null) 
        {
          localNBTTagCompound = new NBTTagCompound();
        }

        localTileStrongbox.writeSyncableToNBT(localNBTTagCompound);
      }
      return localNBTTagCompound;
    }
    
//    public NBTTagCompound lgetItemStackTag(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
//    {
//      TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
//
//      NBTTagCompound localNBTTagCompound = null;
//
//      if (((localTileEntity instanceof TileGenBase)) && (!((TileGenBase)localTileEntity).tileName.isEmpty())) 
//      {
//        localNBTTagCompound = ItemHelper.setItemStackTagName(localNBTTagCompound, ((TileGenBase)localTileEntity).tileName);
//      }
//      if ((localTileEntity instanceof IRedstoneControl)) 
//      {
//        localNBTTagCompound = RedstoneControlHelper.setItemStackTagRS(localNBTTagCompound, (IRedstoneControl)localTileEntity);
//      }
//      return localNBTTagCompound;
//    }
    
    @Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer paramEntityPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
	{
	    NBTTagCompound localNBTTagCompound = getItemStackTag(paramWorld, paramInt1, paramInt2, paramInt3);

	    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
	    
//	    if(localTileEntity instanceof IInventory)
//	    {
//	    	((TileEntityGravityGenerator)localTileEntity).getSizeInventory();
//	    }
	    
//	    if ((localTileEntity instanceof TileGenInventory)) 
//	    {
//	      ((TileGenInventory)localTileEntity)..inventory = new ItemStack[((TileGenInventory)localTileEntity).inventory.length];
//	    }
	    return dismantleBlock(paramEntityPlayer, localNBTTagCompound, paramWorld, paramInt1, paramInt2, paramInt3, paramBoolean, false);
	 }
	
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer paramEntityPlayer, NBTTagCompound paramNBTTagCompound, World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
	  {
	    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
	    int i = paramWorld.getBlockMetadata(paramInt1, paramInt2, paramInt3);

	    ItemStack localItemStack = new ItemStack(this, 1, i);

	      System.out.println("dismantle");
	      
	    if (paramNBTTagCompound != null) 
	    {
	      localItemStack.setTagCompound(paramNBTTagCompound);
	      System.out.println("set tags");
	    }
	    if (!paramBoolean2) 
	    {
	      if ((localTileEntity instanceof TileCoFHBase)) 
	      {
	        ((TileCoFHBase)localTileEntity).blockDismantled();
	      }
	      
	      paramWorld.setBlockToAir(paramInt1, paramInt2, paramInt3);
	      System.out.println("stuff");
	      if (!paramBoolean1) 
	      {
	    	  System.out.println("breaks");
	    	  
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

	@Override
	public boolean canDismantle(EntityPlayer player, World world, int x, int y,	int z) 
	{
		
		return isCreative ? false : true;
	}

}
