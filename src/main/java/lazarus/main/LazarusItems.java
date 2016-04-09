/*Imports*/
package lazarus.main;
import lazarus.items.AbyssalPearl;
import lazarus.items.AmplifyingToken;
import lazarus.items.BaseItem;
import lazarus.items.DormantToken;
import lazarus.items.GildedToken;
import lazarus.items.QuellingToken;
import lazarus.items.TokenPouch;
import lazarus.items.WaningToken;
import lazarus.utils.config.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/*Main*/
public class LazarusItems 
{
	
	/*---------------------------------------- Items variables ----------------------------------------*/
	public static DormantToken dormant_token;
	public static GildedToken gilded_token;
	public static WaningToken waning_token;
	public static AmplifyingToken amplifying_token;
	public static QuellingToken quelling_token;
	
	public static AbyssalPearl abyssal_pearl;
	
	public static TokenPouch token_pouch;
	
	
	/*---------------------------------------- Init items ----------------------------------------*/
	public static void init()
	{
		dormant_token = new DormantToken();
		gilded_token = new GildedToken();
		waning_token = new WaningToken();
		amplifying_token = new AmplifyingToken();
		quelling_token = new QuellingToken();
		
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
