package com.juanalzate.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record BluntData(
        BluntWrapType bluntWrapType,
        NuggetQuality nuggetQuality,
        int durationTicks,
        int amplifier
) implements TooltipProvider {

    public static final Codec<BluntData> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    BluntWrapType.CODEC.fieldOf("blunt_wrap_type").forGetter(BluntData::bluntWrapType),
                    NuggetQuality.CODEC.fieldOf("nugget_quality").forGetter(BluntData::nuggetQuality),
                    Codec.INT.fieldOf("duration_ticks").forGetter(BluntData::durationTicks),
                    Codec.INT.fieldOf("amplifier").forGetter(BluntData::amplifier)
            ).apply(builder, BluntData::new)
    );

    public static BluntData from(BluntWrapType wrap, NuggetQuality quality) {
        return new BluntData(wrap, quality, quality.durationTicks, quality.amplifier);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context,
                             Consumer<Component> tooltip,
                             TooltipFlag type) {
        tooltip.accept(Component.translatable("weirdsustancesmod.tooltip.effect")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("weirdsustancesmod.blunt_wrap_type." + bluntWrapType.getSerializedName())
                        .withStyle(ChatFormatting.WHITE)));

        ChatFormatting qualityColor = switch (nuggetQuality) {
            case LEGENDARY -> ChatFormatting.GOLD;
            case SPECIAL -> ChatFormatting.BLUE;
            case RARE -> ChatFormatting.GREEN;
            case COMMON -> ChatFormatting.WHITE;
        };
        tooltip.accept(Component.translatable("weirdsustancesmod.tooltip.nugget")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("weirdsustancesmod.nugget_quality." + nuggetQuality.getSerializedName())
                        .withStyle(qualityColor)));

        int seconds = (durationTicks / 20) % 60;
        int minutes = (durationTicks / 1200);
        String potency = amplifier == 0 ? "I" : "II";
        tooltip.accept(Component.translatable("weirdsustancesmod.tooltip.duration", minutes, seconds, potency)
                .withStyle(ChatFormatting.GRAY));
    }
}
