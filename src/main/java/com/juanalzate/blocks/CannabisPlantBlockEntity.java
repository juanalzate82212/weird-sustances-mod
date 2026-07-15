package com.juanalzate.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CannabisPlantBlockEntity extends BlockEntity{
    private static final int LIGHT_CAP = 53;

    private int lightExposure = 0;

    public CannabisPlantBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CANNABIS_PLANT, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("lightExposure", lightExposure);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        lightExposure = tag.getInt("lightExposure");
    }

    public void addLightExposure() {
        if (lightExposure < LIGHT_CAP) {
            lightExposure++;
            setChanged();
        }
    }

    public int getLightExposure() {
        return lightExposure;
    }

    public int getQualityScore() {
        int cappedLight = Math.min(lightExposure, LIGHT_CAP);

        return (int) ((cappedLight / (double) LIGHT_CAP) * 100);
    }
}
