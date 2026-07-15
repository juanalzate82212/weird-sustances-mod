package com.juanalzate.effect;

import com.juanalzate.WeirdSustancesMod;
import com.juanalzate.effect.HighEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    public static final Holder<MobEffect> HIGH = register(
            "high",
            new HighEffect()
    );

    private static Holder<MobEffect> register(String id, MobEffect effect) {
        return Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id),
                effect
        );
    }

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Effects registered");
    }
}
