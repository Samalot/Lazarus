/**=============== Imports ===============**/
package lazarus.main;
import lazarus.main.proxy.CommonProxy;
import lazarus.packets.toClient.StringPacketClient;
import lazarus.packets.toServer.StringPacketServer;
import lazarus.utilities.Reference;
import lazarus.utilities.events.BlockEventHandler;
import lazarus.utilities.events.MainEventHandler;
import lazarus.utilities.events.PlayerInstanceHandler;
import lazarus.utilities.events.PlayerRenderHandler;
import lazarus.utilities.handlers.PotionHandler;
import net.minecraft.potion.Potion;
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
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**=============== Main ===============**/
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class LazarusMain 
{
	@Mod.Instance(Reference.MOD_ID) /* Instance */
	public static LazarusMain instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS) /* Setup proxies */
	public static CommonProxy proxy; 
	public static final LazarusTab tabLazarus = new LazarusTab("tabLazarus"); /* Creative tab */

	public static SimpleNetworkWrapper network; 
	
	/*---------------------------------------- GUI ----------------------------------------*/
	private static int modGuiIndex = 0; /* This is used to keep track of GUIs that we make */
	public static final int GUI_TOKEN_POUCH = modGuiIndex++; /* Custom GUI indices: */
	public static final int GUI_ITEM_INFO = modGuiIndex++; /* Custom GUI indices: */
	public static final int GUI_INFUSATRON = modGuiIndex++;

	/*---------------------------------------- Pre Initialisation ----------------------------------------*/
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{		
		LazarusBlocks.init(); /*Create blocks*/
		LazarusBlocks.register(); /*Register blocks*/
		LazarusBlocks.craftingRecipes(); /*Register crafting recipes*/
		LazarusItems.init(); /*Create items*/
		LazarusItems.register(); /*Register items*/
		LazarusTileEntities.init();
		proxy.init();
		
		network = NetworkRegistry.INSTANCE.newSimpleChannel("lazarusChannel");
		network.registerMessage(StringPacketServer.StringPacketHandler.class, StringPacketServer.class, 0, Side.SERVER);
		network.registerMessage(StringPacketClient.StringPacketHandler.class, StringPacketClient.class, 1, Side.CLIENT);

		MinecraftForge.EVENT_BUS.register(new MainEventHandler()); /*Register event handler*/
		MinecraftForge.EVENT_BUS.register(new PlayerRenderHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInstanceHandler());
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());

		FMLCommonHandler.instance().bus().register(new MainEventHandler());
		FMLCommonHandler.instance().bus().register(new PlayerRenderHandler());
		FMLCommonHandler.instance().bus().register(new PlayerInstanceHandler());
		FMLCommonHandler.instance().bus().register(new BlockEventHandler());

		Potion[] potiontypes = PotionHandler.initPotion();/*Load custom potion effects*/

	}

	/*---------------------------------------- Initialisation ----------------------------------------*/
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.registerRenders(); /*Register items in proxy*/
		proxy.registerRenderers(); /*Register entities in proxy*/
		LazarusEntities.register(this); /*Register Entities main*/

		NetworkRegistry.INSTANCE.registerGuiHandler(LazarusMain.instance, proxy);  /*Register GUI Handler*/

		PotionHandler.potionCreate();/*Create potions*/
	}

	/*---------------------------------------- Post Initialisation ----------------------------------------*/
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
