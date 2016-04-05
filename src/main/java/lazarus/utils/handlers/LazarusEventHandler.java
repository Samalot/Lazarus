/*Imports*/
package lazarus.utils.handlers;
import lazarus.utils.whispers.Amsollion_Whispers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
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
		if(event.item.getDisplayName().toString().contains("item.item.amsollions_token")){
			String whisperTextTemp = Amsollion_Whispers.randomWhsiper();
			String whisperText = "";
			String[] whisperTextSplit = whisperTextTemp.split(" ");
			for(String element : whisperTextSplit){whisperText += "§8§o";whisperText += element;whisperText+=" ";}
			IChatComponent whisper = new ChatComponentText(whisperText);
			SoundHandler.lazarusPlaySound("mob.wither.idle", 0.1F, 0.1F);
			event.entityPlayer.addChatMessage(whisper);
		}	
	}
	
}
