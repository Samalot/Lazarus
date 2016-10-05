package lazarus.entity.render;

import lazarus.entity.EntityOsmodeus;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderOsmodeus extends RenderLiving {

	private static final ResourceLocation skin = new ResourceLocation("lazarus:textures/entity/Osmodeus.png");

	public RenderOsmodeus(RenderManager rm, ModelBase modelbase, float f)
	{
		super(rm, modelbase, f);
	}

	protected float getDeathMaxRotation(EntityOsmodeus entitycroc)
	{
		return 180.0F;
	}

	@Override
	protected float getDeathMaxRotation(EntityLivingBase entityLivingBase)
	{
		return this.getDeathMaxRotation((EntityOsmodeus) entityLivingBase);
	}

	public void renderCroc(EntityOsmodeus entitycroc, double d, double d1, double d2, float f, float f1)
	{
		super.doRender(entitycroc, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1)
	{
		renderCroc((EntityOsmodeus) entity, d, d1, d2, f, f1);
	}



	@Override
	protected ResourceLocation getEntityTexture(Entity entity){return skin;}

}