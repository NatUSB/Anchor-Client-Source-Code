package com.anchorclient.client.ui.elements;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.ui.elements.animatedString.AnimatedChar;
import com.anchorclient.client.ui.elements.animatedString.AnimatedString;
import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.misc.MathUtil;
import com.anchorclient.client.util.render.ColorUtil;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class SearchBar {

    private float x, y, width, height;
    private boolean active;

    private AnimatedString as;
    private Animation blinkerAnimation, activeAnimation;

    public SearchBar(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.as = new AnimatedString(Easing.EASE_OUT_CUBIC);
        this.blinkerAnimation = new Animation(500, 0, 1, Easing.EASE_OUT_CUBIC);
        this.activeAnimation = new Animation(1, 0, 0);
        this.activeAnimation.setValue(0);
    }

    public void render(float opacity) {
        if(blinkerAnimation.isDone() && blinkerAnimation.getEnd() == 1) {
            blinkerAnimation = new Animation(500, 1, 0, Easing.EASE_IN_CUBIC);
        }

        if(blinkerAnimation.isDone() && blinkerAnimation.getEnd() == 0) {
            blinkerAnimation = new Animation(500, 0, 1, Easing.EASE_OUT_CUBIC);
        }

        if(!active && blinkerAnimation.getEnd() == 1) {
            blinkerAnimation = new Animation(500, blinkerAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
        }

        if(Float.isNaN(activeAnimation.getValue())) {
            activeAnimation = new Animation(1, active ? 1 : 0, active ? 1 : 0);
            activeAnimation.setValue(active ? 1 : 0);
        }

        TTFFontRenderer font = AnchorClient.INSTANCE.fontManager.getFont("anchor 16");

        DrawingUtil.drawOutlinedRoundedRect(x, y, width, height, 5, (float) MathUtil.interpolate(1, 2, activeAnimation.getValue()), new Color(150, 150, 150, (int) MathUtil.interpolate(0, 200, opacity)).getRGB());

        DrawingUtil.drawRoundedRect(x, y, width, height, 5, new Color(40, 40, 40, (int) MathUtil.interpolate(0, 160, opacity)).getRGB());

        DrawingUtil.drawImg("Anchor/images/search.png", x + 2, y + 3, height - 4, height - 4);

        if(as.getText().isEmpty()) {
            font.drawString("Search...", x + 16, y + 5, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, opacity)).getRGB());
        } else {
            as.render(font, x + 16, y + 5, new Color(255, 255, 255, (int) MathUtil.interpolate(0, 255, opacity)).getRGB());
            DrawingUtil.drawRoundedRect(x + 16 + as.getWidth(font) + 1.5f, y + 2, 1, height - 4, 2, ColorUtil.interpolate(0x00ffffff, 0xffffffff, blinkerAnimation.getValue()));
        }
    }

    public void mouseClicked(float mouseX, float mouseY, int button) {
        if(button == 0) {
            active = HoveredUtil.isRectHovered(x, y, width, height, mouseX, mouseY);

            if(active) {
                activeAnimation = new Animation(250, activeAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            } else {
                activeAnimation = new Animation(250, activeAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }
    }

    public void keyTyped(char keyTyped, int keyCode) {
        if(active) {
            if(keyCode == Keyboard.KEY_BACK) {
                if(!as.getChars().isEmpty()) {
                    ArrayList<AnimatedChar> chars = new ArrayList<>();

                    for(AnimatedChar ac : as.getChars())
                        if(ac.getAnimation().getEnd() == 1)
                            chars.add(ac);

                    if(chars.size() - 1 >= 0)
                        as.removeChar(chars.size() - 1);
                }
            }

            if (ChatAllowedCharacters.isAllowedCharacter(keyTyped)) {
                as.addChar(String.valueOf(keyTyped), as.getChars().size());
            }
        }
    }

}
