package com.juanalzate;

import com.juanalzate.blocks.ModBlocks;
import com.juanalzate.items.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup {
    public static final ResourceKey<CreativeModeTab> WEIRDSUSTANCESMOD_GROUP = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, "weirdsustancesmod_group")
    );

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WEIRDSUSTANCESMOD_GROUP,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModItems.BLUNT))
                        .title(Component.literal("Weird Sustances Mod"))
                        .displayItems((context, output) -> {
                            output.accept(ModItems.CANNABIS_SEED);
                            output.accept(ModItems.CANNABIS_NUGGET_COMMON);
                            output.accept(ModItems.CANNABIS_NUGGET_RARE);
                            output.accept(ModItems.CANNABIS_NUGGET_SPECIAL);
                            output.accept(ModItems.CANNABIS_NUGGET_LEGENDARY);
                            output.accept(ModItems.BLUNT_WRAP_NATURAL);
                            output.accept(ModItems.BLUNT_WRAP_APPLE);
                            output.accept(ModItems.BLUNT_WRAP_WATERMELON);
                            output.accept(ModItems.BLUNT_WRAP_CHOCOLATE);
                            output.accept(ModItems.BLUNT_WRAP_HONEY);
                            output.accept(ModItems.BLUNT_WRAP_GOLDEN);
                            output.accept(ModItems.GRINDER);
                            output.accept(ModBlocks.CANNABIS_SOIL);
                            output.accept(ModBlocks.GROW_LIGHT);
                            output.accept(ModBlocks.CANNABIS_CRAFTING_TABLE);
                            output.accept(ModItems.BLUNT);
                        })
                        .build()
        );

        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Group Items registered");
    }
}
