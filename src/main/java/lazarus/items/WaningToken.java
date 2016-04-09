/*Imports*/
package lazarus.items;
import java.util.List;

import lazarus.utils.handlers.NBTHandler;
import lazarus.utils.handlers.TokenHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		/*If item has been initialised*/
		if(stack.hasTagCompound())
		{
			int rarity = stack.getTagCompound().getInteger("rarity");
			list.add("Rarity: " + NBTHandler.getRarityInfo(rarity));
		}
		else{list.add("§oA small sacrifice for a greater gain.");}
	}
	
	/*---------------------------------------- On tick update ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
		if(loopCount++ % 2 == 0)
		{
			TokenHandler.waningToken(entity);
		}
	}
}
