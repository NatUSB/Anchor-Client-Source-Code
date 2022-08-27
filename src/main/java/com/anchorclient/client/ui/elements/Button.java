package com.anchorclient.client.ui.elements;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.render.ColorUtil;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@Getter
public class Button {

    private String text;
    private ResourceLocation image;
    private final float x, y, width, height;
    private Animation hoverAnimation;
    private final int color;
    private final int hoverColor;

    private boolean textType, imageType;

    public Button(String text, float x, float y, float width, float height, int color, int hoverColor) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hoverColor = hoverColor;

        this.hoverAnimation = new Animation(1, 0, 0);
        this.hoverAnimation.setValue(0);

        this.textType = true;
    }

    public Button(ResourceLocation loc, float x, float y, float width, float height, int color, int hoverColor) {
        this.image = loc;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hoverColor = hoverColor;

        this.hoverAnimation = new Animation(1, 0, 0);
        this.hoverAnimation.setValue(0);

        this.imageType = true;
    }

    /**
     * Renders the button to the UI
     * @param mouseX Current mouse x position
     * @param mouseY Current mouse y position
     */
    public void render(float mouseX, float mouseY) {
        // Hover Animation
        if(HoveredUtil.isRectHovered(this.x, this.y, this.width, this.height, mouseX, mouseY)) {
            if(hoverAnimation.getEnd() == 0) {
                hoverAnimation = new Animation(500, hoverAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            }
        } else {
            if(hoverAnimation.getEnd() == 1) {
                hoverAnimation = new Animation(500, hoverAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }

        if(textType) {
            TTFFontRenderer font = AnchorClient.INSTANCE.fontManager.getFont("anchor 22");
            font.drawCenteredString(this.text, this.x + this.width/2, this.y + this.height/2 - font.getHeight(this.text)/2 + 1, -1);
        }

        if(imageType) {
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.image);
            Gui.drawModalRectWithCustomSizedTexture((int) (this.x + this.width/2 - 7.2f), (int) (this.y + this.height/2 - 7), 16, 16, 16, 16, 16, 16);
        }

        DrawingUtil.drawRoundedRect(this.x, this.y, this.width, this.height, 10, ColorUtil.interpolate(this.color, this.hoverColor, this.hoverAnimation.getValue()));
    }

}
