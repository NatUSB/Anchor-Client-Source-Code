package com.anchorclient.client.ui;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.ui.accountmanager.AccountManagerGui;
import com.anchorclient.client.ui.elements.Button;
import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.render.DrawingUtil;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;
import java.io.IOException;

public class MainMenu extends GuiScreen {

    private final ResourceLocation[] paths = new ResourceLocation[] {
            new ResourceLocation("Anchor/panorama/panorama_0.png"),
            new ResourceLocation("Anchor/panorama/panorama_1.png"),
            new ResourceLocation("Anchor/panorama/panorama_2.png"),
            new ResourceLocation("Anchor/panorama/panorama_3.png"),
            new ResourceLocation("Anchor/panorama/panorama_4.png"),
            new ResourceLocation("Anchor/panorama/panorama_5.png")
    };

    private int panoramaTimer;
    private ResourceLocation backgroundTexture;

    private Button singleplayerButton;
    private Button multiplayerButton;

    private Button dockpassButton;
    private Button optionButton;
    private Button cosmeticButton;


    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);

        DynamicTexture viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);

        singleplayerButton = new Button("SINGLEPLAYER", sr.getScaledWidth()/2f - 60, sr.getScaledHeight()/2f - 20, 120, 26, new Color(255, 255, 255, 80).getRGB(), new Color(200, 200, 200, 80).getRGB());
        multiplayerButton = new Button("MULTIPLAYER", sr.getScaledWidth()/2f - 60, sr.getScaledHeight()/2f + 12, 120, 26, new Color(255, 255, 255, 80).getRGB(), new Color(200, 200, 200, 80).getRGB());

        dockpassButton = new Button(new ResourceLocation("Anchor/dockpass.png"), sr.getScaledWidth()/2f - 60 - 32, sr.getScaledHeight()/2f - 20, 26, 26 * 2 + 6, new Color(255, 255, 255, 80).getRGB(), new Color(200, 200, 200, 80).getRGB());
        optionButton = new Button(new ResourceLocation("Anchor/cog.png"), sr.getScaledWidth()/2f - 60 + 126, sr.getScaledHeight()/2f - 20, 26, 26, new Color(255, 255, 255, 80).getRGB(), new Color(200, 200, 200, 80).getRGB());
        cosmeticButton = new Button(new ResourceLocation("Anchor/cosmetic.png"), sr.getScaledWidth()/2f - 60 + 126, sr.getScaledHeight()/2f - 20 + 32, 26, 26, new Color(255, 255, 255, 80).getRGB(), new Color(200, 200, 200, 80).getRGB());

        //AnchorClient.INSTANCE.discordRP.updatePresence("In-Menu", "Main Menu");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        // Background
        GlStateManager.disableAlpha();
        this.renderSkybox(partialTicks);
        GlStateManager.enableAlpha();

        // Title
        DrawingUtil.drawImgWithShadow("Anchor/icon.png", sr.getScaledWidth()/2f - 75/2f, sr.getScaledHeight()/2f - 80 - 75, 75, 75, 6, 2, -1);
        AnchorClient.INSTANCE.fontManager.getFont("anchor 50").drawCenteredString("ANCHOR CLIENT", sr.getScaledWidth()/2f, sr.getScaledHeight()/2f - 80, -1);



        // Text Buttons
        singleplayerButton.render(mouseX, mouseY);
        multiplayerButton.render(mouseX, mouseY);

        // Image Buttons
        dockpassButton.render(mouseX, mouseY);
        optionButton.render(mouseX, mouseY);
        cosmeticButton.render(mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        panoramaTimer += 1;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);

        if(HoveredUtil.isRectHovered(sr.getScaledWidth()/2f - 60, sr.getScaledHeight()/2f - 20, 120, 26, mouseX, mouseY)) {
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if(HoveredUtil.isRectHovered(sr.getScaledWidth()/2f - 60 + 126, sr.getScaledHeight()/2f - 20 + 32, 26, 26, mouseX, mouseY)) {
            mc.displayGuiScreen(new NewMainMenu());
            //mc.displayGuiScreen(new CosmeticsGUI( ));
        }
        if(HoveredUtil.isRectHovered(sr.getScaledWidth()/2f - 60, sr.getScaledHeight()/2f + 12, 120, 26, mouseX, mouseY)) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if(HoveredUtil.isRectHovered(sr.getScaledWidth()/2f - 60 + 126, sr.getScaledHeight()/2f - 20, 26, 26, mouseX, mouseY)) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    @Override
    public void onGuiClosed() {

    }

    private void renderSkybox(float p_73971_3_) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_3_);
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f = this.width > this.height ? 120.0F / (float) this.width : 120.0F / (float) this.height;
        float f1 = (float) this.height * f / 256.0F;
        float f2 = (float) this.width * f / 256.0F;
        int i = this.width;
        int j = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, j, this.zLevel).tex(0.5F - f1, 0.5F + f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(i, j, this.zLevel).tex(0.5F - f1, 0.5F - f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(i, 0.0D, this.zLevel).tex(0.5F + f1, 0.5F - f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.5F + f1, 0.5F + f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }

    private void drawPanorama(float p_73970_3_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;

        for (int j = 0; j < i * i; ++j) {
            GlStateManager.pushMatrix();
            float f = ((float) (j % i) / (float) i - 0.5F) / 64.0F;
            float f1 = ((float) (j / i) / (float) i - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float) this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F - 10f, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float) this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);
//            GlStateManager.rotate(-90f, 0.0F, 0.0F, 1.0F);

            for (int k = 0; k < 6; ++k) {
                GlStateManager.pushMatrix();

                if (k == 1) {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 2) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 3) {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 4) {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (k == 5) {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                this.mc.getTextureManager().bindTexture(paths[k]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox() {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;
        for (int j = 0; j < i; ++j) {
            float f = 1.0F / (float) (j + 1);
            int k = this.width;
            int l = this.height;
            float f1 = (float) (j - i / 2) / 256.0F;
            worldrenderer.pos(k, l, this.zLevel).tex(0.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(k, 0.0D, this.zLevel).tex(1.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(1.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, l, this.zLevel).tex(0.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

}
