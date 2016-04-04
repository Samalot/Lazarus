/*Imports*/
package lazarus.main;
import lazarus.main.init.LazarusItems;
import lazarus.main.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class LazarusMod {
	
	
	/*Setup proxies*/
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy; 
	
	/*Pre Initialisation*/
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		LazarusItems.init();
		LazarusItems.register();
	}
	
	/*Initialisation*/
	@EventHandler
	public void Init(FMLInitializationEvent event){
		proxy.registerRenders();	
	}
	
	/*Post Initialisation*/
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}
