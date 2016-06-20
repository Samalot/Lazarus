/*Imports*/
package lazarus.items.tokens;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lazarus.items.BaseToken;
import lazarus.utilities.handlers.NBTHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class WaningToken extends BaseToken 
{
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "waning_token";
	int loopCount = 0;
	/*---------------------------------------- Constructor ----------------------------------------*/
	public WaningToken()
	{
		super(name);
		this.amplifiers=Arrays.asList(1.00, 1.25, 1.50, 1.75, 2.00, 2.50);
		this.description = 
				"Protect yourself from wither."
						+ "\nIf you are inflicted by"
						+ "\nwither, replace it with a"
						+ "\ndifferent negative buff!";
		/*"-------------------------§§§"*/
	}

	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		/*If item has been initialised*/
		if(stack.hasTagCompound())
		{
			int rarity = stack.getTagCompound().getInteger("rarity");
			list.add("Rarity: " + NBTHandler.getRarityInfo(rarity));
			list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");

		}
		else{list.add("§oA small sacrifice for a greater gain.");list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");}
	}

	/*---------------------------------------- On tick ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		/*Pouch with inventory*/
		EntityPlayer player = null;
		boolean flag = false;
		if(entity instanceof EntityPlayer){player = (EntityPlayer) entity; flag = true;}
		if(flag)
		{
			waningTokenEffect(player);
		}
	}
	
	
	/*---------------------------------------- Waning Token ----------------------------------------*/
	public static void waningTokenEffect(EntityPlayer player)
	{
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
