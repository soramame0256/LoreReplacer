package com.github.soramame0256.lorereplacer;

import com.github.soramame0256.lorereplacer.command.*;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;

@Mod(
        modid = LoreReplacer.MOD_ID,
        name = LoreReplacer.MOD_NAME,
        version = LoreReplacer.VERSION
)
public class LoreReplacer {

    public static final String MOD_ID = "lorereplacer";
    public static final String MOD_NAME = "LoreReplacer";
    public static final String VERSION = "1.1-SNAPSHOT";
    public static final String INFO_PREFIX = "§2[LoreReplacer]§f ";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static LoreReplacer INSTANCE;
    public static DataUtils dataUtils;
    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            dataUtils = new DataUtils("replacers.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientCommandHandler cch = ClientCommandHandler.instance;
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        cch.registerCommand(new CmdAddReplacer());
        cch.registerCommand(new CmdRemoveReplacer());
        cch.registerCommand(new CmdListReplacer());
        cch.registerCommand(new CmdEditReplacer());
        cch.registerCommand(new CmdReloadReplacer());
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }


}
