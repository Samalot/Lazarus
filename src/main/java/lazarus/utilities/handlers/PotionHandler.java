/*Imports*/
package lazarus.utilities.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lazarus.potions.Collapse;
import lazarus.utilities.Reference;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

/*Main*/
public class PotionHandler {
	/*---------------------------------------- Variables ----------------------------------------*/
	public static Potion collapse;
	private static Potion[] potionTypes = null;

	/*---------------------------------------- Initialisation ----------------------------------------*/
	public static Potion[] initPotion()
	{
		for (Field f : Potion.class.getDeclaredFields()) 
		{
			f.setAccessible(true);
			try 
			{
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) 
				{
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Exception e) {System.err.println("Severe error, please report this to the mod author:");System.err.println(e);}
		}
		return potionTypes;
	}
	
	/*---------------------------------------- Register potions ----------------------------------------*/
	public static void potionCreate()
	{
		ResourceLocation res = new ResourceLocation("lazarus:textures/potion/collapse.png"); 
		collapse = (new Collapse(32, res,true, 0)).setIconIndex(0, 0).setPotionName("potion.collapse");
	}
}
