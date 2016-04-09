/*Imports*/
package lazarus.utils.handlers;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.round;

import org.lwjgl.opengl.GL11;

import lazarus.main.LazarusItems;
import lazarus.utils.whispers.AmsollionWhispers;
import lazarus.utils.whispers.ImbrasWhispers;
import lazarus.utils.whispers.OsmodeusWhispers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class MainEventHandler {
	/*---------------------------------------- Global variables ----------------------------------------*/
	public static int globalFlag_Token_Pouch_Open = 0;
	private static double fogDepth = 0;
	
	/*---------------------------------------- Listen for guiOpens ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void openGui(GuiOpenEvent event)
	{	
			/*No GUI*/
			if(event.gui == null)
			{globalFlag_Token_Pouch_Open = 0;}
			/*Token Pouch opened*/
			else if(event.gui.toString().contains("lazarus.container.token_pouch.GuiTokenPouch"))
			{globalFlag_Token_Pouch_Open = 1;}		
	}
	
	/*---------------------------------------- Listen for item pickup ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onItemPickup(EntityItemPickupEvent event){
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
			//SoundHandler.lazarusPlaySound("mob.wither.idle", 0.1F, 0.1F);
			event.entityPlayer.addChatMessage(whisper);
			trigger = false;
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
	
	/*---------------------------------------- Fog Density ----------------------------------------*/
	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(FogDensity event)
    {
	    EntityPlayer player = Minecraft.getMinecraft().thePlayer;    
		if(player.isPotionActive(PotionHandler.collapse))
		{

			if(fogDepth < 0.2){fogDepth += 0.00005;}
	        event.density = (float) fogDepth;
	        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
	        event.setCanceled(true); // must be canceled to affect the fog density   		
		}
		else
		{
			if(fogDepth > 0){fogDepth -= 0.00005;} 
			event.density = (float) 0;
	        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
	        event.setCanceled(true); // must be canceled to affect the fog density   
		}
	}
	
	/*---------------------------------------- Fog Colour ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFogColors(FogColors event) {
		
		
		int resetFlag = 0;
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;  
		if (player.isPotionActive(PotionHandler.collapse)) {
			resetFlag = 1;
			event.red = 36F/255F;
			event.green = 18F/255F;
	        event.blue = 52F/255F;
		}
		else
		{
			if(resetFlag == 1)
			{
				event.red = 0.71221614F;
				event.green = 0.81895226F;
		        event.blue = 0.99999833F;
		        resetFlag = 0;
			}
		}
	}
	
	/*---------------------------------------- TEMP ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerUpdate(PlayerTickEvent  event) 
	{
		if(event.phase == Phase.START && event.player.isPotionActive(PotionHandler.collapse))
		{
			if (event.player.getActivePotionEffect(PotionHandler.collapse).getDuration()==0) 
			{
				event.player.removePotionEffect(PotionHandler.collapse.id);
				return;
			}
		}
			
	}	
	
	/*---------------------------------------- Listen for explosions ----------------------------------------*/
	@SubscribeEvent
	public void onExplosion(ExplosionEvent event)
	{
		DamageSource quellingExplosion = new DamageSource("quellingExplosion");
		Vec3 explosionPos = event.explosion.getPosition();
		for(int i = 0; i < event.world.playerEntities.size(); i++)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.world.playerEntities.get(i);
			double x = round(abs(player.getPosition().getX() - explosionPos.xCoord));
			double y = round(abs(player.getPosition().getY() - explosionPos.yCoord));
			double z = round(abs(player.getPosition().getZ() - explosionPos.zCoord));
			if(player.inventory.hasItem(LazarusItems.quelling_token)
					&& x <= 5
					&& y <= 5
					&& z <= 5)
			{
				AxisAlignedBB blocks = new AxisAlignedBB(-5, -5, -5, 5, 5, 5);
				float damage = (event.world.getBlockDensity(explosionPos, blocks) * 20);
				player.attackEntityFrom(quellingExplosion, (float)floor(damage));
				event.explosion.doExplosionB(true);
				event.setCanceled(true);
			}
		}	
	}
	
	
}
