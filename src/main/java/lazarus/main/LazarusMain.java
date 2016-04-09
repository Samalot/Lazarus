/*Imports*/
package lazarus.main;
import lazarus.main.proxy.CommonProxy;
import lazarus.network.PacketDispatcher;
import lazarus.potions.Collapse;
import lazarus.utils.config.Config;
import lazarus.utils.config.Reference;
import lazarus.utils.handlers.LazarusEventHooks;
import lazarus.utils.handlers.MainEventHandler;
import lazarus.utils.handlers.PotionHandler;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/*Main*/
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class LazarusMain 
{
	@Mod.Instance(Reference.MOD_ID) /* Instance */
	public static LazarusMain instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS) /* Setup proxies */
	public static CommonProxy proxy; 
	
	public static final LazarusTab tabLazarus = new LazarusTab("tabLazarus"); /* Creative tab */
	public static Configuration lazarusConfig; /* Config */
	
	private static int modGuiIndex = 0; /* This is used to keep track of GUIs that we make */
	public static final int GUI_ITEM_INV = modGuiIndex++; /* Custom GUI indices: */
	
	
	/*---------------------------------------- Pre Initialisation ----------------------------------------*/
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		lazarusConfig = new Configuration(event.getSuggestedConfigurationFile());
		lazarusConfig.load(); /*Load the config*/
		Config.load(lazarusConfig);
		
		LazarusItems.init(); /*Create items*/
		LazarusItems.register(); /*Register items*/
		MinecraftForge.EVENT_BUS.register(new MainEventHandler()); /*Register event handler*/
		FMLCommonHandler.instance().bus().register(new MainEventHandler());
		
		Potion[] potiontypes = PotionHandler.initPotion();
		
		PacketDispatcher.registerPackets(); /*Register packet dispatcher*/
	}
	
	/*---------------------------------------- Initialisation ----------------------------------------*/
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		
		PotionHandler.potionCreate();
	}
	
	/*---------------------------------------- Post Initialisation ----------------------------------------*/
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){}
}
