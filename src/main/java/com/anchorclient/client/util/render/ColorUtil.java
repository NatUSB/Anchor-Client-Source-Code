package com.anchorclient.client.util.render;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorUtil {

    /**
     * Linearly interpolates between 2 colors
     * @param color1 The starting color
     * @param color2 The ending color
     * @param offset The interpolation percentage
     * @return The outputted color
     */
    public int interpolate(int color1, int color2, float offset) {
        if (offset > 1)
            offset = 1 - offset % 1;

        double invert = 1 - offset;
        int r = (int) ((color1 >> 16 & 0xFF) * invert + (color2 >> 16 & 0xFF) * offset);
        int g = (int) ((color1 >> 8 & 0xFF) * invert + (color2 >> 8 & 0xFF) * offset);
        int b = (int) ((color1 & 0xFF) * invert + (color2 & 0xFF) * offset);
        int a = (int) ((color1 >> 24 & 0xFF) * invert + (color2 >> 24 & 0xFF) * offset);
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

}
