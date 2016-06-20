/*Imports*/
package lazarus.utilities.events;

import org.lwjgl.opengl.GL11;

import lazarus.utilities.handlers.PotionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class PlayerRenderHandler{
	/*---------------------------------------- Variables ----------------------------------------*/
	private static double fogDepth = 0;
	int resetFlagDensity = 0;
	int resetFlagColour = 0;
	
	/*For FOV effect*/
	int FOVdirectionFlag = 0;
	float magnification = 1.0F;
	int pulseComplete = 0;
	int pulse1 = 0;
	int pulse2 = 0;
	long start_time = System.nanoTime();
	
	/*---------------------------------------- Fog Colour ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFogColors(FogColors event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer; 
		
		if (player.isPotionActive(PotionHandler.collapse) || fogDepth > 0) {
			event.red = 36F/255F;
			event.green = 18F/255F;
	        event.blue = 52F/255F;
		}
	}
	
	/*---------------------------------------- Fog Density ----------------------------------------*/
	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onFogDensity(FogDensity event)
    {
	    EntityPlayer player = Minecraft.getMinecraft().thePlayer;    
		if(player.isPotionActive(PotionHandler.collapse))
		{
			resetFlagDensity = 1;
			if(fogDepth < 0.2){fogDepth += 0.00005; fogDepth = Math.round(fogDepth * 100000.0) / 100000.0;}
	        event.density = (float) fogDepth;
	        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
	        event.setCanceled(true); // must be canceled to affect the fog density   		
		}
		else if(fogDepth > 0)
		{
			if(fogDepth > 0){fogDepth -= 0.0005; fogDepth = Math.round(fogDepth * 100000.0) / 100000.0;} 
			event.density = (float) fogDepth;
	        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
	        resetFlagDensity = 0;
	        event.setCanceled(true); // must be canceled to affect the fog density   
		}
	}
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onCameraPosition(CameraSetup event)
	{
		//System.out.println(event.roll);
		//event.roll = testRoll;
		//event.pitch = testRoll;
		//event.yaw = testRoll;
		//testRoll += 1;
	}
	
    @SubscribeEvent()
    @SideOnly(Side.CLIENT)
	public void onFOV(FOVUpdateEvent event)
	{
    	EntityPlayer player = event.entity;    
 		if(player.isPotionActive(PotionHandler.collapse))
 		{	
 			if(pulseComplete == 0)
 			{
	 			if(FOVdirectionFlag == 0)
	 			{
	 				if(magnification>0.9){magnification-=0.05;}
	 				else{magnification = 0.9F; FOVdirectionFlag = 1; pulse1 = 1;}
	 			}
	 			if(FOVdirectionFlag == 1)
	 			{
	 				if(magnification<1){magnification+=0.05;}
	 				else{magnification = 1.0F; FOVdirectionFlag = 0; pulse2 = 1;}
	 			}
	 			
	 			if(pulse1 == 1 && pulse2 == 1){pulse1 = 0; pulse2 = 0; pulseComplete = 1; start_time = System.nanoTime();}	
 			}
 			else{
 				if(System.nanoTime()-start_time > 500000000){pulseComplete = 0;}
 				}
 			
 		}
 		else{magnification = 1.0F;}
 		
 		event.newfov = event.fov*magnification;
	}
}
