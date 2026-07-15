package com.juanalzate.component;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum NuggetQuality implements StringRepresentable {
    COMMON      ("common",     "§7",  600,  0),   // 0:30  potencia I
    RARE        ("rare",      "§a",  2400, 0),   // 2:00  potencia I
    SPECIAL     ("special",  "§9",  6000, 1),   // 5:00  potencia II
    LEGENDARY   ("legendary","§6", 12000,1);   // 10:00 potencia II

    private final String id;
    public final String colorPrefix;
    public final int durationTicks;
    public final int amplifier;

    NuggetQuality(String id, String colorPrefix, int durationTicks, int amplifier) {
        this.id           = id;
        this.colorPrefix  = colorPrefix;
        this.durationTicks = durationTicks;
        this.amplifier  = amplifier;
    }

    @Override
    public String getSerializedName() { return id; }

    public static final Codec<NuggetQuality> CODEC =
            StringRepresentable.fromEnum(NuggetQuality::values);
}
