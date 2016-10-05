/**=============== Imports ===============**/
package lazarus.main;
import lazarus.blocks.infusatron.Infusatron;
import lazarus.utilities.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**=============== Main ===============**/
public class LazarusBlocks {
	/*Variables*/
	public static Block infusatron;
	
	/*Initialise the blocks*/
	public static void init()
	{
		infusatron = new Infusatron(Material.cloth).setUnlocalizedName("infusatron");
	}
	
	/*Register the blocks*/
	public static void register()
	{
		GameRegistry.registerBlock(infusatron, infusatron.getUnlocalizedName().substring(5));
	}
	
	/*Bulk register renders*/
	public static void registerRenders()
	{
		registerRender(infusatron);
	}
	
	/*Register the inventory renders*/
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5),"inventory"));
	}
	
	/*Add crafting recipes*/
	public static void  craftingRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(infusatron),
				"GII",
				"GII",
				"ODO",
				'O', Blocks.obsidian, 'D', Items.diamond, 'G', Blocks.glass, 'I', Blocks.iron_block);
		
		GameRegistry.addSmelting(new ItemStack(infusatron), new ItemStack(Blocks.iron_block,2), 1F);
	}
}
