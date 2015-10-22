package com.gendeathrow.gentech.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GravityGeneratorModel extends ModelBase
{
	 //fields
    ModelRenderer CenterMachine;
    ModelRenderer Top;
    ModelRenderer sidebar3;
    ModelRenderer sidebar1;
    ModelRenderer sidebar2;
    ModelRenderer sidebar4;
    ModelRenderer power;
    ModelRenderer insideTop;
    ModelRenderer Shape1;
  
  public GravityGeneratorModel()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      CenterMachine = new ModelRenderer(this, 0, 0);
      CenterMachine.addBox(0F, 0F, 0F, 14, 9, 14);
      CenterMachine.setRotationPoint(-7F, 14F, -7F);
      CenterMachine.setTextureSize(64, 64);
      CenterMachine.mirror = true;
      setRotation(CenterMachine, 0F, 0F, 0F);
      
      Top = new ModelRenderer(this, 0, 40);
      Top.addBox(0F, 0F, 0F, 16, 1, 16);
      Top.setRotationPoint(-8F, 8F, -8F);
      Top.setTextureSize(64, 64);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      
      sidebar3 = new ModelRenderer(this, 56, 0);
      sidebar3.addBox(0F, 0F, 0F, 1, 14, 1);
      sidebar3.setRotationPoint(-8F, 9F, 7F);
      sidebar3.setTextureSize(64, 64);
      sidebar3.mirror = true;
      setRotation(sidebar3, 0F, 0F, 0F);
      
      sidebar1 = new ModelRenderer(this, 56, 0);
      sidebar1.addBox(0F, 0F, 0F, 1, 14, 1);
      sidebar1.setRotationPoint(7F, 9F, -8F);
      sidebar1.setTextureSize(64, 64);
      sidebar1.mirror = true;
      setRotation(sidebar1, 0F, 0F, 0F);
      
      sidebar2 = new ModelRenderer(this, 56, 0);
      sidebar2.addBox(0F, 0F, 0F, 1, 14, 1);
      sidebar2.setRotationPoint(-8F, 9F, -8F);
      sidebar2.setTextureSize(64, 64);
      sidebar2.mirror = true;
      setRotation(sidebar2, 0F, 0F, 0F);
      
      sidebar4 = new ModelRenderer(this, 56, 0);
      sidebar4.addBox(0F, 0F, 0F, 1, 14, 1);
      sidebar4.setRotationPoint(7F, 9F, 7F);
      sidebar4.setTextureSize(64, 64);
      sidebar4.mirror = true;
      setRotation(sidebar4, 0F, 0F, 0F);
      
      power = new ModelRenderer(this, 0, 23);
      power.addBox(-6F, -1F, -6F, 12, 3, 12);
      power.setRotationPoint(0F, 12F, 0F);
      power.setTextureSize(64, 64);
      power.mirror = true;
      setRotation(power, 0F, 0F, 0F);
      
      insideTop = new ModelRenderer(this, 0, 48);
      insideTop.addBox(0F, 0F, 0F, 14, 2, 14);
      insideTop.setRotationPoint(-7F, 9F, -7F);
      insideTop.setTextureSize(64, 64);
      insideTop.mirror = true;
      setRotation(insideTop, 0F, 0F, 0F);
      
      Shape1 = new ModelRenderer(this, 0, 31);
      Shape1.addBox(0F, 0F, 0F, 16, 1, 16);
      Shape1.setRotationPoint(-8F, 23F, -8F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    CenterMachine.render(f5);
    Top.render(f5);
    sidebar3.render(f5);
    sidebar1.render(f5);
    sidebar2.render(f5);
    sidebar4.render(f5);
    power.render(f5);
    insideTop.render(f5);
    Shape1.render(f5);
  }
  
  public void renderModel(float f5)
  {
	    CenterMachine.render(f5);
	    Top.render(f5);
	    sidebar3.render(f5);
	    sidebar1.render(f5);
	    sidebar2.render(f5);
	    sidebar4.render(f5);
	    power.render(f5);
	    insideTop.render(f5);
	    Shape1.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity p_78087_7_)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, p_78087_7_);
  }
}
