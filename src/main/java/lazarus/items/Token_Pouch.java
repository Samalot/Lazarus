/*Imports*/
package lazarus.items;
import java.util.List;

import lazarus.container.token_pouch.InventoryTokenPouch;
import lazarus.main.LazarusMod;
import lazarus.utils.handlers.LazarusEventHandler;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class Token_Pouch extends BaseItem
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "token_pouch";
		
	/*---------------------------------------- Constructor ----------------------------------------*/
	public Token_Pouch()
	{	
		super(name, false); /*Super class*/	
		this.setMaxStackSize(1); /*Stack size - max 1*/
		this.setMaxDamage(0); 
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{list.add("§oStore Your Tokens!");}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{	
		if (!par2World.isRemote) 
		{
			if (!player.isSneaking()) 
			{player.openGui(LazarusMod.instance, LazarusMod.GUI_ITEM_INV, player.worldObj, 0, 0, 0);} 
			else 
			{new InventoryTokenPouch(player.getHeldItem());}
		}
		return par1ItemStack;
	}
	
	/*---------------------------------------- Change model ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining){
		return resourseLocationArray.get(LazarusEventHandler.globalFlag_Token_Pouch_Open);
	}
	
	
	/*---------------------------------------- Stuff to make inv work! ----------------------------------------*/
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {return 1;}	
	
}
