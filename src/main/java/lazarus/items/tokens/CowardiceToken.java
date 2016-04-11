package lazarus.items.tokens;

import static java.lang.Math.abs;

import java.util.List;

import lazarus.items.BaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CowardiceToken extends BaseItem{
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "cowardice_token";
	float speed = 0.1F;
	/*---------------------------------------- Constructor ----------------------------------------*/
	public CowardiceToken(){super(name, true);}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oKeeping out of the action");
	}
	/*---------------------------------------- On tick ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		EntityPlayer player = (EntityPlayer) entity;
			/*All entities in the world*/
			List entities = world.loadedEntityList;
			for(Object ent: entities){if(ent instanceof EntityLiving)
			{
				if(ent instanceof EntityMob)
				{	
					/*Distance calculation*/
					EntityLiving livingEnt = (EntityLiving) ent;
					double entityX = livingEnt.posX; double playerX = player.posX;
					double entityY = livingEnt.posY; double playerY = player.posY;
					double entityZ = livingEnt.posZ; double playerZ = player.posZ;
					if(abs(entityX - playerX) <= 12 && 
					   abs(entityY - playerY) <= 4 &&
					   abs(entityZ - playerZ) <= 12)
					
					{
						/*
						System.out.println(livingEnt.getName());
						System.out.println("X: " + abs(entityX - playerX));
						System.out.println("Y: " + abs(entityY - playerY));
						System.out.println("Z: " + abs(entityZ - playerZ));
						System.out.println();
						*/
							player.capabilities.setPlayerWalkSpeed(0.25F); /*Increase player speed*/
					} else{player.capabilities.setPlayerWalkSpeed(0.1F);} /*Reset player speed*/
			}}
			}
	}
	
	/*---------------------------------------- On hurt ----------------------------------------*/
	public static void onMobHit(LivingHurtEvent event, EntityPlayer player){player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 1));}
}


