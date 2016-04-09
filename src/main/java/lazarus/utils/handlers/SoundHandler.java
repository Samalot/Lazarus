/*Imports*/
package lazarus.utils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/*Main*/
public class SoundHandler {
	
	private World currentWorld;
	private EntityPlayer currentPlayer;
	private String name;
	private float pitch;
	private float volume;
	
	public SoundHandler(String name, float pitch, float volume)
	{
		this.name = name;
		this.pitch = pitch;
		this.volume = volume;
		lazarusPlaySound(name, pitch, volume);
	}
	
	public void lazarusPlaySound(String name, float pitch, float volume){
		currentWorld.playSound(
				currentPlayer.getPosition().getX(), 
				currentPlayer.getPosition().getY(),
				currentPlayer.getPosition().getZ(), 
				name, pitch, volume, false);
	}
}
