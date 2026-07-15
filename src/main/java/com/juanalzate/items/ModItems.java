package com.juanalzate.items;

import com.juanalzate.WeirdSustancesMod;
import com.juanalzate.blocks.ModBlocks;
import com.juanalzate.component.ModComponents;
import com.juanalzate.component.NuggetQuality;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final CannabisSeedItem CANNABIS_SEED = registerSeedItem("cannabis_seed");

    public static final CannabisNuggetItem CANNABIS_NUGGET_COMMON =
            registerNugget("cannabis_nugget_common", NuggetQuality.COMMON);
    public static final CannabisNuggetItem CANNABIS_NUGGET_RARE =
            registerNugget("cannabis_nugget_rare", NuggetQuality.RARE);
    public static final CannabisNuggetItem CANNABIS_NUGGET_SPECIAL =
            registerNugget("cannabis_nugget_special", NuggetQuality.SPECIAL);
    public static final CannabisNuggetItem CANNABIS_NUGGET_LEGENDARY =
            registerNugget("cannabis_nugget_legendary", NuggetQuality.LEGENDARY);

    public static final Item BLUNT_WRAP_NATURAL = registerItem("blunt_wrap_natural");
    public static final Item BLUNT_WRAP_APPLE = registerItem("blunt_wrap_apple");
    public static final Item BLUNT_WRAP_WATERMELON = registerItem("blunt_wrap_watermelon");
    public static final Item BLUNT_WRAP_CHOCOLATE = registerItem("blunt_wrap_chocolate");
    public static final Item BLUNT_WRAP_HONEY = registerItem("blunt_wrap_honey");
    public static final Item BLUNT_WRAP_GOLDEN = registerItem("blunt_wrap_golden");

    public static final GrinderItem GRINDER = registerGrinder("grinder");

    public static final BluntItem BLUNT = registerBlunt("blunt");

    private static CannabisSeedItem registerSeedItem(String id) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id);
        CannabisSeedItem item = new CannabisSeedItem(ModBlocks.CANNABIS_CROP, new Item.Properties());
        return Registry.register(BuiltInRegistries.ITEM, identifier, item);
    }

    private static CannabisNuggetItem registerNugget(String id, NuggetQuality quality) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id);
        CannabisNuggetItem item = new CannabisNuggetItem(
                new Item.Properties()
                        .stacksTo(64)
                        .component(ModComponents.PROCESSED, false)
                        .component(ModComponents.NUGGET_QUALITY, quality),
                quality
        );
        return Registry.register(BuiltInRegistries.ITEM, identifier, item);
    }

    private static Item registerItem(String id) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id);
        Item item = new Item(new Item.Properties().stacksTo(16));
        return Registry.register(BuiltInRegistries.ITEM, identifier, item);
    }

    private static GrinderItem registerGrinder(String id) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id);
        GrinderItem item = new GrinderItem(new Item.Properties().stacksTo(1).durability(64));
        return Registry.register(BuiltInRegistries.ITEM, identifier, item);
    }

    private static BluntItem registerBlunt(String id) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id);
        BluntItem item = new BluntItem(new Item.Properties().stacksTo(16));
        return Registry.register(BuiltInRegistries.ITEM, identifier, item);
    }

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Items registered");
    }
}
