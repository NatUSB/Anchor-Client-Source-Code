package com.anchorclient.client.ui.clickgui.elements;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.feature.module.impl.hud.Keystrokes;
import com.anchorclient.client.ui.elements.HoverAnimation;
import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.misc.MathUtil;
import com.anchorclient.client.util.render.ColorBuilder;
import com.anchorclient.client.util.render.ColorUtil;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.GLUtil;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

@Getter
public class ModuleFrame {

    private final float x, y;
    private final Module module;

    float scale = 1.1f;
    float fWidth = 137f/scale;
    float fHeight = 114f/scale;
    float fOffset = 28f/scale;

    private final HoverAnimation toggleHoverAnimation, settingsHoverAnimation;

    public ModuleFrame(float x, float y, Module module) {
        this.x = x;
        this.y = y;
        this.module = module;
        this.toggleHoverAnimation = new HoverAnimation(x + 1, y + 78, 108/scale, 27/scale, 500);
        this.settingsHoverAnimation = new HoverAnimation(x + 109/scale, y + 78, 27/scale, 27/scale, 500);
    }

    public void render(float yOffset, float mouseX, float mouseY, float opacity) {
        TTFFontRenderer font = AnchorClient.INSTANCE.fontManager.getFont("anchor 22");

        this.toggleHoverAnimation.update(mouseX, mouseY);
        this.settingsHoverAnimation.update(mouseX, mouseY);

        this.toggleHoverAnimation.setY(y + 78 + yOffset);
        this.settingsHoverAnimation.setY(y + 78 + yOffset);

        DrawingUtil.drawOutlinedRoundedRect(x, y + yOffset, fWidth, fHeight, 17, 1, new ColorBuilder(0xffffffff).setAlphaPct(20, opacity).getRGB());
        DrawingUtil.drawRoundedRect(x, y + yOffset, fWidth, fHeight - fOffset, 17, 17, 0, 0, new ColorBuilder(0xff747474).setAlphaPct(50, opacity).getRGB());
        font.drawCenteredString(module.getName(), x + fWidth/2f, y + 8 + yOffset, new ColorBuilder(0xffffffff).setAlphaPct(70, opacity).getRGB());

        DrawingUtil.drawRoundedRect(x + 109/scale, y + 78 + yOffset, 27/scale, 27/scale, 0, 0, 0, 17, ColorUtil.interpolate(new ColorBuilder(0xff767676).setAlphaPct(70, opacity).getRGB(), new ColorBuilder(0xff707070).setAlphaPct(60, opacity).getRGB(), this.settingsHoverAnimation.getOutput()));
        DrawingUtil.drawImg("Anchor/cog.png", x + 105.5f, y + 84.5f + yOffset, 13, 13);

        if(!(module.getIcon().isEmpty())) {
            if(module.getName().equalsIgnoreCase("Keystrokes")) {
                DrawingUtil.drawImg("Anchor/images/modules/" + module.getIcon(), x + fWidth/2 - (65/2f), y + fHeight - (64/2f) + yOffset - 56f, 64, 65);
            } else {
                DrawingUtil.drawImg("Anchor/images/modules/" + module.getIcon(), x + fWidth/2 - (36/2f), y + fHeight - (36/2f) + yOffset - 56f, 36, 36);
            }
        }

        int color = ColorUtil.interpolate(module.isEnabled() ? new ColorBuilder(0xff5aa049).setAlphaPct(80, opacity).getRGB() : new ColorBuilder(0xffbd4545).setAlphaPct(80, opacity).getRGB(), module.isEnabled() ? new ColorBuilder(0xff4d8a3e).setAlphaPct(80, opacity).getRGB() : new ColorBuilder(0xff9e3737).setAlphaPct(80, opacity).getRGB(), this.toggleHoverAnimation.getOutput());
        DrawingUtil.drawRoundedRect(x + 1, y + 78 + yOffset, 108/scale, 27/scale, 0, 0, 17, 0, color);

        TTFFontRenderer stateText = AnchorClient.INSTANCE.fontManager.getFont("anchor 22");
        stateText.drawCenteredString(module.isEnabled() ? "Enabled" : "Disabled", x + (108/scale)/2f, y + (98/scale) + 2.5f - stateText.getHeight(module.isEnabled() ? "Enabled" : "Disabled")/2f + yOffset, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, opacity)).getRGB());
    }

    public void onMouseClick(float yOffset, float mouseX, float mouseY, int button) {
        if(button == 0) {
            if(HoveredUtil.isRectHovered(x, y + 80 + yOffset, 111/1.1f, 27/1.1f, mouseX, mouseY)) {
                module.toggle();
            }
        }
    }

}
