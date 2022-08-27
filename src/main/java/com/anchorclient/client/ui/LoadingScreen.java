package com.anchorclient.client.ui;

import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.font.FontManager;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LoadingScreen {

    public static final int pMax = 12;
    public static int pCurrent = 0;
    public static String pDisplay = "Starting Minecraft";
    public static ResourceLocation background;

    public static FontManager fontManager = new FontManager();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ScaledResolution sr = new ScaledResolution(mc);
    public static TTFFontRenderer font = fontManager.getFont("anchor 20");

    public static void onUpdate() {
        if(mc == null || mc.getLanguageManager() == null) {
            return;
        } drawScreen(Minecraft.getMinecraft().getTextureManager());
    }

    public static void setStatus(int stage, String display) {
        pCurrent = stage;
        pDisplay = display;

        onUpdate();
    }

    public static void drawScreen(TextureManager tm) {
        int sf = sr.getScaleFactor();
        Framebuffer fb = new Framebuffer(sr.getScaledWidth() * sf, sr.getScaledHeight() * sf, true);
        fb.bindFramebuffer(true);

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double) sr.getScaledWidth(), (double) sr.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);

        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();

        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        if(background == null) {
            background = new ResourceLocation("Anchor/splash.png");
        } tm.bindTexture(background);

        GlStateManager.resetColor();
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        // Draws Background & Progress Bar
        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1920, 1080, sr.getScaledWidth(), sr.getScaledHeight(), 1920, 1080);
        drawLoading();

        fb.unbindFramebuffer();
        fb.framebufferRender(sr.getScaledWidth() * sf, sr.getScaledHeight() * sf);

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);

        mc.updateDisplay();
    }

    public static void drawLoading() {
        if(mc.gameSettings == null || mc.getTextureManager() == null) {
            return;
        }

        // double dCurrent = (double) pCurrent;
        // double percent = (dCurrent / pMax) * sr.getScaledWidth();

        // DrawingUtil.drawRoundedRect(0, sr.getScaledHeight() - 15, sr.getScaledWidth(), sr.getScaledHeight(), 3, 0x50000000);

        GlStateManager.resetColor();
        resetTextures();
        font.drawCenteredString(pDisplay.toUpperCase() + " (" + pCurrent + "/" + pMax + ")", sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, -1);

        GlStateManager.resetColor();
        resetTextures();

        TTFFontRenderer titleFont = fontManager.getFont("anchor 50");
        DrawingUtil.drawImgWithShadow("Anchor/icon.png", sr.getScaledWidth()/2f - 75/2f, sr.getScaledHeight()/2f - 80 - 75, 75, 75, 6, 2, -1);
        titleFont.drawCenteredString("ANCHOR CLIENT", sr.getScaledWidth()/2f, sr.getScaledHeight()/2f - 80, -1);

        // mc.getTextureManager().bindTexture(new ResourceLocation("Anchor/icon.png"));
        // Gui.drawModalRectWithCustomSizedTexture(sr.getScaledWidth()/2f - 75/2f, sr.getScaledHeight()/2f - 75f, 75f, 75f, 75f, 75f, 75f, 75f);

        GlStateManager.resetColor();
        resetTextures();

        // DrawingUtil.drawOutlinedRoundedRect(sr.getScaledWidth() / 2 - 262.5F, (float) sr.getScaledHeight() / 2 - 5, (float) (sr.getScaledWidth() / 2 - 262.5F + (percent * 525F)), sr.getScaledHeight() / 2 + 5, 3, 3, -1);
        // DrawingUtil.drawRoundedRect(sr.getScaledWidth() / 2 - 262.5F, (float) sr.getScaledHeight() / 2 - 5, (float) (sr.getScaledWidth() / 2 - 262.5F + (percent * 525F)), sr.getScaledHeight() / 2 + 5, 3, -1);
        // DrawingUtil.drawRoundedRect(sr.getScaledWidth() / 2 - 264.5F, (float) sr.getScaledHeight() / 2 - 7, (float) (sr.getScaledWidth() / 2 + 264.5F), sr.getScaledHeight() / 2 + 7, 3, -1);
    }

    public static void resetTextures() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
    }

}