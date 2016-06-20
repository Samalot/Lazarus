/*Imports*/
package lazarus.utilities.events;
import java.util.HashMap;
import java.util.Map;

import lazarus.items.tokens.AmplifyingToken;
import lazarus.items.tokens.CowardiceToken;
import lazarus.potions.Collapse;
import lazarus.utilities.handlers.InventoryHandler;
import lazarus.utilities.handlers.PotionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class PlayerInstanceHandler {
	/*---------------------------------------- Global variables ----------------------------------------*/
	public static Map currentPlayer = new HashMap<String, EntityPlayer>();
	public static EntityPlayer mpPlayer;

	/*---------------------------------------- Player update  ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerUpdate(PlayerTickEvent event) 
	{	
		/*Collect the world.*/
		World world = Minecraft.getMinecraft().theWorld;
		/*If player has collapse*/
		if(event.phase == Phase.START && event.player.isPotionActive(PotionHandler.collapse)){Collapse.applyEffects(event.player, world);}	
	}
	
	/*---------------------------------------- Listen for players joining ----------------------------------------*/
	@SubscribeEvent
	public void getServerPlayer(PlayerEvent.PlayerLoggedInEvent event)
	{
		World world = event.player.worldObj; //Word object from event.
		EntityPlayer mpPlayer = event.player;
		currentPlayer.put(mpPlayer.getUniqueID().toString(), mpPlayer);
	}

	/*---------------------------------------- Listen for players leaving ----------------------------------------*/
	@SubscribeEvent
	public void getServerPlayer(PlayerEvent.PlayerLoggedOutEvent event)
	{
		EntityPlayer mpPlayer = event.player;
		currentPlayer.remove(mpPlayer.getUniqueID().toString());
	}
	
}
