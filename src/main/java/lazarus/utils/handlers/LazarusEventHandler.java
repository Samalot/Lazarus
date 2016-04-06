/*Imports*/
package lazarus.utils.handlers;
import lazarus.main.LazarusItems;
import lazarus.utils.whispers.AmsollionWhispers;
import lazarus.utils.whispers.ImbrasWhispers;
import lazarus.utils.whispers.OsmodeusWhispers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*Main*/
public class LazarusEventHandler {
	/*---------------------------------------- Global variables ----------------------------------------*/
	public static int globalFlag_Token_Pouch_Open = 0;
	
	/*---------------------------------------- Listen for guiOpens ----------------------------------------*/
	@SubscribeEvent
	public void openGui(GuiOpenEvent event)
	{	
			/*No GUI*/
			if(event.gui == null)
			{
				globalFlag_Token_Pouch_Open = 0;
			}
			/*Token Pouch opened*/
			else if(event.gui.toString().contains("lazarus.container.token_pouch.GUI_Token_Pouch"))
			{globalFlag_Token_Pouch_Open = 1;}		
	}
	
	/*---------------------------------------- Listen for item pickup ----------------------------------------*/
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event){
		/*Amsollions Token*/
		
		String whisperTextTemp = "";
		boolean trigger = false;
		
		
		if(event.item.getDisplayName().toString().contains("item.item.gilded_token")){whisperTextTemp = AmsollionWhispers.randomWhsiper();trigger = true;}
		if(event.item.getDisplayName().toString().contains("item.item.waning_token")){whisperTextTemp = OsmodeusWhispers.randomWhsiper();trigger = true;}
		if(event.item.getDisplayName().toString().contains("item.item.amplifying_token")){whisperTextTemp = ImbrasWhispers.randomWhsiper();trigger = true;}
		
		if(trigger)
		{
			String whisperText = "";
			String[] whisperTextSplit = whisperTextTemp.split(" ");
			for(String element : whisperTextSplit){whisperText += "§8§o";whisperText += element;whisperText+=" ";}
			IChatComponent whisper = new ChatComponentText(whisperText);
			SoundHandler.lazarusPlaySound("mob.wither.idle", 0.1F, 0.1F);
			event.entityPlayer.addChatMessage(whisper);
		}
		
		
	}
	
	/*---------------------------------------- Listen entity taking damage ----------------------------------------*/
	@SubscribeEvent
	public void onEntityGetHurt(LivingHurtEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			/*Multiplied damgage taken by the player*/
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if(player.inventory.hasItem(LazarusItems.amplifying_token)){event.ammount *= 100;}
		}
		else if(event.source.getEntity() instanceof EntityPlayer)
		{
			/*Multiplied damgage dealt by the player*/
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			if(player.inventory.hasItem(LazarusItems.amplifying_token)){event.ammount *= 100;}
		}
		
		}
	
	
}
