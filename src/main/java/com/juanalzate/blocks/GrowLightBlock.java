package com.juanalzate.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GrowLightBlock extends Block{
    public static final MapCodec<GrowLightBlock> CODEC = simpleCodec(GrowLightBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    public static final VoxelShape SHAPE_UP = Block.box(0, 0, 0, 16, 3, 16);
    public static final VoxelShape SHAPE_DOWN = Block.box(0, 13, 0, 16, 16, 16);

    public GrowLightBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.UP));
    }

    @Override
    public MapCodec<GrowLightBlock> codec() {
        return CODEC;
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction clicked = ctx.getClickedFace();

        if (clicked == Direction.UP) {
            return defaultBlockState().setValue(FACING, Direction.UP);
        } else if (clicked == Direction.DOWN) {
            return defaultBlockState().setValue(FACING, Direction.DOWN);
        }

        return defaultBlockState().setValue(FACING, Direction.DOWN);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world,
                               BlockPos pos, CollisionContext context) {
        return state.getValue(FACING) == Direction.UP ? SHAPE_UP : SHAPE_DOWN;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }
}
