/*Imports*/
package lazarus.main;
import com.ibm.icu.impl.duration.impl.Utils;

import lazarus.main.init.LazarusItems;
import lazarus.main.proxy.CommonProxy;
import lazarus.utils.Config;
import lazarus.utils.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/*Main*/
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class LazarusMod 
{
	
	/*---------------------------------------- Setup proxies ----------------------------------------*/
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy; 
	
	/*---------------------------------------- Creative tab ----------------------------------------*/
	public static final LazarusTab tabLazarus = new LazarusTab("tabLazarus");
	
	/*---------------------------------------- Config ----------------------------------------*/
	public static Configuration lazarusConfig;
	
	/*---------------------------------------- Pre Initialisation ----------------------------------------*/
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		lazarusConfig = new Configuration(event.getSuggestedConfigurationFile());
		lazarusConfig.load(); /*Load the config*/
		Config.load(lazarusConfig);
		LazarusItems.init(); /*Create items*/
		LazarusItems.register(); /*Register items*/
	}
	
	/*---------------------------------------- Initialisation ----------------------------------------*/
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.registerRenders();	
	}
	
	/*---------------------------------------- Post Initialisation ----------------------------------------*/
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){}
}
