package com.juanalzate.blocks;

import com.juanalzate.component.NuggetQuality;
import com.juanalzate.items.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CannabisBlock extends BaseEntityBlock implements BonemealableBlock{
    public static final MapCodec<CannabisBlock> CODEC = simpleCodec(CannabisBlock::new);
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    private static final int GROW_LIGHT_RADIUS = 3;
    private static final int CHANCE_WITHOUT_LIGHT = 12;
    private static final int CHANCE_WITH_LIGHT = 5;

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.box(0, 0, 0, 16, 6, 16),
            Block.box(0, 0, 0, 16, 9, 16),
            Block.box(0, 0, 0, 16, 11, 16),
            Block.box(0, 0, 0, 16, 13, 16),
            Block.box(0, 0, 0, 16, 14, 16),
            Block.box(0, 0, 0, 16, 15, 16),
            Block.box(0, 0, 0, 16, 16, 16),
            Block.box(0, 0, 0, 16, 16, 16),
    };

    public CannabisBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any()
                .setValue(AGE, 0));
    }

    @Override
    public MapCodec<CannabisBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CannabisPlantBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return CannabisSoilBlock.isCannabisSoil(world, pos.below());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world,
                               BlockPos pos, CollisionContext context) {
        return AGE_TO_SHAPE[state.getValue(AGE)];
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        CannabisPlantBlockEntity be = ModBlockEntities.CANNABIS_PLANT.getBlockEntity(world, pos);
        if (be == null) {
            return;
        }

        boolean hasLight = hasNearbyGrowLight(world, pos);
        int age = state.getValue(AGE);

        if (age < MAX_AGE) {
            int chance = hasLight ? CHANCE_WITH_LIGHT : CHANCE_WITHOUT_LIGHT;
            if (random.nextInt(chance) == 0) {
                world.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
            }
        } else if (hasLight) {
            be.addLightExposure();
        }
    }

    private boolean hasNearbyGrowLight(ServerLevel world, BlockPos pos) {
        for (BlockPos nearby : BlockPos.betweenClosed(
                pos.offset(-GROW_LIGHT_RADIUS, -GROW_LIGHT_RADIUS, -GROW_LIGHT_RADIUS),
                pos.offset(GROW_LIGHT_RADIUS, GROW_LIGHT_RADIUS, GROW_LIGHT_RADIUS))) {
            if (world.getBlockState(nearby).getBlock() instanceof GrowLightBlock) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide() && !player.isCreative()) {
            int age = state.getValue(AGE);

            popResource(world, pos, new ItemStack(ModItems.CANNABIS_SEED));

            if (age == MAX_AGE) {
                CannabisPlantBlockEntity be = ModBlockEntities.CANNABIS_PLANT.getBlockEntity(world, pos);
                int score = be != null ? be.getQualityScore() : 0;
                dropNuggets(world, pos, score, world.getRandom());
            }
        }

        return super.playerWillDestroy(world, pos, state, player);
    }

    private void dropNuggets(Level world, BlockPos pos, int score, RandomSource random) {
        int count = 1 + (score >= 30 ? 1 : 0) + (score >= 70 ? 1 : 0);

        for (int i = 0; i < count; i++) {
            NuggetQuality quality = rollQuality(score, random);
            ItemStack nugget = getNuggetForQuality(quality);
            popResource(world, pos, nugget);
        }
    }

    private NuggetQuality rollQuality(int score, RandomSource random) {
        if (score >= 85) {
            int roll = random.nextInt(100);
            if (roll < 20) return NuggetQuality.RARE;
            if (roll < 60) return NuggetQuality.SPECIAL;
            return NuggetQuality.LEGENDARY;
        } else if (score >= 60) {
            int roll = random.nextInt(100);
            if (roll < 50) return NuggetQuality.COMMON;
            if (roll < 85) return NuggetQuality.RARE;
            return NuggetQuality.SPECIAL;
        } else if (score >= 30) {
            return random.nextInt(100) < 60 ? NuggetQuality.COMMON : NuggetQuality.RARE;
        } else {
            return NuggetQuality.COMMON;
        }
    }

    private ItemStack getNuggetForQuality(NuggetQuality quality) {
        return switch (quality) {
            case COMMON -> new ItemStack(ModItems.CANNABIS_NUGGET_COMMON);
            case RARE -> new ItemStack(ModItems.CANNABIS_NUGGET_RARE);
            case SPECIAL -> new ItemStack(ModItems.CANNABIS_NUGGET_SPECIAL);
            case LEGENDARY -> new ItemStack(ModItems.CANNABIS_NUGGET_LEGENDARY);
        };
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader view, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        int newAge = Math.min(age + 1 + random.nextInt(2), MAX_AGE);
        world.setBlock(pos, state.setValue(AGE, newAge), Block.UPDATE_CLIENTS);
    }
}
