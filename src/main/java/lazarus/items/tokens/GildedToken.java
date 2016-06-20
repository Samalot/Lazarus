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
		this.description = 
				"Change your §6§lgold§r into §a§lxp§r, "
						+ "\n§lright§r §lclick§r to feed §6§lgold§r"
						+ "\nand be rewarded with §a§lxp§r!"
						+ "\nHolding §lshift§r will allow"
						+ "\nyou to feed §6§lgold§r §6§lblocks§r";
		/*"-------------------------"*/
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
			list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
		}
		/*Otherwise*/
		else{list.add("§oGold goes in!");list.add("§oXP comes out!");list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");}
	}

	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}

	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
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
