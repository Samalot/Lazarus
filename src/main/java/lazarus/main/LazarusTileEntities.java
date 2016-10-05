package lazarus.main;

import lazarus.blocks.infusatron.TileEntityInfusatron;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class LazarusTileEntities {

	
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityInfusatron.class, "tileEntityInfusatron"); 
	}
	
}
