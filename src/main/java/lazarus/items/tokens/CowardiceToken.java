package lazarus.items.tokens;

import static java.lang.Math.abs;

import java.util.Arrays;
import java.util.List;

import lazarus.items.BaseToken;
import lazarus.utilities.handlers.NBTHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CowardiceToken extends BaseToken{
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "cowardice_token";
	float speed = 0.1F;
	/*---------------------------------------- Constructor ----------------------------------------*/
	public CowardiceToken()
	{
		super(name);
		this.amplifiers=Arrays.asList(1.00, 1.25, 1.50, 1.75, 2.00, 2.50);
		this.description = "Only a fool would pick a fight he could not win. Let your fear eclipse you and with it bring you power - power that will keep you alive.";
		this.subDescription.add("Recieve Speed II when near a HOSTILE mob.");
		this.subDescription.add("Recieve Slowness when attacking ANY mob.");
		this.subDescription.add("Rarity controls the length and power of the applied effects.");
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("Run away!!!");
		/*If item has been initialised*/
		if(stack.hasTagCompound())
		{
			int rarity = stack.getTagCompound().getInteger("rarity");
			String rarityInfo = NBTHandler.getRarityInfo(rarity);
			list.add("Rarity: " + rarityInfo);
		}
		/*Otherwise*/
		list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
	}
	
	/*---------------------------------------- On tick ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
		/*Pouch with inventory*/
		EntityPlayer player = null;
		boolean flag = false;
		if(entity instanceof EntityPlayer){player = (EntityPlayer) entity; flag = true;}
		if(flag)
		{
			scanForSpeed(world, player);
		}
	}
	
	/*---------------------------------------- On tick ----------------------------------------*/
	public static void scanForSpeed(World world, EntityPlayer player)
	{
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
				if(abs(entityX - playerX) <= 4 && 
						abs(entityY - playerY) <= 4 &&
						abs(entityZ - playerZ) <= 4)
				{
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50, 1));
				} 
			}
		}}
	}
	
	/*---------------------------------------- On hurt ----------------------------------------*/
	public static void onMobHit(EntityPlayer player){player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 75, 2));}
}


