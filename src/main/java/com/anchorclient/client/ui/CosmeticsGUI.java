package com.anchorclient.client.ui;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.cosmetic.CosmeticFrame;
import com.anchorclient.client.feature.cosmetic.capes.BetaCape;
import com.anchorclient.client.ui.clickgui.elements.ModuleFrame;
import com.anchorclient.client.ui.elements.ScrollHelper;
import com.anchorclient.client.ui.elements.SearchBar;
import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.misc.MathUtil;
import com.anchorclient.client.util.render.BlurUtil;
import com.anchorclient.client.util.render.ColorBuilder;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.GLUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CosmeticsGUI extends GuiScreen {

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
    private Animation opacityAnimation;
    private CosmeticCategory currentCosmeticCategory;
    private final ArrayList<CosmeticFrame> cosmeticFrames = new ArrayList<>();

    private ScrollHelper scrollHelper;

    private SearchBar searchBar;

    private float hudOffset = 28;

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);

        DynamicTexture viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);

        float width = 770;
        float height = 400;

        opacityAnimation = new Animation(250, 0, 1, Easing.EASE_OUT_CUBIC);
        currentCosmeticCategory = CosmeticCategory.ALL;

        scrollHelper = new ScrollHelper(calculateMaxScroll(), 500);
            //Search bar
        searchBar = new SearchBar((sr.getScaledWidth()/2f - width/2f + 90) + hudOffset, sr.getScaledHeight()/2f - height/2f + 80, 536, 16);

        initializeFrames();

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
        this.renderSkybox(partialTicks);
        GlStateManager.enableAlpha();

        ScaledResolution sr = new ScaledResolution(mc);

        float width = 770;
        float height = 400;

        Gui.drawRect(0, 0, this.width, this.height, 0x40000000);
        BlurUtil.blur((float) MathUtil.interpolate(1, 20, opacityAnimation.getValue()));

        int scroll = Mouse.getDWheel();

        if(scroll > 0) {
            scrollHelper.onScroll(20);
        }

        if(scroll < 0) {
            scrollHelper.onScroll(-20);
        }

        scrollHelper.update();

        // Shadow
        DrawingUtil.drawShadow(sr.getScaledWidth()/2f - (width/2f - 100), (sr.getScaledHeight()/2f - height/2f) + 38, width - 200, height - (63 + 16), 8,  6, 2);
        // Top Bar
        DrawingUtil.drawRoundedRect(sr.getScaledWidth()/2f - (width/2f - 100), (sr.getScaledHeight()/2f - height/2f) + 38, width - 50, 45, 17, 17, 0, 0, new ColorBuilder(0xff3c3c3c).setAlphaPct(75, this.opacityAnimation.getValue()).getRGB());

        TTFFontRenderer topBarCFont = AnchorClient.INSTANCE.fontManager.getFont("anchor 25");

        DrawingUtil.drawImgWithShadow("Anchor/images/close.png", sr.getScaledWidth()/2f + 400 + (((33/1.4f)/2) - (18/1.4f)/2), (sr.getScaledHeight()/2f - height/2f + 49.5f) + (((33/1.4f)/2) - (18/1.4f)/2), 18/1.4f, 18/1.4f, 6, 2, -1);
        // Main UI
        DrawingUtil.drawRoundedRect(sr.getScaledWidth()/2f - (width/2f - 100), sr.getScaledHeight()/2f - height/2f + (63 + 20), width - 50, height - (63 + 60), 0, 0, 17, 17, new ColorBuilder(0xff3c3c3c).setAlphaPct(75, this.opacityAnimation.getValue()).getRGB());

        TTFFontRenderer role = AnchorClient.INSTANCE.fontManager.getFont("anchor 16");
        role.drawString("Cosmetics:  " + (AnchorClient.INSTANCE.cosmeticManager.getEnabledCosmetics().isEmpty() ? "None" : AnchorClient.INSTANCE.cosmeticManager.getEnabledCosmetics().toString()), sr.getScaledWidth()/1.19f, sr.getScaledHeight()/1.3f, new Color(255, 255, 255, 255).getRGB());

        // Scrollbar
        DrawingUtil.drawRoundedRect(width - 16, sr.getScaledHeight()/2f - height/2f + 120 + (-scrollHelper.getScrollAnimation().getValue()), 4, 83, 4, 4, 4, 4, new ColorBuilder(0xffffff).setAlphaPct(75, this.opacityAnimation.getValue()).getRGB());

        int cCount = 0;
        for(CosmeticCategory cc : CosmeticCategory.values()) {
            float offset = (cCount * 80);

            TTFFontRenderer font = AnchorClient.INSTANCE.fontManager.getFont("anchor 20");

            DrawingUtil.drawShadow((sr.getScaledWidth()/2f - width/2f + 92 + offset) + hudOffset, sr.getScaledHeight()/2f - height/2f + 53, 72, 16, 4, 3, 1);
            DrawingUtil.drawRoundedRect((sr.getScaledWidth()/2f - width/2f + 92 + offset) + hudOffset, sr.getScaledHeight()/2f - height/2f + 53, 72, 16, 4, cc == currentCosmeticCategory ? new ColorBuilder(0xff333b56).setAlphaPct(50, this.opacityAnimation.getValue()).getRGB() : new ColorBuilder(0xff42454e).setAlphaPct(50, this.opacityAnimation.getValue()).getRGB());
            font.drawCenteredString(cc.name, (sr.getScaledWidth()/2f - width/2f + 92 + offset + 72/2f) + hudOffset, sr.getScaledHeight()/2f - height/2f + 53 + 8 - font.getHeight(cc.name)/2f + 1, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, this.opacityAnimation.getValue())).getRGB());
//draws name
         cCount++;
        }

        // Search Bar
        searchBar.render(this.opacityAnimation.getValue());


        GLUtil.scissor(sr.getScaledWidth()/2f - width/2f, sr.getScaledHeight()/2f - height/4f + 15, width, height/1.37f - 70, () -> {
            for(CosmeticFrame mf : cosmeticFrames) {
                mf.render(scrollHelper.getScrollAnimation().getValue() + 2, mouseX, mouseY, this.opacityAnimation.getValue());
            }
        });
    }
    private float calculateMaxScroll() {
        return 30;
    }

    private void initializeFrames(String query) {
        ScaledResolution sr = new ScaledResolution(mc);

        this.scrollHelper.setHVal(0);
        this.scrollHelper.setScrollAnimation(new Animation(500, this.scrollHelper.getScrollAnimation().getValue(), 0, Easing.EASE_OUT_CUBIC));

        cosmeticFrames.clear();

        float width = 770;
        float height = 400;

        float firstOffset = 0;
        float secondOffset = 0;
        float thirdOffset = 0;
        float fourthOffset = 0;

        float cosmeticOffset = 70; // Vertical Frame Spacing

        int mCount = 0;
        for(Cosmetic cc : AnchorClient.INSTANCE.cosmeticManager.getCosmeticsInCategory(currentCosmeticCategory)) {
            if(!cc.isVisible()) continue; // Skip over the cosmetic if it's not supposed to show on the gui

            if(!query.isEmpty()) {
                if (!cc.getName().toLowerCase().contains(query.toLowerCase())) continue;
            }


            boolean first = (mCount + 1) % 4 == 1;
            boolean second = (mCount + 1) % 4 == 2;
            boolean third = (mCount + 1) % 4 == 3;
            boolean fourth = (mCount + 1) % 4 == 0;

            //COSMETIC TOGGLE KEYS

            if(first) {
                cosmeticFrames.add(new CosmeticFrame((sr.getScaledWidth()/2f - 293) + hudOffset, sr.getScaledHeight()/2f - height/2f + 118 + firstOffset, cc));
                firstOffset += cosmeticOffset;
            }

            if(second) {
                cosmeticFrames.add(new CosmeticFrame((sr.getScaledWidth()/2f - 143 - 14) + hudOffset, sr.getScaledHeight()/2f - height/2f + 118 + secondOffset, cc));
                secondOffset += cosmeticOffset;
            }

            if(third) {
                cosmeticFrames.add(new CosmeticFrame((sr.getScaledWidth()/2f + 7 - 28) + hudOffset, sr.getScaledHeight()/2f - height/2f + 118 + thirdOffset, cc));
                thirdOffset += cosmeticOffset;
            }

            if(fourth) {
                cosmeticFrames.add(new CosmeticFrame((sr.getScaledWidth()/2f + 157 - 42) + hudOffset, sr.getScaledHeight()/2f - height/2f + 118 + fourthOffset, cc));
                fourthOffset += cosmeticOffset;
            }
            mCount++;
        }
    }

    private void initializeFrames() {
        initializeFrames("");
    }

    @Override
    public void updateScreen() {
        panoramaTimer += 1;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);

        float width = 770;
        float height = 400;

        for(CosmeticFrame mf : cosmeticFrames) {
            mf.onMouseClick(this.scrollHelper.getScrollAnimation().getValue(), mouseX, mouseY, mouseButton);
        }

        int cCount = 0;
        for(CosmeticCategory cc : CosmeticCategory.values()) {
            float offset = (cCount * 80);

            if(HoveredUtil.isRectHovered((sr.getScaledWidth()/2f - width/2f + 92 + offset) + hudOffset, sr.getScaledHeight()/2f - height/2f + 53, 72, 16, mouseX, mouseY)) {
                this.currentCosmeticCategory = cc;
                initializeFrames();
            }

            cCount++;
        }
        searchBar.mouseClicked(mouseX, mouseY, mouseButton);

        if(HoveredUtil.isRectHovered(sr.getScaledWidth()/2f + 400, sr.getScaledHeight()/2f - height/2f + 49, 33/1.4f, 33/1.4f, mouseX, mouseY)) {
            mc.displayGuiScreen(null);
        }

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
        searchBar.keyTyped(typedChar, keyCode);

        if(!searchBar.getAs().getText().isEmpty()) {
            initializeFrames(searchBar.getAs().getText());
        } else {
            initializeFrames();
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
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
