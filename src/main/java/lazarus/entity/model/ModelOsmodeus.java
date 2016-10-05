//  
//  =====GPL=============================================================
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; version 2 dated June, 1991.
// 
//  This program is distributed in the hope that it will be useful, 
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
// 
//  You should have received a copy of the GNU General Public License
//  along with this program;  if not, write to the Free Software
//  Foundation, Inc., 675 Mass Ave., Cambridge, MA 02139, USA.
//  =====================================================================
//
//
//
package lazarus.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;


public class ModelOsmodeus extends ModelBase {

	//fields
	ModelRenderer Body;
	ModelRenderer Left_Leg;
	ModelRenderer Right_Leg;
	ModelRenderer Left_Arm;
	ModelRenderer Right_Arm;
	ModelRenderer Head;
	ModelRenderer BR1;
	ModelRenderer BR2;
	ModelRenderer BR3;
	ModelRenderer BR4;
	ModelRenderer BR5;
	ModelRenderer RSH1;
	ModelRenderer LSH1;
	ModelRenderer RSH2;
	ModelRenderer LSH2;
	ModelRenderer RSH3;
	ModelRenderer LSH3;
	ModelRenderer RSH4;
	ModelRenderer LSH4;	

	public ModelOsmodeus()
	{
		 textureWidth = 512;
		    textureHeight = 512;
		    
		      Body = new ModelRenderer(this, 128, 128);
		      Body.addBox(-32F, 0F, -16F, 64, 96, 32);
		      Body.setRotationPoint(0F, -168F, 0F);
		      Body.setTextureSize(512, 512);

		      setRotation(Body, 0F, 0F, 0F);

		      Left_Leg = new ModelRenderer(this, 0, 128);
		      Left_Leg.addBox(-16F, 0F, -16F, 32, 96, 32);
		      Left_Leg.setRotationPoint(16F, -72F, 0F);
		      Left_Leg.setTextureSize(512, 512);

		      setRotation(Left_Leg, 0F, 0F, 0F);

		      Right_Leg = new ModelRenderer(this, 0, 128);
		      Right_Leg.addBox(-16F, 0F, -16F, 32, 96, 32);
		      Right_Leg.setRotationPoint(-16F, -72F, 0F);
		      Right_Leg.setTextureSize(512, 512);

		      setRotation(Right_Leg, 0F, 0F, 0F);

		      Left_Arm = new ModelRenderer(this, 320, 128);
		      Left_Arm.addBox(0F, -8F, -16F, 32, 96, 32);
		      Left_Arm.setRotationPoint(32F, -160F, 0F);
		      Left_Arm.setTextureSize(512, 512);

		      setRotation(Left_Arm, 0F, 0F, 0F);

		      Right_Arm = new ModelRenderer(this, 320, 128);
		      Right_Arm.addBox(-32F, -8F, -16F, 32, 96, 32);
		      Right_Arm.setRotationPoint(-32F, -160F, 0F);
		      Right_Arm.setTextureSize(512, 512);

		      setRotation(Right_Arm, 0F, 0F, 0F);
		      Head = new ModelRenderer(this, 0, 0);
		      Head.addBox(-32F, -64F, -32F, 64, 64, 64);
		      Head.setRotationPoint(0F, -168F, 0F);
		      Head.setTextureSize(512, 512);

		      setRotation(Head, 0F, 0F, 0F);
		      BR1 = new ModelRenderer(this, 467, 500);
		      BR1.addBox(0F, 0F, 0F, 18, 4, 3);
		      BR1.setRotationPoint(17F, -181F, -34F);
		      BR1.setTextureSize(512, 512);

		      setRotation(BR1, 0F, 0F, 0F);
		      BR2 = new ModelRenderer(this, 467, 500);
		      BR2.addBox(0F, 0F, 0F, 21, 4, 3);
		      BR2.setRotationPoint(-35F, -181F, -34F);
		      BR2.setTextureSize(512, 512);

		      setRotation(BR2, 0F, 0F, 0F);
		      BR3 = new ModelRenderer(this, 357, 437);
		      BR3.addBox(0F, 0F, 0F, 3, 4, 64);
		      BR3.setRotationPoint(32F, -181F, -32F);
		      BR3.setTextureSize(512, 512);

		      setRotation(BR3, 0F, 0F, 0F);
		      BR4 = new ModelRenderer(this, 357, 437);
		      BR4.addBox(0F, 0F, 0F, 3, 4, 64);
		      BR4.setRotationPoint(-35F, -181F, -32F);
		      BR4.setTextureSize(512, 512);
	
		      setRotation(BR4, 0F, 0F, 0F);
		      BR5 = new ModelRenderer(this, 356, 498);
		      BR5.addBox(0F, 0F, 0F, 70, 4, 3);
		      BR5.setRotationPoint(-35F, -181F, 32F);
		      BR5.setTextureSize(512, 512);
	
		      setRotation(BR5, 0F, 0F, 0F);
		      RSH1 = new ModelRenderer(this, 8, 440);
		      RSH1.addBox(-6F, -27F, -20F, 11, 27, 44);
		      RSH1.setRotationPoint(-37F, -147F, -2F);
		      RSH1.setTextureSize(512, 512);
		 
		      setRotation(RSH1, 0F, 0F, 0F);
		      LSH1 = new ModelRenderer(this, 8, 440);
		      LSH1.addBox(-6F, -27F, -21F, 11, 26, 44);
		      LSH1.setRotationPoint(38F, -147F, 0F);
		      LSH1.setTextureSize(512, 512);
	
		      setRotation(LSH1, 0F, 0F, 0F);
		      RSH2 = new ModelRenderer(this, 8, 440);
		      RSH2.addBox(-9F, -20F, -20F, 17, 20, 38);
		      RSH2.setRotationPoint(-51F, -151F, 1F);
		      RSH2.setTextureSize(512, 512);
		
		      setRotation(RSH2, 0F, 0F, 0F);
		      LSH2 = new ModelRenderer(this, 8, 440);
		      LSH2.addBox(-9F, -20F, -20F, 17, 20, 38);
		      LSH2.setRotationPoint(52F, -151F, 1F);
		      LSH2.setTextureSize(512, 512);
		    
		      setRotation(LSH2, 0F, 0F, 0F);
		      RSH3 = new ModelRenderer(this, 8, 440);
		      RSH3.addBox(-5F, -14F, -20F, 11, 14, 34);
		      RSH3.setRotationPoint(-66F, -155F, 3F);
		      RSH3.setTextureSize(512, 512);
		    
		      setRotation(RSH3, 0F, 0F, 0F);
		      LSH3 = new ModelRenderer(this, 8, 440);
		      LSH3.addBox(-5F, -14F, -20F, 11, 14, 34);
		      LSH3.setRotationPoint(65F, -155F, 3F);
		      LSH3.setTextureSize(512, 512);
		     
		      setRotation(LSH3, 0F, 0F, 0F);
		      RSH4 = new ModelRenderer(this, 158, 450);
		      RSH4.addBox(-17F, -11F, -17F, 35, 22, 34);
		      RSH4.setRotationPoint(-50F, -145F, 0F);
		      RSH4.setTextureSize(512, 512);
		 
		      setRotation(RSH4, 0F, 0F, 0F);
		      LSH4 = new ModelRenderer(this, 158, 450);
		      LSH4.addBox(-17F, -11F, -17F, 35, 22, 34);
		      LSH4.setRotationPoint(49F, -145F, 0F);
		      LSH4.setTextureSize(512, 512);
		
		      setRotation(LSH4, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		float div = 5.0F;
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.0F / div, 1.0F / div, 1.0F / div);
		GlStateManager.translate(0.0F, 100.0F*f5, 0.0F);

		Body.render(f5);
	    Left_Leg.render(f5);
	    Right_Leg.render(f5);
	    Left_Arm.render(f5);
	    Right_Arm.render(f5);
	    Head.render(f5);
	    BR1.render(f5);
	    BR2.render(f5);
	    BR3.render(f5);
	    BR4.render(f5);
	    BR5.render(f5);
	    RSH1.render(f5);
	    LSH1.render(f5);
	    RSH2.render(f5);
	    LSH2.render(f5);
	    RSH3.render(f5);
	    LSH3.render(f5);
	    RSH4.render(f5);
	    LSH4.render(f5);

		GlStateManager.popMatrix();

	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}


}