/*Imports*/
package lazarus.items;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import lazarus.main.LazarusMod;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	{
		this.resLocList = resLocList;
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oStore Your Tokens!");
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player){rightClick();return par1ItemStack;}	
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer p5EP) {rightClick();}
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase target){rightClick();return true;}
	
	public void rightClick()
	{
		if(pressedFlag == 1)
		{	
		long difference = System.nanoTime() - flagTime;
		if(difference>1000000000){changeFlag = 1;}	
		}
		else{pressedFlag = 1;}
		if(changeFlag == 1){flagTime = System.nanoTime(); openFlag = (openFlag+1)%2; changeFlag = 0;}
		
	}
	
	/*---------------------------------------- Change model ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining){return resLocList.get(openFlag);}
	
	
}
