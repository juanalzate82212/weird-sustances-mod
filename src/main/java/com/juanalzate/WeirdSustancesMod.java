package com.juanalzate;

import com.juanalzate.blocks.ModBlockEntities;
import com.juanalzate.blocks.ModBlocks;
import com.juanalzate.component.ModComponents;
import com.juanalzate.effect.ModEffects;
import com.juanalzate.items.ModItems;
import com.juanalzate.screen.ModScreenHandlers;
import com.juanalzate.sound.ModSounds;
import com.juanalzate.villager.ModTrades;
import com.juanalzate.villager.ModVillagers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeirdSustancesMod implements ModInitializer {
    public static final String MOD_ID = "weirdsustancesmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String VERSION = /*$ mod_version*/ "1.0.0";
    public static final String MINECRAFT = /*$ minecraft*/ "1.21.1";

    private static MinecraftServer SERVER = null;

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModItemGroup.initialize();
        ModComponents.initialize();
        ModBlockEntities.initialize();
        ModScreenHandlers.initialize();
        ModEffects.initialize();
        ModSounds.initialize();
        ModVillagers.initialize();
        ModTrades.initialize();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> SERVER = server);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> SERVER = null);

        LOGGER.info("Cargando WeirdSustancesMod");
    }

    /**
     * Adapts to the {@link ResourceLocation} changes introduced in 1.21.
     */
    public static ResourceLocation id(String namespace, String path) {
        //? if <1.21 {
        /*return new ResourceLocation(namespace, path);
        *///?} else
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}