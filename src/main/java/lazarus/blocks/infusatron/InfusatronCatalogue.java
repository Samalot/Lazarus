/**=============== Imports ===============**/
package lazarus.blocks.infusatron;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import lazarus.items.BaseItem;
import lazarus.main.LazarusItems;

/**=============== Main ===============**/
public class InfusatronCatalogue {

	/*--------------- Variables ---------------*/
	public HashMap<BaseItem, List<BaseItem>> catalogue = new HashMap<BaseItem, List<BaseItem>>();

	/*--------------- Constructor ---------------*/	
	public InfusatronCatalogue()
	{
		//Dormant Token
		catalogue.put(LazarusItems.dormant_token, 
				Arrays.asList(
						LazarusItems.amplifying_token,
						LazarusItems.cowardice_token,
						LazarusItems.gilded_token,
						LazarusItems.quelling_token,
						LazarusItems.waning_token
						));
		//amplifying_token
		catalogue.put(LazarusItems.amplifying_token, 
				Arrays.asList(
						LazarusItems.dormant_token
						));
		//Gilded Token
		catalogue.put(LazarusItems.gilded_token, 
				Arrays.asList(
						LazarusItems.dormant_token
						));
		//cowardice_token
		catalogue.put(LazarusItems.cowardice_token, 
				Arrays.asList(
						LazarusItems.dormant_token
						));
		//quelling_token
		catalogue.put(LazarusItems.quelling_token, 
				Arrays.asList(
						LazarusItems.dormant_token
						));
		//waning_token
		catalogue.put(LazarusItems.waning_token, 
				Arrays.asList(
						LazarusItems.dormant_token
						));
	}

	/*--------------- Return possible infusions ---------------*/
	public List<BaseItem> getItems(BaseItem input)
	{
		if(catalogue.containsKey(input)){return catalogue.get(input);}
		return null;
	}

	/*--------------- Check for key ---------------*/
	public boolean hasKey(BaseItem input)
	{
		return (catalogue.containsKey(input)) ? true:false;
	}
}
