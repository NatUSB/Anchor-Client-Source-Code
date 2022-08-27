package com.anchorclient.client.ui.clickgui;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.ui.LayoutEditorMenu;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGui extends GuiScreen {

    private Animation opacityAnimation;

    private Category currentCategory;
    private final ArrayList<ModuleFrame> moduleFrames = new ArrayList<>();

    private ScrollHelper scrollHelper;

    private SearchBar searchBar;

    private float hudOffset = 28;

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);

        float width = 770;
        float height = 400;

        opacityAnimation = new Animation(250, 0, 1, Easing.EASE_OUT_CUBIC);
        currentCategory = Category.ALL;

        scrollHelper = new ScrollHelper(calculateMaxScroll(), 500);

        searchBar = new SearchBar((sr.getScaledWidth()/2f - width/2f + 412) + hudOffset, sr.getScaledHeight()/2f - height/2f + 98, 190, 16);

        initializeFrames();

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
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
        DrawingUtil.drawShadow(sr.getScaledWidth()/2f - (width/2f - 100), (sr.getScaledHeight()/2f - height/2f) + 38, width - 200, height - (63 + 16), 17, 6, 2);

        // Top Bar
        DrawingUtil.drawRoundedRect(sr.getScaledWidth()/2f - (width/2f - 100), (sr.getScaledHeight()/2f - height/2f) + 38, width - 200, 45, 17, 17, 0, 0, new ColorBuilder(0xff1f1e20).setAlphaPct(75, this.opacityAnimation.getValue()).getRGB());

        TTFFontRenderer topBarCFont = AnchorClient.INSTANCE.fontManager.getFont("anchor 25");
        DrawingUtil.drawOutlinedRoundedRect(sr.getScaledWidth()/2f - 120, sr.getScaledHeight()/2f - height/2f + 49, 120/1.4f, 33/1.4f, 7, 2, new Color(150, 150, 150, (int) MathUtil.interpolate(0, 200, this.opacityAnimation.getValue())).getRGB());
        DrawingUtil.drawRoundedRect(sr.getScaledWidth()/2f - 120, sr.getScaledHeight()/2f - height/2f + 49, 120/1.4f, 33/1.4f, 7, new Color(125, 125, 125, (int) MathUtil.interpolate(0, 200, this.opacityAnimation.getValue())).getRGB());
        topBarCFont.drawCenteredString("MODS", (sr.getScaledWidth()/2f - 120) + ((120/1.4f)/2f), sr.getScaledHeight()/2f - height/2.012f + 49 + (33/1.4f)/2f - topBarCFont.getHeight("MODS")/2f, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, this.opacityAnimation.getValue())).getRGB());

        DrawingUtil.drawOutlinedRoundedRect(sr.getScaledWidth()/2f + (120 - (120/1.4f)), sr.getScaledHeight()/2f - height/2f + 49, 120/1.4f, 33/1.4f, 7, 2, new Color(150, 150, 150, (int) MathUtil.interpolate(0, 200, this.opacityAnimation.getValue())).getRGB());
        DrawingUtil.drawRoundedRect(sr.getScaledWidth()/2f + (120 - (120/1.4f)), sr.getScaledHeight()/2f - height/2f + 49, 120/1.4f, 33/1.4f, 7, new Color(125, 125, 125, (int) MathUtil.interpolate(0, 200, this.opacityAnimation.getValue())).getRGB());
        topBarCFont.drawCenteredString("SETTINGS", (sr.getScaledWidth()/2f + (120 - (120/1.4f)) + ((120/1.4f)/2f)), sr.getScaledHeight()/2f - height/2.012f + 49 + (33/1.4f)/2f - topBarCFont.getHeight("SETTINGS")/2f, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, this.opacityAnimation.getValue())).getRGB());

        DrawingUtil.drawOutlinedRoundedRect(sr.getScaledWidth()/2f + 250, sr.getScaledHeight()/2f - height/2f + 49, 33/1.4f, 33/1.4f, 7, 2, new Color(150, 150, 150, (int) MathUtil.interpolate(0, 200, this.opacityAnimation.getValue())).getRGB());
        DrawingUtil.drawRoundedRect(sr.getScaledWidth()/2f + 250, sr.getScaledHeight()/2f - height/2f + 49, 33/1.4f, 33/1.4f, 7, new Color(125, 125, 125, (int) MathUtil.interpolate(0, 200, this.opacityAnimation.getValue())).getRGB());
        DrawingUtil.drawImgWithShadow("Anchor/images/close.png", sr.getScaledWidth()/2f + 250 + (((33/1.4f)/2) - (18/1.4f)/2), (sr.getScaledHeight()/2f - height/2f + 49.5f) + (((33/1.4f)/2) - (18/1.4f)/2), 18/1.4f, 18/1.4f, 6, 2, -1);

        // Logo
        DrawingUtil.drawImgWithShadow("Anchor/icon.png", sr.getScaledWidth()/2f - 30/1.7f, sr.getScaledHeight()/2f - height/2f + 43, 60/1.7f, 60/1.7f, 6, 2, -1);

        // Main UI
        DrawingUtil.drawRoundedRect(sr.getScaledWidth()/2f - (width/2f - 100), sr.getScaledHeight()/2f - height/2f + (63 + 20), width - 200, height - (63 + 60), 0, 0, 17, 17, new ColorBuilder(0xff3c3c3c).setAlphaPct(75, this.opacityAnimation.getValue()).getRGB());

        // Scrollbar
        DrawingUtil.drawRoundedRect(width - 16, sr.getScaledHeight()/2f - height/2f + 128f + (-scrollHelper.getScrollAnimation().getValue()), 4, 93.5f, 4, 4, 4, 4, new ColorBuilder(0xffffff).setAlphaPct(75, this.opacityAnimation.getValue()).getRGB());


        int cCount = 0;
        for(Category c : Category.values()) {
            float offset = (cCount * 80);

            TTFFontRenderer font = AnchorClient.INSTANCE.fontManager.getFont("anchor 20");

            DrawingUtil.drawShadow((sr.getScaledWidth()/2f - width/2f + 92 + offset) + hudOffset, sr.getScaledHeight()/2f - height/2f + 98, 72, 16, 4, 3, 1);
            DrawingUtil.drawRoundedRect((sr.getScaledWidth()/2f - width/2f + 92 + offset) + hudOffset, sr.getScaledHeight()/2f - height/2f + 98, 72, 16, 4, c == currentCategory ? new ColorBuilder(0xff333b56).setAlphaPct(50, this.opacityAnimation.getValue()).getRGB() : new ColorBuilder(0xff42454e).setAlphaPct(50, this.opacityAnimation.getValue()).getRGB());
            font.drawCenteredString(c.name, (sr.getScaledWidth()/2f - width/2f + 92 + offset + 72/2f) + hudOffset, sr.getScaledHeight()/2f - height/2f + 98 + 8 - font.getHeight(c.name)/2f + 1, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, this.opacityAnimation.getValue())).getRGB());

            cCount++;
        }

        // Search Bar
        searchBar.render(this.opacityAnimation.getValue());

        // Editor button
        DrawingUtil.drawImg("Anchor/images/editormenu.png", (sr.getScaledWidth()/2f - width/2f + 609) + hudOffset, sr.getScaledHeight()/2f - height/2f + 98, 16, 16);

        GLUtil.scissor(sr.getScaledWidth()/2f - width/2f, sr.getScaledHeight()/2f - height/4.5f + 15, width, height - 178, () -> {
            for(ModuleFrame mf : moduleFrames) {
                mf.render(scrollHelper.getScrollAnimation().getValue() + 2, mouseX, mouseY, this.opacityAnimation.getValue());
            }
        });
    }

    private float calculateMaxScroll() {return 120;}

    private void initializeFrames(String query) {
        ScaledResolution sr = new ScaledResolution(mc);

        this.scrollHelper.setHVal(0);
        this.scrollHelper.setScrollAnimation(new Animation(500, this.scrollHelper.getScrollAnimation().getValue(), 0, Easing.EASE_OUT_CUBIC));

        moduleFrames.clear();

        float width = 770;
        float height = 400;

        float firstOffset = 0;
        float secondOffset = 0;
        float thirdOffset = 0;
        float fourthOffset = 0;

        float moduleOffset = 115; // Vertical Frame Spacing

        int mCount = 0;
        for(Module m : AnchorClient.INSTANCE.moduleManager.getModulesInCategory(currentCategory)) {
            if(!m.isVisible()) continue; // Skip over the module if it's not supposed to show on the clickgui

            if(!query.isEmpty()) {
                if (!m.getName().toLowerCase().contains(query.toLowerCase())) continue;
            }


            boolean first = (mCount + 1) % 4 == 1;
            boolean second = (mCount + 1) % 4 == 2;
            boolean third = (mCount + 1) % 4 == 3;
            boolean fourth = (mCount + 1) % 4 == 0;


            if(first) {
                moduleFrames.add(new ModuleFrame((sr.getScaledWidth()/2f - 293) + hudOffset, sr.getScaledHeight()/2f - height/2f + 126 + firstOffset, m));
                firstOffset += moduleOffset;
            }

            if(second) {
                moduleFrames.add(new ModuleFrame((sr.getScaledWidth()/2f - 143 - 14) + hudOffset, sr.getScaledHeight()/2f - height/2f + 126 + secondOffset, m));
                secondOffset += moduleOffset;
            }

            if(third) {
                moduleFrames.add(new ModuleFrame((sr.getScaledWidth()/2f + 7 - 28) + hudOffset, sr.getScaledHeight()/2f - height/2f + 126 + thirdOffset, m));
                thirdOffset += moduleOffset;
            }

            if(fourth) {
                moduleFrames.add(new ModuleFrame((sr.getScaledWidth()/2f + 157 - 42) + hudOffset, sr.getScaledHeight()/2f - height/2f + 126 + fourthOffset, m));
                fourthOffset += moduleOffset;
            }

            mCount++;
        }
    }

    private void initializeFrames() {
        initializeFrames("");
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);

        float width = 770;
        float height = 400;

        for(ModuleFrame mf : moduleFrames) {
            mf.onMouseClick(this.scrollHelper.getScrollAnimation().getValue(), mouseX, mouseY, mouseButton);
        }

        int cCount = 0;
        for(Category c : Category.values()) {
            float offset = (cCount * 80);

            if(HoveredUtil.isRectHovered((sr.getScaledWidth()/2f - width/2f + 92 + offset) + hudOffset, sr.getScaledHeight()/2f - height/2f + 98, 72, 16, mouseX, mouseY)) {
                this.currentCategory = c;
                initializeFrames();
            }

            cCount++;
        }
        searchBar.mouseClicked(mouseX, mouseY, mouseButton);

        if(HoveredUtil.isRectHovered(sr.getScaledWidth()/2f + 250, sr.getScaledHeight()/2f - height/2f + 49, 33/1.4f, 33/1.4f, mouseX, mouseY)) {
            mc.displayGuiScreen(null);
        }
        if(HoveredUtil.isRectHovered((sr.getScaledWidth()/2f - width/2f + 609) + hudOffset, sr.getScaledHeight()/2f - height/2f + 98, 16, 16, mouseX, mouseY)) {
            mc.displayGuiScreen(new LayoutEditorMenu());
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
}
