package com.juanalzate.blocks;

import com.juanalzate.WeirdSustancesMod;
import com.juanalzate.items.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    public static final CannabisBlock CANNABIS_CROP = registerBlock("cannabis_crop",
            new CannabisBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
            ), false);

    public static final CannabisSoilBlock CANNABIS_SOIL = registerBlock("cannabis_soil",
            new CannabisSoilBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .strength(0.6f)
                    .sound(SoundType.GRAVEL)
            ), true);

    public static final GrowLightBlock GROW_LIGHT = registerBlock("grow_light",
            new GrowLightBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(0.3f)
                    .lightLevel(state -> 15)
                    .noOcclusion()
                    .sound(SoundType.GLASS)
            ), true);

    public static final CannabisCraftingBlock CANNABIS_CRAFTING_TABLE = registerBlock("cannabis_crafting_table",
            new CannabisCraftingBlock(BlockBehaviour.Properties.of()
                    .strength(2.5f)
                    .sound(SoundType.WOOD)
            ), true);

    public static ItemLike CANNABIS_SEED_ITEM = ModItems.CANNABIS_SEED;

    private static <T extends Block> T registerBlock(String id, T block, boolean withBlockItem) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id);
        Registry.register(BuiltInRegistries.BLOCK, identifier, block);

        if (withBlockItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, identifier, blockItem);
        }
        return block;
    }

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Blocks registered");
    }
}
