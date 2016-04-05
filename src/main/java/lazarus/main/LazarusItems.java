/*Imports*/
package lazarus.main;
import lazarus.items.Amsollions_token;
import lazarus.items.BaseItem;
import lazarus.items.Token_Pouch;
import lazarus.items.Waning_token;
import lazarus.utils.config.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/*Main*/
public class LazarusItems 
{
	
	/*---------------------------------------- Items variables ----------------------------------------*/
	public static Amsollions_token amsollions_token;
	public static Waning_token waning_token;
	
	public static Token_Pouch token_pouch;
	
	
	/*---------------------------------------- Init items ----------------------------------------*/
	public static void init()
	{
		amsollions_token = new Amsollions_token();
		waning_token = new Waning_token();
		
		token_pouch = new Token_Pouch();
	}
	
	/*---------------------------------------- Register items ----------------------------------------*/
	public static void register()
	{
		GameRegistry.registerItem(amsollions_token, amsollions_token.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(waning_token, waning_token.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(token_pouch, token_pouch.getUnlocalizedName().substring(5));
	}
	
	/*---------------------------------------- Register item renders ----------------------------------------*/
	public static void registerRenders()
	{
		registerRender(amsollions_token, 0, "");
		registerRender(waning_token, 0, "");
		
		registerRender(token_pouch, 0, "");
		registerRender(token_pouch, 1, "_open");
	}
	
	/*---------------------------------------- Register item render ----------------------------------------*/
	public static void registerRender(BaseItem item, int metadata, String suffix)
	{
		String name = Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5) + suffix;
		ModelResourceLocation res = new ModelResourceLocation(name ,"inventory");
		item.registerItemModel(res);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, metadata, res);
		ModelBakery.addVariantName(item, name);
	}
}
