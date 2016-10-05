/**=============== Imports ===============**/
package lazarus.interfaces;

import java.util.HashMap;
import java.util.Map;

import lazarus.blocks.infusatron.TileEntityInfusatron;
import lazarus.interfaces.container.ContainerInfusatron;
import lazarus.interfaces.guis.GuiInfusatron;
import net.minecraft.entity.player.EntityPlayer;

/**=============== Main ===============**/
public class InterfaceLog {
	/*--------------- Variables ---------------*/
	public int infusatronID = 0;
	
	public static Map<String, ContainerInfusatron> infusatronLog = new HashMap<String, ContainerInfusatron>();
	public static Map<String, GuiInfusatron> infusatronGUILog = new HashMap<String, GuiInfusatron>();
	
	public static Map<TileEntityInfusatron, Boolean> infusatronModeClient= new HashMap<TileEntityInfusatron, Boolean>();
	
	
	
	
	
}
