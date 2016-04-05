/*Imports*/
package lazarus.items;
import java.util.ArrayList;

import lazarus.main.LazarusMod;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/*Main*/
public class BaseItem extends Item
{

	/*---------------------------------------- Variables ----------------------------------------*/
	private String name;
	private boolean isShiny;
	ArrayList<ModelResourceLocation> resourseLocationArray = new ArrayList<ModelResourceLocation>();
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public BaseItem(String name, boolean isShiny)
	{
		super(); /*Super class*/
		this.name = name; /*Set name*/	
		this.setUnlocalizedName(name); /*Set unlocalized name*/		
		this.setCreativeTab(LazarusMod.tabLazarus); /*Creative tab*/
		this.isShiny = isShiny; /*Set is shiny*/
	}
	
	/*---------------------------------------- Make it shiny ----------------------------------------*/
	public boolean hasEffect(ItemStack par1ItemStack){return isShiny;}
	
	/*---------------------------------------- Set resource locations ----------------------------------------*/
	public void registerItemModel(ModelResourceLocation resLocList)
	{this.resourseLocationArray.add(resLocList);}
	
}
