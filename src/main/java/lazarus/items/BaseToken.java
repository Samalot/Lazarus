/**========== Imports ==========**/
package lazarus.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**========== Main ==========**/
public class BaseToken extends BaseItem{	
	/*---------------------------------------- Variables ----------------------------------------*/
	public List<Double> amplifiers=Arrays.asList(0.00, 0.00, 0.00, 0.00, 0.00, 0.00);
	
	/*---------- Constructor ----------*/
	public BaseToken(String name)
	{
		super(name);
	}
		
	/*---------------------------------------- Make it shiny ----------------------------------------*/
	public boolean hasEffect(ItemStack par1ItemStack){return true;}
	
	/*---------------------------------------- Get amplifiers ----------------------------------------*/
	public List<Double> getAmplifiers()
	{return amplifiers;}
	
	@SideOnly(Side.CLIENT)
	public boolean isFull3D(){return true;}
	
}
