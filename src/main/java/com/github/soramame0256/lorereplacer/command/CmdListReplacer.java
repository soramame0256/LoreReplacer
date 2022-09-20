package com.github.soramame0256.lorereplacer.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.github.soramame0256.lorereplacer.LoreReplacer.INFO_PREFIX;
import static com.github.soramame0256.lorereplacer.LoreReplacer.dataUtils;

public class CmdListReplacer extends CommandBase {
    private static final String REGEX_LIST_NAME = " 正規表現 ";
    private static final String TO_LIST_NAME = " 置換先 ";
    @Override
    public String getName() {
        return "listreplacer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/listreplacer";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!dataUtils.getRootJson().has("replacers")) dataUtils.getRootJson().add("replacers", new JsonArray());
        JsonArray ja = dataUtils.getRootJson().getAsJsonArray("replacers");
        ITextComponent tc = new TextComponentString(INFO_PREFIX);
        ITextComponent toJoin;
        JsonObject jo;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md5 == null) return;
        int regexlt = 0, tolt = 0, regexl = 0, tol = 0, regexbl = 0, tobl = 0, janum = 0;
        String regex, to, shownRegex, shownTo;
        regexbl = REGEX_LIST_NAME.length();
        tobl = TO_LIST_NAME.length();
        if (args.length == 0) {
            tc.appendText("§7(ページ1)\n");
            for (JsonElement je : ja) {
                if(janum == 15) break;
                jo = je.getAsJsonObject();
                regex = jo.get("from").getAsString().replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                to = jo.get("to").getAsString().replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                regexl = regex.length();
                tol = to.length();
                if (regexlt < regexl) regexlt = regexl;
                if (tolt < tol) tolt = tol;
                janum++;
            }
            tc.appendText(REGEX_LIST_NAME);
            for (int i = 1; i <= regexlt - regexbl; i++) tc.appendText(" ");
            tc.appendText("|" + TO_LIST_NAME);
            for (int i = 1; i <= tolt - tobl; i++) tc.appendText(" ");
            tc.appendText("| 操作");
            janum = 0;
            for (JsonElement je : ja) {
                if(janum == 15) break;
                tc.appendText("\n");
                jo = je.getAsJsonObject();
                regex = jo.get("from").getAsString();
                to = jo.get("to").getAsString();
                shownRegex = regex.replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                shownTo = to.replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                regexl = shownRegex.length();
                tol = shownTo.length();
                tc.appendText(regex);
                for (int i = 1; i <= regexlt - regexl; i++) tc.appendText(" ");
                tc.appendText("§f | ");
                tc.appendText(to);
                for (int i = 1; i <= tolt - tol; i++) tc.appendText(" ");
                tc.appendText(" §f| ");
                ClickEvent tx = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/editreplacer regex " + janum + " " + shownRegex);
                Style st = new Style();
                toJoin = new TextComponentString("§b[r✎]");
                st.setClickEvent(tx);
                HoverEvent he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("正規表現の編集をします。"));
                st.setHoverEvent(he);
                st.setUnderlined(true);
                toJoin.setStyle(st);
                tc.appendSibling(toJoin);
                tc.appendText(" | ");

                tx = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/editreplacer to " + janum + " " + shownTo);
                st = new Style();
                toJoin = new TextComponentString("§b[t✎]");
                st.setClickEvent(tx);
                he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("置換先テキストの編集をします。"));
                st.setHoverEvent(he);
                st.setUnderlined(true);
                toJoin.setStyle(st);
                tc.appendSibling(toJoin);
                tc.appendText(" | ");

                byte[] hashedText = md5.digest((je.getAsJsonObject().get("from").getAsString() + je.getAsJsonObject().get("to").getAsString()).getBytes());
                tx = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/removereplacer ashash " + String.format("%020x", new BigInteger(1, hashedText)));
                st = new Style();
                toJoin = new TextComponentString("§c[x]");
                st.setClickEvent(tx);
                he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("このreplacerを削除します。"));
                st.setHoverEvent(he);
                st.setUnderlined(true);
                toJoin.setStyle(st);
                tc.appendSibling(toJoin);
                janum++;
            }
            sender.sendMessage(tc);
        }else if(args.length == 1){
            int page = Integer.parseInt(args[0]);
            tc.appendText("§7(ページ"+ page + ")\n");
            for (JsonElement je : ja) {
                if(janum < (page-1)*15) {
                    janum++;
                    continue;
                }
                if(janum == page*15) break;
                jo = je.getAsJsonObject();
                regex = jo.get("from").getAsString().replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                to = jo.get("to").getAsString().replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                regexl = regex.length();
                tol = to.length();
                if (regexlt < regexl) regexlt = regexl;
                if (tolt < tol) tolt = tol;
                janum++;
            }
            tc.appendText(REGEX_LIST_NAME);
            for (int i = 1; i <= regexlt - regexbl; i++) tc.appendText(" ");
            tc.appendText("|" + TO_LIST_NAME);
            for (int i = 1; i <= tolt - tobl; i++) tc.appendText(" ");
            tc.appendText("| 操作");
            janum = 0;
            for (JsonElement je : ja) {
                if(janum < (page-1)*15) {
                    janum++;
                    continue;
                }
                if(janum == page*15) break;
                tc.appendText("\n");
                jo = je.getAsJsonObject();
                regex = jo.get("from").getAsString();
                to = jo.get("to").getAsString();
                shownRegex = regex.replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                shownTo = to.replaceAll(" ", "#s").replaceAll("§", "#c").replaceAll("\n", "#n");
                regexl = shownRegex.length();
                tol = shownTo.length();
                tc.appendText(regex);
                for (int i = 1; i <= regexlt - regexl; i++) tc.appendText(" ");
                tc.appendText("§f | ");
                tc.appendText(to);
                for (int i = 1; i <= tolt - tol; i++) tc.appendText(" ");
                tc.appendText(" §f| ");
                ClickEvent tx = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/editreplacer regex " + janum + " " + shownRegex);
                Style st = new Style();
                toJoin = new TextComponentString("§b[r✎]");
                st.setClickEvent(tx);
                HoverEvent he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("正規表現の編集をします。"));
                st.setHoverEvent(he);
                st.setUnderlined(true);
                toJoin.setStyle(st);
                tc.appendSibling(toJoin);
                tc.appendText(" | ");

                tx = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/editreplacer to " + janum + " " + shownTo);
                st = new Style();
                toJoin = new TextComponentString("§b[t✎]");
                st.setClickEvent(tx);
                he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("置換先テキストの編集をします。"));
                st.setHoverEvent(he);
                st.setUnderlined(true);
                toJoin.setStyle(st);
                tc.appendSibling(toJoin);
                tc.appendText(" | ");

                byte[] hashedText = md5.digest((je.getAsJsonObject().get("from").getAsString() + je.getAsJsonObject().get("to").getAsString()).getBytes());
                tx = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/removereplacer ashash " + String.format("%020x", new BigInteger(1, hashedText)));
                st = new Style();
                toJoin = new TextComponentString("§c[x]");
                st.setClickEvent(tx);
                he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("このreplacerを削除します。"));
                st.setHoverEvent(he);
                st.setUnderlined(true);
                toJoin.setStyle(st);
                tc.appendSibling(toJoin);
                janum++;
            }
            sender.sendMessage(tc);
        }
    }
}
