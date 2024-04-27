package cn.snowskystudio.crystallography.screen;

import cn.snowskystudio.crystallography.Crystallography;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrystallizerScreen extends AbstractContainerScreen<CrystallizerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Crystallography.MODID, "textures/gui/crystallizer_gui.png");

    public CrystallizerScreen(CrystallizerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.imageHeight = 186;
        this.imageWidth = 230;
        this.inventoryLabelY = this.imageHeight - 94;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 95, y + 44, 176, 0, menu.getScaledProgress(), 20);
        }
        guiGraphics.blit(TEXTURE, x + 189, y + 169 - menu.getScaledTemperature(), 248, 0, 2, menu.getScaledTemperature());
        guiGraphics.blit(TEXTURE, x + 201, y + 178 - menu.getScaledWater(), 230, 0, 18, menu.getScaledWater());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        if (pX > (this.getGuiLeft()+201) && pY > (this.getGuiTop()+11) &&
                pX < (this.getGuiLeft()+220) && pY < (this.getGuiTop()+179)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable("tooltip.crystallography.water"));
            list.add(Component.nullToEmpty(String.valueOf(menu.getData().get(3)) + "/500000"));
            pGuiGraphics.renderTooltip(this.font, list, Optional.empty(), pX, pY);
        } else if (pX > (this.getGuiLeft() + 185) && pY > (this.getGuiTop() + 11) &&
                pX < (this.getGuiLeft() + 195) && pY < (this.getGuiTop() + 169)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable("tooltip.crystallography.temperature"));
            list.add(Component.nullToEmpty(String.valueOf(menu.getData().get(2)) + "/10000"));
            pGuiGraphics.renderTooltip(this.font, list, Optional.empty(), pX, pY);
        }
    }
}
