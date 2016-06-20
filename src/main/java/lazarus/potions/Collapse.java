/*Imports*/
package lazarus.potions;
import java.util.Random;

import lazarus.utilities.handlers.PotionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/*Main*/
public class Collapse extends Potion {
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public Collapse(int par1, ResourceLocation icon,boolean par2, int par3) 
	{
		super(par1, null, par2, par3);
		this.setPotionName("potion.collapse");
	}
	
	/*---------------------------------------- Set icon ----------------------------------------*/
	public Potion setIconIndex(int par1, int par2) 
	{
		super.setIconIndex(par1, par2);
		return this;
	}
	
	/*---------------------------------------- Apply Effects ----------------------------------------*/
	public static void applyEffects(EntityPlayer thisPlayer, World world)
	{
		Random rand = new Random();
		/*Particle effects*/
		double d0 = (double)((float)thisPlayer.getPosition().getX() + rand.nextInt(10)-5);
        double d1 = (double)((float)thisPlayer.getPosition().getY() + rand.nextInt(10)-5);
        double d2 = (double)((float)thisPlayer.getPosition().getZ() + rand.nextInt(10)-5);
        double d3 = ((double)rand.nextInt(100)/100 - 0.5D) * 0.5D;
        double d4 = ((double)rand.nextInt(100)/100 - 0.5D) * 0.5D;
        double d5 = ((double)rand.nextInt(100)/100 - 0.5D) * 0.5D;
		world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
		world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
		world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
		
		/*Remove collapse effect if it is stalling.*/
		if (thisPlayer.getActivePotionEffect(PotionHandler.collapse).getDuration()==0) 
		{thisPlayer.removePotionEffect(PotionHandler.collapse.id);return;}
	}
}