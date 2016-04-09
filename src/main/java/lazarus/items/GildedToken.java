/*Imports*/
package lazarus.items;
import java.util.List;

import lazarus.utils.handlers.KeyboardHandler;
import lazarus.utils.handlers.NBTHandler;
import lazarus.utils.handlers.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class GildedToken extends BaseItem
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "gilded_token";
	private int xpAmount = 6;
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public GildedToken()
	{	
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
		/*Otherwise*/
		else{list.add("§oGold goes in!");list.add("§oXP comes out!");}
	}
	
	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		int boostedAmount = (int) Math.round(xpAmount*NBTHandler.getRarityBoost(stack.getTagCompound().getInteger("rarity")));
		System.out.println(boostedAmount);
		if(!KeyboardHandler.isShiftDown()){ /*If player is not holding shift*/
			if(player.inventory.hasItem(Items.gold_ingot)) /*If player has golden ingot*/
			{
			player.inventory.consumeInventoryItem(Items.gold_ingot); /*consume ingot*/
			player.addExperience(boostedAmount); /*give xp*/
			//SoundHandler levelup = new SoundHandler("random.levelup", 0.1F, 0.3F);
			}
		}
		
		if(KeyboardHandler.isShiftDown()){ /*If player is holding shift*/
			if(player.inventory.hasItemStack(new ItemStack(Blocks.gold_block)))
			{
			player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.gold_block)); /*Take gold blocks from player*/	
			player.addExperience(boostedAmount*9); /*Give XP*/	
			//SoundHandler levelup = new SoundHandler("random.levelup", 0.1F, 0.3F);
			}
		}	
		return stack;
    }
	
}
