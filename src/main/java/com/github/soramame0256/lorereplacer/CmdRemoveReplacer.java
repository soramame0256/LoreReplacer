package com.github.soramame0256.lorereplacer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.soramame0256.lorereplacer.LoreReplacer.INFO_PREFIX;
import static com.github.soramame0256.lorereplacer.LoreReplacer.dataUtils;

public class CmdRemoveReplacer extends CommandBase {

    @Override
    public String getName() {
        return "removereplacer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "removereplacer <regex>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1){
            String regex = args[0];
            regex = regex.replaceAll("#s", " ").replaceAll("#c", "§");
            if (!dataUtils.getRootJson().has("replacers")) dataUtils.getRootJson().add("replacers", new JsonArray());
            JsonArray ja = dataUtils.getRootJson().getAsJsonArray("replacers");
            List<JsonElement> toRemove = new ArrayList<>();
            for(JsonElement je : dataUtils.getRootJson().getAsJsonArray("replacers")){
                if(je.getAsJsonObject().get("from").getAsString().equals(regex)) {
                    toRemove.add(je);
                }
            }
            for(JsonElement je : toRemove){
                sender.sendMessage(new TextComponentString(INFO_PREFIX + "\"" + je.getAsJsonObject().get("to").getAsString() + "\"に置換される該当replacerを削除しました。"));
                ja.remove(je);
            }
            dataUtils.getRootJson().add("replacers", ja);
            try {
                dataUtils.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
