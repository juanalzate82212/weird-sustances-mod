package com.juanalzate.items;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class CannabisSeedItem extends BlockItem {
    public CannabisSeedItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public String getDescriptionId() {
        return Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
    }
}
