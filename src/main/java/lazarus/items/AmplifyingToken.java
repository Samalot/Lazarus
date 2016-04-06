/*Imports*/
package lazarus.items;

import java.util.List;

import lazarus.main.LazarusItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*Main*/
public class AmplifyingToken extends BaseItem{
	/*---------------------------------------- Variables ----------------------------------------*/
	public static String name = "amplifying_token";
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public AmplifyingToken(){
		super(name, true);
		this.setMaxStackSize(1); /*Stack size - max 1*/
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oBecome a glass cannon");
	}
}

