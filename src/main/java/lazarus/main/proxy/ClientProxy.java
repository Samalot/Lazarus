/*Imports*/
package lazarus.main.proxy;

import lazarus.main.init.LazarusItems;

/*Main*/
public class ClientProxy extends CommonProxy{
	/*Register the renders*/
	@Override
	public void registerRenders(){
		LazarusItems.registerRenders();
	}
}
