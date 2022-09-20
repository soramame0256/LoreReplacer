package com.github.soramame0256.lorereplacer.command;

import com.github.soramame0256.lorereplacer.LoreReplacer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CmdReloadReplacer extends CommandBase {

    @Override
    public String getName() {
        return "reloadreplacer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/reloadreplacer";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        LoreReplacer.dataUtils.reloadJsonFromFile();
        sender.sendMessage(new TextComponentString("リロードしました。"));
    }
}
