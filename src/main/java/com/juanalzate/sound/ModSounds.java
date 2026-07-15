package com.juanalzate.sound;

import com.juanalzate.WeirdSustancesMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final SoundEvent BLUNT_USE = register("blunt_use");
    public static final SoundEvent BLUNT_FINISH = register("blunt_finish");
    public static final SoundEvent DEALER_WORK = register("dealer_work");

    private static SoundEvent register(String id) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id);
        return Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                identifier,
                SoundEvent.createVariableRangeEvent(identifier)
        );
    }

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Sounds registered");
    }
}
