/*Imports*/
package lazarus.utils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

/*Main*/
public class SoundHandler {
	public static void lazarusPlaySound(String name, float pitch, float volume){
		World currentWorld = Minecraft.getMinecraft().theWorld; 
		EntityPlayerSP currentPlayer = Minecraft.getMinecraft().thePlayer; 
		currentWorld.playSound(
				currentPlayer.getPosition().getX(), 
				currentPlayer.getPosition().getY(),
				currentPlayer.getPosition().getZ(), 
				name, pitch, volume, false);
	}
}
