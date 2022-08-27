package com.anchorclient.client.ui.elements.animatedString;

import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class AnimatedString {

    private CopyOnWriteArrayList<AnimatedChar> chars = new CopyOnWriteArrayList<>();

    private Easing easing;

    public AnimatedString(Easing easing) {
        this.easing = easing;
    }

    public void addChar(String c, int index) {
        chars.add(index, new AnimatedChar(c, 500, easing));
    }

    public void removeChar(int index) {
        AnimatedChar c = chars.get(index);
        c.setAnimation(new Animation(500, c.getAnimation().getValue(), 0, easing));
    }

    public void render(TTFFontRenderer font, float x, float y, int color) {
        int xOff = 0;
        for(AnimatedChar ac : chars) {
            if(ac.getAnimation().getEnd() == 0 && ac.getAnimation().getValue() == 0) {
                chars.remove(ac);
            }

            ac.render(font, x + xOff, y, color);

            xOff += font.getWidth(ac.getC()) - 1;
        }
    }

    public float getWidth(TTFFontRenderer font) {
        int xOff = 0;
        for(AnimatedChar ac : chars)
            if(ac.getAnimation().getEnd() == 1)
                xOff += font.getWidth(ac.getC()) - 1;

        return xOff;
    }

    public String getText() {
        StringBuilder text = new StringBuilder();

        for(AnimatedChar ac : chars)
            if(ac.getAnimation().getEnd() == 1)
                text.append(ac.getC());

        return text.toString();
    }

}
