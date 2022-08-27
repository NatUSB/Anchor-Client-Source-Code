package com.anchorclient.client.util.render.shader.impl;

import com.anchorclient.client.util.misc.MathUtil;
import com.anchorclient.client.util.render.RenderUtil;
import com.anchorclient.client.util.render.shader.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public class BloomShader extends Shader {

    public Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public BloomShader() {
        super("#version 120\n" +
                "\n" +
                "void main() {\n" +
                "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n" +
                "}",
                "#version 120\n" +
                        "\n" +
                        "uniform vec3 color;\n" +
                        "uniform sampler2D inTexture, textureToCheck;\n" +
                        "uniform vec2 texelSize, direction;\n" +
                        "uniform float radius;\n" +
                        "uniform float weights[256];\n" +
                        "\n" +
                        "#define offset texelSize * direction\n" +
                        "\n" +
                        "void main() {\n" +
                        "    if (direction.y > 0 && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n" +
                        "    float blr = texture2D(inTexture, gl_TexCoord[0].st).a * weights[0];\n" +
                        "\n" +
                        "    for (float f = 1.0; f <= radius; f++) {\n" +
                        "        blr += texture2D(inTexture, gl_TexCoord[0].st + f * offset).a * (weights[int(abs(f))]);\n" +
                        "        blr += texture2D(inTexture, gl_TexCoord[0].st - f * offset).a * (weights[int(abs(f))]);\n" +
                        "    }\n" +
                        "\n" +
                        "    gl_FragColor = vec4(color, blr);\n" +
                        "}");
    }

    public void bloom(int texture, float radius, float offset, int color) {
        framebuffer = RenderUtil.createFrameBuffer(framebuffer);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.0f);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; i++) {
            weightBuffer.put(MathUtil.calculateGaussianValue(i, radius));
        }
        weightBuffer.rewind();

        GlStateManager.alphaFunc(GL_GREATER, (float) (0.0F * .01));

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        start();
        setupUniformVariables(radius, (int) offset, 0, weightBuffer, color);
        glBindTexture(GL_TEXTURE_2D, texture);
        drawCanvas();
        stop();
        framebuffer.unbindFramebuffer();

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);

        start();
        setupUniformVariables(radius, 0, (int) offset, weightBuffer, color);
        glActiveTexture(GL_TEXTURE16);
        glBindTexture(GL_TEXTURE_2D, texture);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, framebuffer.framebufferTexture);
        drawCanvas();
        stop();

        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableAlpha();

        GlStateManager.bindTexture(0);
    }

    public void setupUniformVariables(float radius, int directionX, int directionY, FloatBuffer weights, int color) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        setUniform3f("color", r, g, b);
        setUniform1i("inTexture", 0);
        setUniform1i("textureToCheck", 16);
        setUniform1f("radius", radius);
        setUniform2f("texelSize", 1.0F / (float) Minecraft.getMinecraft().displayWidth, 1.0F / (float) Minecraft.getMinecraft().displayHeight);
        setUniform2f("direction", directionX, directionY);
        glUniform1(getUniform("weights"), weights);
    }

}
