package ru.glitchless.tpmod.cmds;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class SetSpawnCommand extends CommandBase {
    @Override
    public String getName() {
        return "setspawn";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Use /setspawn to set spawn location.";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer && sender.canUseCommand(2, "");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) sender;
            sender.getEntityWorld().setSpawnPoint(entityPlayer.getPosition());
            sender.sendMessage(new TextComponentString("Spawn set."));
        }
    }
}
