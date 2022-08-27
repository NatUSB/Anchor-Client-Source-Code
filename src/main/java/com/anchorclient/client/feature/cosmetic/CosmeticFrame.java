package com.anchorclient.client.feature.cosmetic;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.ui.elements.HoverAnimation;
import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.misc.MathUtil;
import com.anchorclient.client.util.render.ColorBuilder;
import com.anchorclient.client.util.render.ColorUtil;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.GLUtil;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;

import static com.anchorclient.client.ui.LoadingScreen.mc;

@Getter
public class CosmeticFrame {

    private final float x;
    private float y;
    private final Cosmetic cosmetic;
    private final ArrayList<CosmeticFrame> cosmeticFrames = new ArrayList<>();
    float scale = 1.1f;
    float fWidth = 137f/scale;
    float fHeight = 60f/scale;
    float fOffset = 28f/scale;

    private final HoverAnimation toggleHoverAnimation, settingsHoverAnimation;

    public CosmeticFrame(float x, float y, Cosmetic cosmetic) {
        this.x = x;
        this.y = y;
        this.cosmetic = cosmetic;
        this.toggleHoverAnimation = new HoverAnimation(x + 1, y + 42, 108/scale, 27/scale, 500);
        this.settingsHoverAnimation = new HoverAnimation(x + 109/scale, y + 42, 27/scale, 27/scale, 500);

    }

    public void render(float yOffset, float mouseX, float mouseY, float opacity) {
        ScaledResolution sr = new ScaledResolution(mc);
        TTFFontRenderer font = AnchorClient.INSTANCE.fontManager.getFont("anchor 22");

        this.toggleHoverAnimation.update(mouseX, mouseY);
        this.settingsHoverAnimation.update(mouseX, mouseY);

        this.toggleHoverAnimation.setY(y + 42 + yOffset);
        this.settingsHoverAnimation.setY(y + 42  + yOffset);

        DrawingUtil.drawOutlinedRoundedRect(x, y + yOffset, fWidth, fHeight, 17, 1, new ColorBuilder(0xffffffff).setAlphaPct(20, opacity).getRGB());
       // DrawingUtil.drawRoundedRect(x, y + yOffset, fWidth, fHeight - fOffset, 17, 17, 0, 0, new ColorBuilder(0xff747474).setAlphaPct(50, opacity).getRGB());
        font.drawCenteredString(cosmetic.getName(), x + fWidth/2f, y + 8 + yOffset, new ColorBuilder(0xffffffff).setAlphaPct(70, opacity).getRGB());

       // DrawingUtil.drawRoundedRect(x + 109/scale, y + 78 + yOffset, 27/scale, 27/scale, 0, 0, 0, 17, ColorUtil.interpolate(new ColorBuilder(0xff767676).setAlphaPct(70, opacity).getRGB(), new ColorBuilder(0xff707070).setAlphaPct(60, opacity).getRGB(), this.settingsHoverAnimation.getOutput()));

        int color = ColorUtil.interpolate(cosmetic.isEnabled() ? new ColorBuilder(0xff5aa049).setAlphaPct(80, opacity).getRGB() : new ColorBuilder(0xffbd4545).setAlphaPct(80, opacity).getRGB(), cosmetic.isEnabled() ? new ColorBuilder(0xff4d8a3e).setAlphaPct(80, opacity).getRGB() : new ColorBuilder(0xff9e3737).setAlphaPct(80, opacity).getRGB(), this.toggleHoverAnimation.getOutput());
        DrawingUtil.drawRoundedRect(x + 1, y + 42 + yOffset, 135/scale, 13/scale, 0, 0, 17, 17, color); //y + 78 // height 27/scale // draws bg of on off

        GlStateManager.pushMatrix();
        GlStateManager.popMatrix();

        //TTFFontRenderer stateText = AnchorClient.INSTANCE.fontManager.getFont("anchor 22");
        //stateText.drawCenteredString(cosmetic.isEnabled() ? "ON" : "OFF", x + (140/scale)/2f, y + (55/scale) + 3.5f - stateText.getHeight(cosmetic.isEnabled() ? "ON" : "OFF")/2f + yOffset, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, opacity)).getRGB()); //y + (98/scale)
    }

    public void onMouseClick(float yOffset, float mouseX, float mouseY, int button) {
        if(button == 0) {
            if(HoveredUtil.isRectHovered(x, y + 45 + yOffset, 111/1.1f, 27/1.1f, mouseX, mouseY)) {
                cosmetic.toggle();
            }
        }
    }
}
