/*Import*/
package lazarus.items;

import java.util.List;

import lazarus.utils.handlers.PotionHandler;
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
		super(name,false);
		this.setMaxStackSize(64); /*Overide sack size*/
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oNot from this world.");
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	@SideOnly(Side.CLIENT)
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		System.out.println(player.isPotionActive(PotionHandler.collapse));
		if(!player.isPotionActive(PotionHandler.collapse)){
			world.playSoundAtEntity(player, "lazarus:spooky", 10.0F, 1.0F);
		}
		
		if(world.isRemote)
		{
			if(!player.isPotionActive(PotionHandler.collapse))
			{
				player.addPotionEffect(new PotionEffect(PotionHandler.collapse.id, 400, 0));
				IChatComponent whisper = new ChatComponentText("§5§oYour §5§ohead §5§ostrains, §5§olike §5§osomething §5§ois §5§otearing §5§oat §5§othe §5§oseams §5§oof §5§oyour §5§omind.");
				player.addChatMessage(whisper);
			}
		}
		return stack;
    }
	
	
	
	
}
