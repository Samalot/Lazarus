/*Imports*/
package lazarus.items;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lazarus.main.LazarusItems;
import lazarus.main.LazarusMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/*Main*/
public class Waning_token extends BaseItem {
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "waning_token";
	int loopCount = 0;
	/*---------------------------------------- Constructor ----------------------------------------*/
	public Waning_token()
	{
		super(name,true); /*Super class*/	
		this.setMaxStackSize(1); /*Stack size - max 1*/
	}

	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oA small sacrifice for a greater gain.");
	}
	
	/*---------------------------------------- Test ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(Potion.wither.id, 25, 0));		
		return par1ItemStack;
		
	}
	
	/*---------------------------------------- On tick update ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		EntityPlayer player = (EntityPlayer) entity;
		if(loopCount++ % 2 == 0)
		{
				if(player.isPotionActive(Potion.wither)) /*Check if the player has the wither effect*/
				{
					player.removePotionEffect(Potion.wither.getId()); /*Clear the player of wither*/
					
					/*Select and apply a random potion effect*/
					Random random = new Random();
					int index = random.nextInt(4);
					System.out.println(index);
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
}
