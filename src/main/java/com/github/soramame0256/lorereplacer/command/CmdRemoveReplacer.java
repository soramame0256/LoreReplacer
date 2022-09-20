package com.github.soramame0256.lorereplacer.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            regex = regex.replaceAll("#s", " ").replaceAll("#c", "§").replaceAll("#n", "\n");
            if (!dataUtils.getRootJson().has("replacers")) dataUtils.getRootJson().add("replacers", new JsonArray());
            JsonArray ja = dataUtils.getRootJson().getAsJsonArray("replacers");
            List<JsonElement> toRemove = new ArrayList<>();
            for(JsonElement je : dataUtils.getRootJson().getAsJsonArray("replacers")){
                if(je.getAsJsonObject().get("from").getAsString().equals(regex)) {
                    toRemove.add(je);
                }
            }
            for(JsonElement je : toRemove){
                sender.sendMessage(new TextComponentString(INFO_PREFIX + "\"" + je.getAsJsonObject().get("to").getAsString() + "§f\"に置換される該当replacerを削除しました。"));
                ja.remove(je);
            }
            dataUtils.getRootJson().add("replacers", ja);
            try {
                dataUtils.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(args.length == 2){
            if(args[0].equals("ashash")){
                if (!dataUtils.getRootJson().has("replacers")) dataUtils.getRootJson().add("replacers", new JsonArray());
                JsonArray ja = dataUtils.getRootJson().getAsJsonArray("replacers");
                List<JsonElement> toRemove = new ArrayList<>();
                MessageDigest md5 = null;
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if(md5 == null) return;
                for(JsonElement je : dataUtils.getRootJson().getAsJsonArray("replacers")){
                    byte[] hashedText = md5.digest((je.getAsJsonObject().get("from").getAsString() + je.getAsJsonObject().get("to").getAsString()).getBytes());
                    if(args[1].equals(String.format("%020x", new BigInteger(1, hashedText)))){
                        toRemove.add(je);
                    }
                }
                for(JsonElement je : toRemove){
                    sender.sendMessage(new TextComponentString(INFO_PREFIX + "\"" + je.getAsJsonObject().get("to").getAsString() + "§f\"に置換される該当replacerを削除しました。"));
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
}
