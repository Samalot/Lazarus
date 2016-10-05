/*Imports*/
package lazarus.main;

import lazarus.entity.EntityOsmodeus;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/*Main*/
public class LazarusEntities {
	/*Register*/
	public static void register(Object instance)
	{
		
		//Entities
		registerEntity(EntityOsmodeus.class, "Osmodeus", 0x888888, 0x6d3b6c, instance);
	}
	
	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor, Object instance)
	{
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id, bkEggColor, fgEggColor);
		EntityRegistry.registerModEntity(entityClass, entityName, id, instance, 80, 3, true);
	}
}
