package com.juanalzate.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CannabisCraftingBlock extends BaseEntityBlock{
    public static final MapCodec<CannabisCraftingBlock> CODEC =
            simpleCodec(CannabisCraftingBlock::new);

    protected CannabisCraftingBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<CannabisCraftingBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CannabisCraftingBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.CANNABIS_CRAFTING,
                CannabisCraftingBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide()) {
            CannabisCraftingBlockEntity be = (CannabisCraftingBlockEntity) world.getBlockEntity(pos);
            if (be != null) {
                player.openMenu(be);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide()) {
            CannabisCraftingBlockEntity be = (CannabisCraftingBlockEntity) world.getBlockEntity(pos);
            if (be != null) {
                for (int i = 0; i < 3; i++) {
                    Block.popResource(world, pos, be.getItem(i));
                }
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }
}
