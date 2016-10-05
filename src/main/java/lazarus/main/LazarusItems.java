/*Imports*/
package lazarus.main;
import lazarus.items.BaseItem;
import lazarus.items.other.AbyssalPearl;
import lazarus.items.other.TokenPouch;
import lazarus.items.tokens.AmplifyingToken;
import lazarus.items.tokens.CowardiceToken;
import lazarus.items.tokens.DormantToken;
import lazarus.items.tokens.GildedToken;
import lazarus.items.tokens.QuellingToken;
import lazarus.items.tokens.WaningToken;
import lazarus.utilities.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;

/*Main*/
public class LazarusItems 
{
	
	/*---------------------------------------- Items variables ----------------------------------------*/
	public static BaseItem dormant_token;
	public static BaseItem gilded_token;
	public static BaseItem waning_token;
	public static BaseItem amplifying_token;
	public static BaseItem quelling_token;
	public static BaseItem cowardice_token;
	
	public static BaseItem abyssal_pearl;
	
	public static BaseItem token_pouch;
	
	
	/*---------------------------------------- Init items ----------------------------------------*/
	public static void init()
	{
		dormant_token = new DormantToken();
		gilded_token = new GildedToken();
		waning_token = new WaningToken();
		amplifying_token = new AmplifyingToken();
		quelling_token = new QuellingToken();
		cowardice_token = new CowardiceToken();
		
		abyssal_pearl = new AbyssalPearl();
		
		token_pouch = new TokenPouch();
	}
	
	/*---------------------------------------- Register items ----------------------------------------*/
	public static void register()
	{
		GameRegistry.registerItem(dormant_token, dormant_token.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(gilded_token, gilded_token.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(waning_token, waning_token.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(amplifying_token, amplifying_token.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(quelling_token, quelling_token.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cowardice_token, cowardice_token.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(abyssal_pearl, abyssal_pearl.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(token_pouch, token_pouch.getUnlocalizedName().substring(5));
	}
	
	/*---------------------------------------- Register item renders ----------------------------------------*/
	public static void registerRenders()
	{
		registerRender(dormant_token, 0, "");
		registerRender(gilded_token, 0, "");
		registerRender(waning_token, 0, "");
		registerRender(amplifying_token, 0, "");
		registerRender(quelling_token, 0, "");
		registerRender(cowardice_token, 0, "");
		
		registerRender(abyssal_pearl, 0, "");
		
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
