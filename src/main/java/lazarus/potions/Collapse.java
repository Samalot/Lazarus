/*Imports*/
package lazarus.potions;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class Collapse extends Potion {
	
	public Collapse(int par1, ResourceLocation icon,boolean par2, int par3) 
	{
		super(par1, null, par2, par3);
		this.setPotionName("potion.collapse");
	}
	
	public Potion setIconIndex(int par1, int par2) 
	{
		super.setIconIndex(par1, par2);
		return this;
	}
}