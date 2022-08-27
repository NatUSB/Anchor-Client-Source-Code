package com.anchorclient.client.ui.elements;

import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrollHelper {

    private Animation scrollAnimation;
    private float maxScroll;
    private int hVal, speed;

    public ScrollHelper(float maxScroll, int speed) {
        this.maxScroll = maxScroll;
        this.speed = speed;
        this.scrollAnimation = new Animation(1, 0, 0);
        this.scrollAnimation.setValue(0);
    }

    public void onScroll(float offset) {
        if(Math.abs(this.hVal + offset) > this.maxScroll) return;

        if(!(hVal + offset > 0)) {
            this.hVal += offset;
            this.scrollAnimation = new Animation(this.speed, this.scrollAnimation.getValue(), this.hVal, Easing.EASE_OUT_CUBIC);
        }
    }

    public void update() {
        if(Float.isNaN(this.scrollAnimation.getValue())) {
            this.scrollAnimation = new Animation(1, this.hVal, this.hVal);
            this.scrollAnimation.setValue(this.hVal);
        }
    }

}
