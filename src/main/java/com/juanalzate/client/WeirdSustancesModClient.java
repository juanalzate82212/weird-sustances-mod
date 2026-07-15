package com.juanalzate.client;

import com.juanalzate.blocks.ModBlocks;
import com.juanalzate.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class WeirdSustancesModClient implements ClientModInitializer{
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CANNABIS_CROP, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GROW_LIGHT, RenderType.cutout());

        MenuScreens.register(ModScreenHandlers.CANNABIS_CRAFTING,
                CannabisCraftingScreen::new);

//		ComponentTooltipAppenderRegistry.addAfter(
//				DataComponents.MAX_DAMAGE,
//				ModComponents.BLUNT_DATA
//		);
    }
}
