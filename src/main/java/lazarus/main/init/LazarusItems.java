/*Imports*/
package lazarus.main.init;
import items.Amsollions_token;
import lazarus.main.LazarusMod;
import lazarus.main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/*Main*/
public class LazarusItems {
	
	/*Items variables*/
	public static Item amsollions_token;
	
	/*Init items*/
	public static void init(){
		amsollions_token = new Amsollions_token("amsollions_token");
	}
	
	/*Register items*/
	public static void register(){
		GameRegistry.registerItem(amsollions_token, amsollions_token.getUnlocalizedName().substring(5));
	}
	
	/*Register item renders*/
	public static void registerRenders(){
		registerRender(amsollions_token);
	}
	
	/*Register item render*/
	public static void registerRender(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
