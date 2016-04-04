/*Imports*/
package lazarus.main.init;
import java.util.ArrayList;

import lazarus.items.Amsollions_token;
import lazarus.items.Token_Pouch;
import lazarus.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/*Main*/
public class LazarusItems 
{
	
	/*---------------------------------------- Items variables ----------------------------------------*/
	public static Item amsollions_token;
	public static Token_Pouch token_pouch;
	
	/*---------------------------------------- Init items ----------------------------------------*/
	public static void init()
	{
		amsollions_token = new Amsollions_token("amsollions_token");
		token_pouch = new Token_Pouch("token_pouch");
	}
	
	/*---------------------------------------- Register items ----------------------------------------*/
	public static void register()
	{
		GameRegistry.registerItem(amsollions_token, amsollions_token.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(token_pouch, token_pouch.getUnlocalizedName().substring(5));
	}
	
	/*---------------------------------------- Register item renders ----------------------------------------*/
	public static void registerRenders()
	{
		registerRender(amsollions_token, 0);
		
		
		ArrayList<ModelResourceLocation> resLocList = new ArrayList<ModelResourceLocation>();
		
		ModelResourceLocation res = new ModelResourceLocation("lazarus:token_pouch","inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(token_pouch, 0, res);
		
		resLocList.add(res);
		
		ModelResourceLocation res2 = new ModelResourceLocation("lazarus:token_pouch_open","inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(token_pouch, 1, res);
		
		resLocList.add(res2);
		
		token_pouch.setRL(resLocList);
		
		ModelBakery.addVariantName(token_pouch, new String[]{
				Reference.MOD_ID + ":" + token_pouch.getUnlocalizedName().substring(5),
				Reference.MOD_ID + ":" + token_pouch.getUnlocalizedName().substring(5) + "_open"
		});
		
		
		
	}
	
	/*---------------------------------------- Register item render ----------------------------------------*/
	public static void registerRender(Item item, int metadata)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
