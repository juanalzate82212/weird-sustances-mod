package com.juanalzate.screen;

import com.juanalzate.WeirdSustancesMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModScreenHandlers {
    public static final MenuType<CannabisCraftingScreenHandler> CANNABIS_CRAFTING = register("cannabis_crafting", CannabisCraftingScreenHandler::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(
            String id, MenuType.MenuSupplier<T> constructor) {
        return Registry.register(
                BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(WeirdSustancesMod.MOD_ID, id),
                new MenuType<>(constructor, FeatureFlagSet.of())
        );
    }

    public static void initialize() {
        WeirdSustancesMod.LOGGER.info("[Weird Sustances Mod] Screen Handlers registered");
    }
}
