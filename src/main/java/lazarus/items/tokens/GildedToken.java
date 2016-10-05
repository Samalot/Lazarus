/*Imports*/
package lazarus.items.tokens;
import java.util.Arrays;
import java.util.List;

import lazarus.items.BaseToken;
import lazarus.utilities.handlers.KeyboardHandler;
import lazarus.utilities.handlers.NBTHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class GildedToken extends BaseToken
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "gilded_token";
	private int xpAmount = 6;

	/*---------------------------------------- Constructor ----------------------------------------*/
	public GildedToken()
	{
		super(name);
		this.amplifiers=Arrays.asList(1.00, 1.25, 1.50, 1.75, 2.00, 2.50);
		this.description = "Greed and power go hand in hand, consuming the minds of man and mob alike. Infused within this stone is the raw Abyssal power to transform the root of all greed - gold - into pure power.";
		this.subDescription.add("Right click to exchange gold ingots for XP.");
		this.subDescription.add("Shift Right click to exchange gold blocks for XP (X9).");
		this.subDescription.add("Rarity boosts the amount of XP gained.");
	}

	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("Gold to XP!");
		/*If item has been initialised*/
		if(stack.hasTagCompound())
		{
			int rarity = stack.getTagCompound().getInteger("rarity");
			String rarityInfo = NBTHandler.getRarityInfo(rarity);
			list.add("Rarity: " + rarityInfo);
		}
		/*Otherwise*/
		list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
	}

	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}

	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		
		System.out.println(amplifiers.get(stack.getTagCompound().getInteger("rarity")));
		
		int boostedAmount = (int) Math.round(xpAmount*amplifiers.get(stack.getTagCompound().getInteger("rarity")));
		System.out.println(boostedAmount);
		if(!KeyboardHandler.isShiftDown()){ /*If player is not holding shift*/
			if(player.inventory.hasItem(Items.gold_ingot)) /*If player has golden ingot*/
			{
				player.inventory.consumeInventoryItem(Items.gold_ingot); /*consume ingot*/
				player.addExperience(boostedAmount); /*give xp*/
				world.playSoundEffect(player.posX, player.posY, player.posZ, "random.pop", 1.0F, 1.0F);	
			}
		}

		if(KeyboardHandler.isShiftDown()){ /*If player is holding shift*/
			if(player.inventory.hasItemStack(new ItemStack(Blocks.gold_block)))
			{
				player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.gold_block)); /*Take gold blocks from player*/	
				player.addExperience(boostedAmount*9); /*Give XP*/	
				world.playSoundEffect(player.posX, player.posY, player.posZ, "random.pop", 1.2F, 0.3F);	
			}
		}	
		return stack;
	}

}
