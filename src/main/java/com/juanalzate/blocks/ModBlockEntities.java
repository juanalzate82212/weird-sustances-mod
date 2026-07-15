package com.juanalzate.blocks;

import com.juanalzate.WeirdSustancesMod;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final BlockEntityType<CannabisPlantBlockEntity> CANNABIS_PLANT =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, "cannabis_plant"),
                    FabricBlockEntityTypeBuilder.create(
                            CannabisPlantBlockEntity::new,
                            ModBlocks.CANNABIS_CROP
                    ).build()
            );

    public static final BlockEntityType<CannabisCraftingBlockEntity> CANNABIS_CRAFTING =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, "cannabis_crafting"),
                    FabricBlockEntityTypeBuilder.create(
                            CannabisCraftingBlockEntity::new,
                            ModBlocks.CANNABIS_CRAFTING_TABLE
                    ).build()
            );

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Block Entities registered");
    }
}
