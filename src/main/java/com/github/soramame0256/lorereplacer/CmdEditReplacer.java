package com.github.soramame0256.lorereplacer;

import com.google.gson.JsonArray;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

import static com.github.soramame0256.lorereplacer.LoreReplacer.INFO_PREFIX;
import static com.github.soramame0256.lorereplacer.LoreReplacer.dataUtils;

public class CmdEditReplacer extends CommandBase {
    @Override
    public String getName() {
        return "editreplacer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/editreplacer <regex/to> <index> <変更先値>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 3){
            if (!dataUtils.getRootJson().has("replacers")) dataUtils.getRootJson().add("replacers", new JsonArray());
            JsonArray ja = dataUtils.getRootJson().getAsJsonArray("replacers");
            if(args[0].equals("regex")){
                int index = Integer.parseInt(args[1]);
                String regex = args[2].replaceAll("#s", " ").replaceAll("#c", "§");
                sender.sendMessage(new TextComponentString("通過テスト 引数: " + args[0] + " " + args[1] + " " + args[2]));
                if(ja.size()-1>=index){
                    ja.get(index).getAsJsonObject().addProperty("from", regex);
                    sender.sendMessage(new TextComponentString(INFO_PREFIX + "該当replacerの正規表現を\"" + regex + "§f\"に変更しました。"));
                }
            }
            else if(args[0].equals("to")){
                int index = Integer.parseInt(args[1]);
                String to = args[2].replaceAll("#s", " ").replaceAll("#c", "§");
                if(ja.size()-1>=index){
                    ja.get(index).getAsJsonObject().addProperty("to", to);
                    sender.sendMessage(new TextComponentString(INFO_PREFIX + "該当replacerの置換先テキストを\"" + to + "§f\"に変更しました。"));
                }
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
