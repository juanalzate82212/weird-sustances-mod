package com.juanalzate.client;

import com.juanalzate.WeirdSustancesMod;
import com.juanalzate.screen.CannabisCraftingScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CannabisCraftingScreen extends AbstractContainerScreen<CannabisCraftingScreenHandler> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            WeirdSustancesMod.MOD_ID, "textures/gui/cannabis_crafting_table.png"
    );

    public CannabisCraftingScreen(CannabisCraftingScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.titleLabelY = 6;
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.blit(
                TEXTURE,
                this.leftPos, this.topPos,
                0.0f, 0.0f,
                this.imageWidth,
                this.imageHeight,
                256, 256
        );
        drawProgressBar(context);
    }

    private void drawProgressBar(GuiGraphics context) {
        float progress = this.menu.getProgress();
        if (progress <= 0f) return;

        int barWidth = 22; //ajustar según textura;
        int barHeight = 2;
        int filled = (int) (barWidth * progress);

        int screenX = this.leftPos + 92;
        int screenY = this.topPos + 42;

        context.blit(
                TEXTURE,
                screenX, screenY,
                176f, 0f,
                filled, barHeight,
                256, 256
        );
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
    }
}
