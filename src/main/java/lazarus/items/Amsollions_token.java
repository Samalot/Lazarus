/*Imports*/
package lazarus.items;
import java.util.List;
import lazarus.main.LazarusMod;
import lazarus.utils.handlers.KeyboardHelper;
import lazarus.utils.handlers.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

/*Main*/
public class Amsollions_token extends Item
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private int xpAmount = LazarusMod.lazarusConfig.get(Configuration.CATEGORY_GENERAL, "amsollionXPfromGold", 6).getInt();
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public Amsollions_token(String unlocalizedName)
	{	
		super(); /*Super class*/
		this.setUnlocalizedName(unlocalizedName); /*Set name*/		
		this.setCreativeTab(LazarusMod.tabLazarus); /*Creative tab*/	
		this.setMaxStackSize(1); /*Stack size - max 1*/
	}
	
	/*---------------------------------------- Make it shiny ----------------------------------------*/
	public boolean hasEffect(ItemStack par1ItemStack){return true;}
	
	/*Tooltip*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oGold goes in!");list.add("§oXP comes out!");
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
		if(!KeyboardHelper.isShiftDown()){ /*If player is not holding shift*/
			if(player.inventory.hasItem(Items.gold_ingot)) /*If player has golden ingot*/
			{
			player.inventory.consumeInventoryItem(Items.gold_ingot); /*consume ingot*/
			player.addExperience(xpAmount); /*give xp*/
			SoundHandler.lazarusPlaySound("random.levelup", 0.1F, 0.3F);
			}
		}
		
		if(KeyboardHelper.isShiftDown()){ /*If player is holding shift*/
			if(player.inventory.hasItemStack(new ItemStack(Blocks.gold_block)))
			{
			player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.gold_block)); /*Take gold blocks from player*/	
			player.addExperience(xpAmount*9); /*Give XP*/	
			SoundHandler.lazarusPlaySound("random.levelup", 0.1F, 0.3F);
			}
		}	
		return par1ItemStack;
    }
	
}
