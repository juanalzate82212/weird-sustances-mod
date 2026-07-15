package com.juanalzate.items;

import com.juanalzate.component.ModComponents;
import com.juanalzate.component.NuggetQuality;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CannabisNuggetItem extends Item {
    private final NuggetQuality quality;

    public CannabisNuggetItem(Properties settings, NuggetQuality quality) {
        super(settings);
        this.quality = quality;
    }

    public NuggetQuality getQuality() {
        return quality;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        boolean processed = stack.getOrDefault(ModComponents.PROCESSED, false);

        if (processed) {
            tooltip.add(Component.translatable("weirdsustancesmod.tooltip.processed").withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.add(Component.translatable("weirdsustancesmod.tooltip.unprocessed").withStyle(ChatFormatting.DARK_GRAY));
        }

        ChatFormatting color = switch (quality) {
            case LEGENDARY -> ChatFormatting.GOLD;
            case SPECIAL -> ChatFormatting.BLUE;
            case RARE -> ChatFormatting.GREEN;
            case COMMON -> ChatFormatting.WHITE;
        };
        tooltip.add(Component.translatable("weirdsustancesmod.tooltip.quality")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("weirdsustancesmod.nugget_quality." + quality.getSerializedName())
                        .withStyle(color)));
    }
}
