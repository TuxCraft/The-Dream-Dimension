package com.github.ubiquitousspice.dreamdimension.command;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import com.github.ubiquitousspice.dreamdimension.handlers.DreamManager;
import com.github.ubiquitousspice.dreamdimension.handlers.DreamerData;
import com.github.ubiquitousspice.dreamdimension.command.ICommandSender;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandTimeLeft extends CommandBase 
{

    @Override
    public String getCommandName()
    {
        return "timeleft";
    }

   

	@Override
	public String getCommandUsage(
			net.minecraft.command.ICommandSender icommandsender) {
	
		return "timeleft: to be used to find the amount of time you have left.";
		
	}
	
    private static final HashMap<String, DreamerData> dreamers = new HashMap<String, DreamerData>();

public void DreamerData(EntityPlayerMP player){}
@Override
public void processCommand(net.minecraft.command.ICommandSender icommandsender,
		String[] astring) {
	// TODO Auto-generated method stub
	
	if (icommandsender instanceof EntityPlayer)
	{
		EntityPlayerMP player = (EntityPlayerMP) icommandsender;
		DreamerData data = new DreamerData(player);
		icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("there are " + data.timeLeft/20 + " seconds Left till you wake up..."));
	}
	
}






}


