package com.anchorclient.client.ui.elements.animatedString;

import com.anchorclient.client.util.misc.MathUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class AnimatedChar {

    private String c;
    private Animation animation;

    public AnimatedChar(String c, int duration, Easing e) {
        this.c = c;
        this.animation = new Animation(duration, 0, 1, e);
    }

    public void render(TTFFontRenderer font, float x, float y, int color) {
        int r = (color >> 16 & 255);
        int g = (color >> 8 & 255);
        int b = (color & 255);
        int a = (color >> 24 & 255);

        if(Float.isNaN(animation.getValue())) {
            animation = new Animation(500, 0, 1, Easing.EASE_OUT_CUBIC);
        }

        font.drawString(c, x, y, new Color(r, g, b, (int) MathUtil.interpolate(0, a, animation.getValue())).getRGB());
    }

}
