package com.github.soramame0256.lorereplacer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.soramame0256.lorereplacer.LoreReplacer.dataUtils;

public class EventHandler {
    private static Map<String, List<String>> cache = new HashMap<>();
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLoreRender(ItemTooltipEvent e) {
        List<String> newLore = new ArrayList<>();
        if(Minecraft.getMinecraft().player == null || dataUtils == null) return;
        String hash = ItemUtils.getHash(e.getToolTip());
        if(cache.containsKey(hash)){
            newLore = cache.get(hash);
        }else{
            for (String s : e.getToolTip()) {
                if (dataUtils.getRootJson().has("replacers")) {
                    JsonArray ja = dataUtils.getRootJson().get("replacers").getAsJsonArray();
                    for (JsonElement je : ja) {
                        JsonObject jo = je.getAsJsonObject();
                        s = s.replaceAll(jo.get("from").getAsString(), jo.get("to").getAsString());
                    }
                }
                if (!s.contains("//DELETE")) newLore.add(s);
            }
            cache.put(hash, newLore);
        }
        e.getToolTip().clear();
        e.getToolTip().addAll(newLore);

    }
}
