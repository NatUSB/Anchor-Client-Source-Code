package com.anchorclient.client.util.render;

import com.anchorclient.client.util.render.shader.impl.GaussianShader;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@UtilityClass
public class BlurUtil {

    private final GaussianShader shader = new GaussianShader();

    /**
     * Blurs the specified area
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param radius The blur radius
     */
    public void blur(float x, float y, float width, float height, float radius) {
        StencilUtil.initStencilToWrite();
        DrawingUtil.drawRect(x, y, width, height, -1);
        StencilUtil.readStencilBuffer(1);

        shader.drawBlur(radius);

        StencilUtil.uninitStencilBuffer();
    }

    /**
     * Blurs the entire screen
     * @param radius The blur radius
     */
    public void blur(float radius) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        blur(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), radius);
    }

}
