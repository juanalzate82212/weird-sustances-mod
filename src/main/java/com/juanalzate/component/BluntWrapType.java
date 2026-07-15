package com.juanalzate.component;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Holder;

public enum BluntWrapType implements StringRepresentable {
    NATURAL    ("natural",    "§f"),
    APPLE      ("apple",      "§e"),
    WATERMELON ("watermelon", "§c"),
    CHOCOLATE  ("chocolate",  "§6"),
    HONEY      ("honey",      "§b"),
    GOLDEN     ("golden",     "§e");

    private final String id;

    public final String tooltipColor;

    BluntWrapType(String id, String tooltipColor) {
        this.id = id;
        this.tooltipColor = tooltipColor;
    }

    @Override
    public String getSerializedName() { return id; }

    public static final Codec<BluntWrapType> CODEC = StringRepresentable.fromEnum(BluntWrapType::values);

    @SuppressWarnings("unchecked")
    public Holder<MobEffect>[] getEffects() {
        return switch (this) {
            case NATURAL -> arr(MobEffects.MOVEMENT_SLOWDOWN);
            case APPLE -> arr(MobEffects.DIG_SPEED);
            case WATERMELON -> arr(MobEffects.REGENERATION);
            case CHOCOLATE -> arr(MobEffects.DAMAGE_BOOST);
            case HONEY -> arr(MobEffects.MOVEMENT_SPEED);
            case GOLDEN -> arr(MobEffects.NIGHT_VISION, MobEffects.DIG_SPEED, MobEffects.MOVEMENT_SPEED);
        };
    }

    @SafeVarargs
    private static Holder<MobEffect>[] arr(Holder<MobEffect>... entries) {
        return entries;
    }
}
