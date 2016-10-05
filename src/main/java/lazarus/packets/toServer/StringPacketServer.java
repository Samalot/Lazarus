//Created from diesieben07's tutorial. 

/**=============== Imports ===============**/
package lazarus.packets.toServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lazarus.interfaces.InterfaceLog;
import lazarus.interfaces.container.ContainerInfusatron;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**=============== Main ===============**/
public class StringPacketServer implements IMessage{

	/*--------------- Variables ---------------*/
	private String text;

	/*--------------- Blank Constructor ---------------*/
	public StringPacketServer(){}

	/*--------------- Main Constructor ---------------*/
	public StringPacketServer(String message)
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
	public static class StringPacketHandler implements IMessageHandler<StringPacketServer, IMessage> 
	{
		/*--------------- Triggered on message ---------------*/
		@Override
		public IMessage onMessage(final StringPacketServer message, final MessageContext ctx) 
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj; // or Minecraft.getMinecraft() on the client
			
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					List<String> messageList = Arrays.asList(message.text.split("SPLIT"));
							
					if(messageList.get(0).equals("infusatronSearch"))
					{	
						
						String key = "D"+messageList.get(1)+"X"+messageList.get(2)+"Y"+messageList.get(3)+"Z"+messageList.get(4);
									
						ContainerInfusatron containerTarget = InterfaceLog.infusatronLog.get(key);	
						if(messageList.size()==6){containerTarget.setSearch(messageList.get(5));}
						else{containerTarget.setSearch("");}
						containerTarget.updateCatalogue();
					}	
					
					else if(messageList.get(0).equals("infusatronModeSet"))
					{	
						
						String key = "D"+messageList.get(1)+"X"+messageList.get(2)+"Y"+messageList.get(3)+"Z"+messageList.get(4);			
						ContainerInfusatron containerTarget = InterfaceLog.infusatronLog.get(key);	
						containerTarget.clearCatalogue();
						containerTarget.refersh();
						
						if(messageList.get(5).equals("true")){containerTarget.searchMode=true;containerTarget.updateCatalogue();}
						else if(messageList.get(5).equals("false")){containerTarget.searchMode=false;}
					}
				}
			});		
			return null; // no response in this case
		}
	}
}
