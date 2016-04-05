/*Imports*/
package lazarus.main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/*Main*/
public class LazarusTab extends CreativeTabs{

	/*Constructor*/
	public LazarusTab(String label) {
		super(label);
		this.setBackgroundImageName("LazarusTab.png");
	}

	/*Icon for tab*/
	@Override
	public Item getTabIconItem() {
		return LazarusItems.amsollions_token;
	}

}
