package com.juanalzate.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CannabisSoilBlock extends Block{
    public static final MapCodec<CannabisSoilBlock> CODEC = simpleCodec(CannabisSoilBlock::new);

    public CannabisSoilBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<CannabisSoilBlock> codec() {
        return CODEC;
    }

    public static boolean isCannabisSoil(BlockGetter world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof CannabisSoilBlock;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved) {
            BlockPos above = pos.above();
            BlockState aboveState = world.getBlockState(above);
            if (aboveState.getBlock() instanceof CannabisBlock) {
                world.destroyBlock(above, true);
            }
        }
        super.onRemove(state, world, pos, newState, moved);
    }


}
