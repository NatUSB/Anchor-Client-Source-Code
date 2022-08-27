package com.anchorclient.client.util.render;

import com.anchorclient.client.util.render.shader.impl.BloomShader;
import lombok.experimental.UtilityClass;
import net.minecraft.client.shader.Framebuffer;

@UtilityClass
public class BloomUtil {

    private Framebuffer bloomFramebuffer = new Framebuffer(1, 1, false);

    private final BloomShader shader = new BloomShader();

    /**
     * Blooms the specified GL context
     * @param radius The bloom radius
     * @param offset The bloom offset
     * @param color The bloom color
     * @param data The GL context
     */
    public void bloom(float radius, float offset, int color, Runnable data) {
        bloomFramebuffer = RenderUtil.createFrameBuffer(bloomFramebuffer);

        bloomFramebuffer.framebufferClear();
        bloomFramebuffer.bindFramebuffer(true);
        data.run();
        bloomFramebuffer.unbindFramebuffer();

        shader.bloom(bloomFramebuffer.framebufferTexture, radius, offset, color);
    }

    /**
     * Blooms the specified GL context
     * @param radius The bloom radius
     * @param offset The bloom offset
     * @param data The GL context
     */
    public void bloom(float radius, float offset, Runnable data) {
        bloom(radius, offset, 0xff000000, data);
    }

}
