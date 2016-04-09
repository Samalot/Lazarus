/*Imports*/
package lazarus.utils.handlers;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/*Main*/
public class TokenHandler {
	/*---------------------------------------- Waning Token ----------------------------------------*/
	public static void waningToken(Entity entity)
	{
		EntityPlayer player = (EntityPlayer) entity;
		if(player.isPotionActive(Potion.wither)) /*Check if the player has the wither effect*/
		{
			player.removePotionEffect(Potion.wither.getId()); /*Clear the player of wither*/
			
			/*Select and apply a random potion effect*/
			Random random = new Random();
			int index = random.nextInt(4);
			if(!player.isPotionActive(Potion.poison) && !player.isPotionActive(Potion.hunger)
					&& !player.isPotionActive(Potion.moveSlowdown)  && !player.isPotionActive(Potion.confusion)
					 && !player.isPotionActive(Potion.weakness))
			{
				
				if(index == 0){player.addPotionEffect(new PotionEffect(Potion.poison.id, 100, 1));}
				else if(index == 1){player.addPotionEffect(new PotionEffect(Potion.hunger.id, 100, 1));}
				else if(index == 2){player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 1));}
				else if(index == 3){player.addPotionEffect(new PotionEffect(Potion.confusion.id, 100, 1));}
				else if(index == 4){player.addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 1));}
			}	
		}										
	}	
}
