package com.juanalzate.component;

import com.juanalzate.WeirdSustancesMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ModComponents {
    public static final DataComponentType<Boolean> PROCESSED =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, "processed"),
                    DataComponentType.<Boolean>builder()
                            .persistent(Codec.BOOL)
                            .build()
            );

    public static final DataComponentType<NuggetQuality> NUGGET_QUALITY =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, "nugget_quality"),
                    DataComponentType.<NuggetQuality>builder()
                            .persistent(NuggetQuality.CODEC)
                            .build()
            );

    public static final DataComponentType<BluntData> BLUNT_DATA =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, "blunt_data"),
                    DataComponentType.<BluntData>builder()
                            .persistent(BluntData.CODEC)
                            .build()
            );

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Data Components registered");
    }
}
