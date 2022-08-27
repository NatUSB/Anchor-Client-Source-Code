package com.anchorclient.client.ui.elements;

import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoverAnimation {

    private float x, y, width, height, output;
    private int duration;
    private Animation hoverAnimation;

    public HoverAnimation(float x, float y, float width, float height, int duration) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.hoverAnimation = new Animation(1, 0, 0);
        this.hoverAnimation.setValue(0);
    }

    public void update(float mouseX, float mouseY) {
        if(HoveredUtil.isRectHovered(x, y, width, height, mouseX, mouseY)) {
            if(hoverAnimation.getEnd() == 0) {
                hoverAnimation = new Animation(500, hoverAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            }
        } else {
            if(hoverAnimation.getEnd() == 1) {
                hoverAnimation = new Animation(500, hoverAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }

        output = hoverAnimation.getValue();
    }

}
