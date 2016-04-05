/*Imports*/
package lazarus.items;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Mouse;
import lazarus.container.token_pouch.InventoryTokenPouch;
import lazarus.main.LazarusMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import lazarus.utils.handlers.*;

/*Main*/
public class Token_Pouch extends Item
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private int openFlag = 0; 
	ArrayList<ModelResourceLocation> resLocList = new ArrayList<ModelResourceLocation>();
	int pressedFlag = 0;
	int changeFlag = 1;
	long flagTime = 0;
	/*---------------------------------------- Constructor ----------------------------------------*/
	public Token_Pouch(String unlocalizedName)
	{	
		super(); /*Super class*/	
		this.setUnlocalizedName(unlocalizedName); /*Set name*/		
		this.setCreativeTab(LazarusMod.tabLazarus); /*Creative tab*/	
		this.setMaxStackSize(1); /*Stack size - max 1*/
		this.hasSubtypes = true;
		this.setMaxDamage(0); 
	}
	
	/*---------------------------------------- Set resource locations ----------------------------------------*/
	public void setRL(ArrayList<ModelResourceLocation> resLocList)
	{this.resLocList = resLocList;}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{list.add("§oStore Your Tokens!");}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{	
		if (!par2World.isRemote) {
			if (!player.isSneaking()) 
			{
				player.openGui(LazarusMod.instance, LazarusMod.GUI_ITEM_INV, player.worldObj, 0, 0, 0);
			} 
			else 
			{
				new InventoryTokenPouch(player.getHeldItem());
			}
		}
		return par1ItemStack;
	}
	
	/*---------------------------------------- Change model ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining){return resLocList.get(LazarusEventHandler.globalFlag_Token_Pouch_Open);}
	
	/*---------------------------------------- Stuff to make inv work! ----------------------------------------*/
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {return 1;}	
	
}
