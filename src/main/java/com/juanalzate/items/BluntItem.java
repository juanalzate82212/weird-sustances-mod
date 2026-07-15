package com.juanalzate.items;

import com.juanalzate.component.BluntData;
import com.juanalzate.component.BluntWrapType;
import com.juanalzate.component.ModComponents;
import com.juanalzate.effect.ModEffects;
import com.juanalzate.sound.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class BluntItem extends Item {
    public BluntItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack offhand = player.getOffhandItem();
        boolean hasLighter = offhand.is(Items.FLINT_AND_STEEL);

        if (!hasLighter) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        BluntData data = stack.get(ModComponents.BLUNT_DATA);
        if (data == null) return;
        data.addToTooltip(context, tooltip::add, type);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 60;
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClientSide() && remainingUseTicks % 60 == 0) {
            world.playSound(null, user.blockPosition(),
                    ModSounds.BLUNT_USE,
                    SoundSource.PLAYERS,
                    3.0f, 1.0f);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!(user instanceof Player player)) return stack;

        BluntData data = stack.get(ModComponents.BLUNT_DATA);

        if (data != null && !world.isClientSide()) {
            applyEffects(player, data);
        }

        world.playSound(null, user.blockPosition(), ModSounds.BLUNT_FINISH, SoundSource.PLAYERS, 3.0f, 1.0f);

        if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
            for (int i = 0; i < 10; i++) {
                serverWorld.sendParticles(
                        ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        player.getX(),
                        player.getY() + 1.5,
                        player.getZ(),
                        10,
                        0.3, 0.1, 0.3,
                        0.02
                );
            }

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }
        }

        if (!player.isCreative()) {
            stack.shrink(1);
        }

        return stack;
    }

    private void applyEffects(Player player, BluntData data) {
        int duration = data.durationTicks();
        int amplifier = data.amplifier();

        player.addEffect(new MobEffectInstance(
                ModEffects.HIGH,
                duration,
                0
        ));

        player.addEffect(new MobEffectInstance(
                MobEffects.CONFUSION,
                300,
                0,
                false,
                false,
                false
        ));

        player.addEffect(new MobEffectInstance(
                MobEffects.HUNGER,
                600,
                0,
                false,
                false,
                false
        ));

        BluntWrapType wrap = data.bluntWrapType();

        for (Holder<MobEffect> effect : wrap.getEffects()) {
            player.addEffect(new MobEffectInstance(
                    effect,
                    duration,
                    amplifier
            ));
        }


    }
}
