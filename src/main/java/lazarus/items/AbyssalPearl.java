/*Import*/
package lazarus.items;

import java.util.List;

import lazarus.utils.handlers.KeyboardHandler;
import lazarus.utils.handlers.NBTHandler;
import lazarus.utils.handlers.PotionHandler;
import lazarus.utils.handlers.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oNot from this world.");
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(world.isRemote)
		{
			
			if(!player.isPotionActive(PotionHandler.collapse))
			{
				player.addPotionEffect(new PotionEffect(PotionHandler.collapse.id, 400, 0));
				IChatComponent whisper = new ChatComponentText("§5§oYour §5§ohead §5§ostrains, §5§olike §5§osomething §5§ois §5§otearing §5§oat §5§othe §5§oseams §5§oof §5§oyour §5§omind.");
				player.addChatMessage(whisper);
				
				world.playSoundAtEntity(player, "lazarus:spooky", 10.0F, 1.0F);
			}
		}
		
		
	
		
		return stack;
    }
	
	
	
	
}
