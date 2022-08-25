package com.github.soramame0256.lorereplacer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

import static com.github.soramame0256.lorereplacer.LoreReplacer.dataUtils;

public class CmdAddReplacer extends CommandBase {

    @Override
    public String getName() {
        return "addreplacer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/addreplacer <regex> <to>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 2){
            String regex = args[0];
            String to = args[1];
            regex = regex.replaceAll("#s", " ");
            to = to.replaceAll("#s", " ");
            if (!dataUtils.getRootJson().has("replacers")) dataUtils.getRootJson().add("replacers", new JsonArray());
            JsonArray ja = dataUtils.getRootJson().getAsJsonArray("replacers");
            JsonObject jo = new JsonObject();
            jo.addProperty("from", regex);
            jo.addProperty("to", to);
            ja.add(jo);
            dataUtils.getRootJson().add("replacers", ja);
            try {
                dataUtils.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(new TextComponentString("regex\"" + regex + "\"を\"" + to + "\"で置換します。"));
        }
    }
}
