package com.gendeathrow.gentech.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;

public class Utils 
{
	  private static boolean bcWrenchExists = false;
	  private static boolean bcPipeExists = false;
	
	  static
	  {
	    try
	    {
	      Class.forName("buildcraft.api.tools.IToolWrench");
	      bcWrenchExists = true;
	    }
	    catch (Throwable localThrowable1) {
	    }
	    try {
	      Class.forName("buildcraft.api.transport.IPipeTile");
	      bcPipeExists = true;
	    }
	    catch (Throwable localThrowable2)
	    {
	    }
	  }

	  public static boolean isHoldingUsableWrench(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3)
	  {
	    Item localItem = paramEntityPlayer.getCurrentEquippedItem() != null ? paramEntityPlayer.getCurrentEquippedItem().getItem() : null;
	    if ((localItem instanceof IToolHammer))
	      return ((IToolHammer)localItem).isUsable(paramEntityPlayer.getCurrentEquippedItem(), paramEntityPlayer, paramInt1, paramInt2, paramInt3);
	    if (bcWrenchExists) {
	      return canHandleBCWrench(localItem, paramEntityPlayer, paramInt1, paramInt2, paramInt3);
	    }
	    return false;
	  }

	  public static void usedWrench(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3)
	  {
	    Item localItem = paramEntityPlayer.getCurrentEquippedItem() != null ? paramEntityPlayer.getCurrentEquippedItem().getItem() : null;
	    if ((localItem instanceof IToolHammer))
	      ((IToolHammer)localItem).toolUsed(paramEntityPlayer.getCurrentEquippedItem(), paramEntityPlayer, paramInt1, paramInt2, paramInt3);
	    else if (bcWrenchExists)
	      bcWrenchUsed(localItem, paramEntityPlayer, paramInt1, paramInt2, paramInt3);
	  }

	  private static boolean canHandleBCWrench(Item paramItem, EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3)
	  {
	    return ((paramItem instanceof IToolWrench)) && (((IToolWrench)paramItem).canWrench(paramEntityPlayer, paramInt1, paramInt2, paramInt3));
	  }

	  private static void bcWrenchUsed(Item paramItem, EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3)
	  {
	    if ((paramItem instanceof IToolWrench))
	      ((IToolWrench)paramItem).wrenchUsed(paramEntityPlayer, paramInt1, paramInt2, paramInt3);
	  }

}
