/*Imports*/
package lazarus.items;

import java.util.List;

import lazarus.utils.handlers.NBTHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		/*If item has been initialised*/
		if(stack.hasTagCompound())
		{
			int rarity = stack.getTagCompound().getInteger("rarity");
			list.add("Rarity: " + NBTHandler.getRarityInfo(rarity));
		}
		else{list.add("§oBecome a glass cannon");}
	}
	
	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}
}

