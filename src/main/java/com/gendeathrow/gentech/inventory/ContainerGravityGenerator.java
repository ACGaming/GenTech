package com.gendeathrow.gentech.inventory;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.tileentity.IRedstoneControl.ControlMode;

import com.gendeathrow.gentech.core.GenTech;
import com.gendeathrow.gentech.core.Settings;
import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGravityGenerator extends Container
{
	
	TileEntityGravityGenerator generator;

	public ContainerGravityGenerator(EntityPlayer player, TileEntityGravityGenerator tile)
	{
		
		this.generator = tile;
		
		this.addSlotToContainer(new Slot(tile, 0, 42, 33)
		{
		    /**
		     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
		     */
			@Override
		    public boolean isItemValid(ItemStack stack)
		    {
		        return stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem;
		    }
		});
	
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		if(this.generator.isCreative() && player.capabilities.isCreativeMode) return true;
		else if(this.generator.isCreative()) 
		{  player.addChatComponentMessage(new ChatComponentText("You dont have access to this block. Creative Only!")); return false; }
		else return true;
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		//System.out.println(this.generator.getInfoEnergyStored());
		
		for (int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)this.crafters.get(i);
			
			//icrafting.sendProgressBarUpdate(this, 0, this.generator.getInfoEnergyStored());

			icrafting.sendProgressBarUpdate(this, 1, this.generator.machineUsage);
			//icrafting.sendProgressBarUpdate(this, 2, this.generator.isPowered ? 1 : 0);
			
			icrafting.sendProgressBarUpdate(this, 3, this.generator.getGravityFieldRange(ForgeDirection.UP));
			icrafting.sendProgressBarUpdate(this, 4, this.generator.getGravityFieldRange(ForgeDirection.DOWN));
			icrafting.sendProgressBarUpdate(this, 5, this.generator.getGravityFieldRange(ForgeDirection.EAST));
			icrafting.sendProgressBarUpdate(this, 6, this.generator.getGravityFieldRange(ForgeDirection.WEST));
			icrafting.sendProgressBarUpdate(this, 7, this.generator.getGravityFieldRange(ForgeDirection.NORTH));
			icrafting.sendProgressBarUpdate(this, 8, this.generator.getGravityFieldRange(ForgeDirection.SOUTH));
			
			icrafting.sendProgressBarUpdate(this, 9, (int) this.generator.gravityForce);
			
			//icrafting.sendProgressBarUpdate(this, 10, this.generator.getControl() == ControlMode.DISABLED ? 1 : this.generator.getControl() == ControlMode.HIGH ? 2 : 3);
			
			if(icrafting instanceof EntityPlayerMP)
			{
				GenTech.instance.network.sendTo(this.generator.getGuiPacket(), (EntityPlayerMP)icrafting);
			}
			
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int number)
	{
		//System.out.println("id:"+id +" num:"+ number);
		if(id == 0) this.generator.power = number; 
		else if(id == 1) this.generator.machineUsage = number;
		//else if(id == 2) this.generator.isPowered = number == 1 ? true : false;
		else if(id == 3) this.generator.setGravityFieldRange(ForgeDirection.UP, number);
		else if(id == 4) this.generator.setGravityFieldRange(ForgeDirection.DOWN, number);
		else if(id == 5) this.generator.setGravityFieldRange(ForgeDirection.EAST, number);
		else if(id == 6) this.generator.setGravityFieldRange(ForgeDirection.WEST, number);
		else if(id == 7) this.generator.setGravityFieldRange(ForgeDirection.NORTH, number);
		else if(id == 8) this.generator.setGravityFieldRange(ForgeDirection.SOUTH, number);
		else if(id == 9) this.generator.gravityForce = number;
		//else if(id == 10) this.generator.setControl(number == 1? ControlMode.DISABLED : number == 2 ? ControlMode.HIGH : ControlMode.LOW);
		

	}
	
	@Override
	public boolean enchantItem(EntityPlayer player, int action)
	{
		switch(action)
		{
			case 1:
				this.generator.addToGravityFieldRange(ForgeDirection.UP, 1);
				break;
			case 2:
				this.generator.addToGravityFieldRange(ForgeDirection.UP, -1);
				break;
			case 3:
				this.generator.addToGravityFieldRange(ForgeDirection.DOWN, 1);
				break;
			case 4:
				this.generator.addToGravityFieldRange(ForgeDirection.DOWN, -1);
				break;
			case 5:
				this.generator.addToGravityFieldRange(ForgeDirection.EAST, 1);
				break;
			case 6:
				this.generator.addToGravityFieldRange(ForgeDirection.EAST, -1);
				break;		
			case 7:
				this.generator.addToGravityFieldRange(ForgeDirection.WEST, 1);
				break;
			case 8:
				this.generator.addToGravityFieldRange(ForgeDirection.WEST, -1);
				break;	
			case 9:
				this.generator.addToGravityFieldRange(ForgeDirection.NORTH, 1);
				break;
			case 10:
				this.generator.addToGravityFieldRange(ForgeDirection.NORTH, -1);
				break;
			case 11:
				this.generator.addToGravityFieldRange(ForgeDirection.SOUTH, 1);
				break;
			case 12:
				this.generator.addToGravityFieldRange(ForgeDirection.SOUTH, -1);
				break;	
			case 13:
				this.generator.gravityForce += 10;
				//if(this.generator.gravityForce > 100) this.generator.gravityForce = 100;
				break;
			case 14:
				this.generator.gravityForce -= 10;
				if(this.generator.gravityForce <= (Settings.GravGen_MinGravity * 100)) this.generator.gravityForce = (int) (Settings.GravGen_MinGravity * 100);
				
				break;	
			case 15:
				this.generator.setControl(ControlMode.DISABLED);
				break;
			case 16:
				this.generator.setControl(ControlMode.LOW);
				break;
			case 17:
				this.generator.setControl(ControlMode.HIGH);
				break;
			default:
				break;
		}
//		this.generator.machineUsage = this.generator.updatePowerReq();
		this.generator.hasChanged = true;
		
		this.detectAndSendChanges();
		return false;
		
	}
	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
	        ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(index);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if (index < 1)
	            {
	                if (!this.mergeItemStack(itemstack1, 2, 37, true))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 0, 1, false))
	            {
	                return null;
	            }

	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.stackSize == itemstack.stackSize)
	            {
	                return null;
	            }

	            slot.onPickupFromSlot(player, itemstack1);
	        }

	        return itemstack;
	    }
	   

}
