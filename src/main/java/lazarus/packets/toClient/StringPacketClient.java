//Created from diesieben07's tutorial. 

/**=============== Imports ===============**/
package lazarus.packets.toClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lazarus.blocks.infusatron.TileEntityInfusatron;
import lazarus.interfaces.InterfaceLog;
import lazarus.interfaces.container.ContainerInfusatron;
import lazarus.interfaces.guis.GuiInfusatron;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**=============== Main ===============**/
public class StringPacketClient implements IMessage{

	/*--------------- Variables ---------------*/
	private String text;

	/*--------------- Blank Constructor ---------------*/
	public StringPacketClient(){}

	/*--------------- Main Constructor ---------------*/
	public StringPacketClient(String message)
	{
		this.text = message;	
	}

	/*--------------- Convert FROM Bytes ---------------*/
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		text = ByteBufUtils.readUTF8String(buf); 
	}

	/*--------------- Convert TO Bytes ---------------*/
	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeUTF8String(buf, text);
	}

	/**=============== Sub-Class Handler ===============**/
	public static class StringPacketHandler implements IMessageHandler<StringPacketClient, IMessage> 
	{
		/*--------------- Triggered on message ---------------*/
		@Override
		public IMessage onMessage(final StringPacketClient message, final MessageContext ctx) 
		{
			IThreadListener mainThread = Minecraft.getMinecraft(); // or Minecraft.getMinecraft() on the client

			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {

					List<String> messageList = Arrays.asList(message.text.split("SPLIT"));
					
					System.out.println(messageList);
					
					String key = "D"+messageList.get(1)+"X"+messageList.get(2)+"Y"+messageList.get(3)+"Z"+messageList.get(4);
					if(messageList.get(0).equals("selectedItem"))
					{			
						if(InterfaceLog.infusatronGUILog.containsKey(key))
						{
							GuiInfusatron containerTarget = InterfaceLog.infusatronGUILog.get(key);	
							containerTarget.seletedItem = messageList.get(5);
						}
					}	
					if(messageList.get(0).equals("selectedItemReset"))
					{
						if(InterfaceLog.infusatronGUILog.containsKey(key))
						{
							GuiInfusatron containerTarget = InterfaceLog.infusatronGUILog.get(key);	
							containerTarget.seletedItem = "";
							containerTarget.searchMode = true;		
							TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(new BlockPos(Integer.valueOf(messageList.get(2)),Integer.valueOf(messageList.get(3)),Integer.valueOf(messageList.get(4))));	
							InterfaceLog.infusatronModeClient.put((TileEntityInfusatron)tileEntity, true);
							
						}
					}

				}
			});		
			return null; // no response in this case
		}
	}
}
