package com.juanalzate.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HighEffect extends MobEffect {
    public HighEffect() {
        super(
                MobEffectCategory.NEUTRAL,
                0x22BB44
        );
    }
}
