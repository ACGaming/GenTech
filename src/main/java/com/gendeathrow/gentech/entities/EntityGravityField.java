package com.gendeathrow.gentech.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.gendeathrow.gentech.tileentity.TileEntityGravityGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityGravityField extends Entity 
{
	public TileEntityGravityGenerator generator; 
	public float size; 
	
	public EntityGravityField(World world, TileEntityGravityGenerator generator)
	{
		this(world);
		
        this.posX = generator.xCoord + 0.5D;
        this.posY = generator.yCoord + 1.0D;
        this.posZ = generator.zCoord + 0.5D;
        this.generator = generator;
	}
	
	public EntityGravityField(World world)
	{
		super(world);
		this.noClip = true;
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() 
	{

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tags) 
	{
		super.readFromNBT(tags);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tags) 
	{
		super.writeToNBT(tags);
	}
	
    //This is pushOutOfBlocks
    @Override
    protected boolean func_145771_j(double par1, double par3, double par5)
    {
    	return false;
    }

    
    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public void moveEntity(double par1, double par3, double par5)
    {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
    }  
    

    @Override
    public void onEntityUpdate()
    {
        if (this.generator != null)
        {
            final Vec3 vec = Vec3.createVectorHelper(this.generator.xCoord, this.generator.yCoord, this.generator.zCoord);

            this.posX = vec.xCoord + 0.5D;
            this.posY = vec.yCoord + 1.0D;
            this.posZ = vec.zCoord + 0.5D;
        }

        super.onEntityUpdate();

        final TileEntity tileAt = this.worldObj.getTileEntity(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 1.0), MathHelper.floor_double(this.posZ));

        if (tileAt instanceof TileEntityGravityGenerator)
        {
            this.generator = (TileEntityGravityGenerator) tileAt;
        }
        else if (tileAt == null)
        {
            if (this.generator != null)
            {
                this.generator.gravityField = null;
            }

            this.generator = null;

            if (!this.worldObj.isRemote)
            {
                this.setDead();
            }
        }

        if (!this.worldObj.isRemote)
        {
            if (this.generator != null && (this.generator.gravityField == null || !this.generator.gravityField.equals(this)))
            {
               // this.generator.gravityField = this;
            }

            if (this.generator == null)
            {
                this.setDead();
            }

            if (tileAt == null)
            {
                this.setDead();
            }
        }

        if (!this.worldObj.isRemote && this.generator != null)
        {
            if (this.generator.power > this.generator.machineUsage)
            {
                this.size += 0.01F;
            }
            else
            {
                this.size -= 0.1F;
            }

            this.size = Math.min(Math.max(this.size, 0.0F), 10.0F);
        }

        if (this.generator != null)
        {
           // final Vector3 vec = new Vector3(this.generator);

           // this.posX = vec.x + 0.5D;
           // this.posY = vec.y + 1.0D;
           // this.posZ = vec.z + 0.5D;
        }
    }


}
