package com.anchorclient.client.util.render;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

@UtilityClass
public class RenderUtil {

    /**
     * Sets up the given framebuffer
     * @param framebuffer The framebuffer
     * @return The setup framebuffer
     */
    public Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != Minecraft.getMinecraft().displayWidth || framebuffer.framebufferHeight != Minecraft.getMinecraft().displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, true);
        }
        return framebuffer;
    }

}
