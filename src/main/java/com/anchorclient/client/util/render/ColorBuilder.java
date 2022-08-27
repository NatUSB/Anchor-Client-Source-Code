package com.anchorclient.client.util.render;

import com.anchorclient.client.util.misc.MathUtil;

import java.awt.*;

public class ColorBuilder {

    public int r, g, b, a = 255;

    public ColorBuilder(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColorBuilder(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public ColorBuilder(int color) {
        this.r = (color >> 16 & 255);
        this.g = (color >> 8 & 255);
        this.b = (color & 255);
        this.a = (color >> 24 & 255);
    }

    /**
     * Sets the alpha percentage
     * @param percent percentage
     * @return The color-builder
     */
    public ColorBuilder setAlphaPct(float percent) {
        this.a = (int) MathUtil.interpolate(0, 255, percent/100);
        return this;
    }

    /**
     * Sets the alpha percentage based off of another interpolation value
     * @param percent percentage
     * @param interpolationVal second interpolation value
     * @return The color-builder
     */
    public ColorBuilder setAlphaPct(float percent, float interpolationVal) {
        this.a = (int) MathUtil.interpolate(0, MathUtil.interpolate(0, 255, percent/100), interpolationVal);
        return this;
    }

    /**
     * Returns the color as an integer
     * @return The color
     */
    public int getRGB() {
        return new Color(r, g, b, a).getRGB();
    }

}
