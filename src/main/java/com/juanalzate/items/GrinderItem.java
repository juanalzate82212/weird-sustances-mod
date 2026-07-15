package com.juanalzate.items;

import com.juanalzate.component.ModComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GrinderItem extends Item {
    public GrinderItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (hand != InteractionHand.MAIN_HAND) return InteractionResultHolder.pass(stack);

        ItemStack offhand = player.getOffhandItem();

        if (!(offhand.getItem() instanceof CannabisNuggetItem)) {
            return InteractionResultHolder.pass(stack);
        }

        boolean alreadyProcessed = offhand.getOrDefault(ModComponents.PROCESSED, false);
        if (alreadyProcessed) {
            if (!world.isClientSide()) {
                player.displayClientMessage(
                        Component.translatable("weirdsustancesmod.grinder.already_processed").withStyle(ChatFormatting.YELLOW),
                        true
                );
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!world.isClientSide()) {
            ItemStack oneNugget = offhand.split(1);
            oneNugget.set(ModComponents.PROCESSED, true);

            if (offhand.isEmpty()) {
                player.setItemInHand(InteractionHand.OFF_HAND, oneNugget);
            } else {
                player.getInventory().placeItemBackInInventory(oneNugget);
            }

            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

            world.playSound(null, player.blockPosition(),
                    SoundEvents.GRINDSTONE_USE,
                    SoundSource.PLAYERS, 1.0f, 1.2f);
        }

        return InteractionResultHolder.success(stack);
    }
}
