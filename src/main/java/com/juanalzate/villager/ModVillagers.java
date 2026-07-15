package com.juanalzate.villager;

import com.google.common.collect.ImmutableSet;
import com.juanalzate.WeirdSustancesMod;
import com.juanalzate.blocks.ModBlocks;
import com.juanalzate.sound.ModSounds;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;

public class ModVillagers {
    public static final ResourceKey<PoiType> DEAL_POI_KEY = registerPoiKey("deal_poi");
    public static final PoiType DEAL_POI = registerPOI("deal_poi", ModBlocks.CANNABIS_CRAFTING_TABLE);
    public static final VillagerProfession DEALER = registerProfession("dealer", DEAL_POI_KEY);
    public static final ResourceKey<VillagerProfession> DEALER_KEY = registerProfessionKey("dealer");

    private static VillagerProfession registerProfession(String name, ResourceKey<PoiType> type) {
        return Registry.register(
                BuiltInRegistries.VILLAGER_PROFESSION,
                ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, name),
                new VillagerProfession(
                        "weirdsustancesmod.dealer",
                        entry -> entry.is(type),
                        entry -> entry.is(type),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        ModSounds.DEALER_WORK));
    }

    private static ResourceKey<VillagerProfession> registerProfessionKey(String name) {
        return ResourceKey.create(Registries.VILLAGER_PROFESSION, ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, name));
    }

    private static PoiType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, name),
                1, 1, block);
    }

    private static ResourceKey<PoiType> registerPoiKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, name));
    }

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Villagers registered");
    }
}
