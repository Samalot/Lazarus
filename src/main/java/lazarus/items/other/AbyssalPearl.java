/*Import*/
package lazarus.items.other;

import java.util.List;

import lazarus.items.BaseItem;
import lazarus.utilities.handlers.NBTHandler;
import lazarus.utilities.handlers.PotionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class AbyssalPearl extends BaseItem{
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "abyssal_pearl"; 
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public AbyssalPearl()
	{
		super(name);
		this.setMaxStackSize(64); /*Overide sack size*/
		this.description = "Created by throwing an §3§lEnder§r §3§lPearl§r into a §5§lPortal§r. Used to create Tokens.";
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oNot from this world.");
		list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	@SideOnly(Side.CLIENT)
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {		
		return stack;
    }	
}
