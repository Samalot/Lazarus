/*Imports*/
package lazarus.items;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lazarus.main.LazarusItems;
import lazarus.main.LazarusMod;
import lazarus.utils.handlers.TokenHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/*Main*/
public class WaningToken extends BaseItem {
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "waning_token";
	int loopCount = 0;
	/*---------------------------------------- Constructor ----------------------------------------*/
	public WaningToken()
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
	
	/*---------------------------------------- On tick update ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(loopCount++ % 2 == 0)
		{
			TokenHandler.waningToken(entity);
		}
	}
}
