package com.anchorclient.client.util.render.shader.impl;

import com.anchorclient.client.util.misc.MathUtil;
import com.anchorclient.client.util.render.RenderUtil;
import com.anchorclient.client.util.render.shader.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUniform1;

public class GaussianShader extends Shader {

    public Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public GaussianShader() {
        super("#version 120\n" +
                "\n" +
                "void main() {\n" +
                "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n" +
                "}",
                "#version 120\n" +
                        "\n" +
                        "uniform sampler2D textureIn;\n" +
                        "uniform vec2 texelSize, direction;\n" +
                        "uniform float radius;\n" +
                        "uniform float weights[256];\n" +
                        "\n" +
                        "#define offset texelSize * direction\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec3 blr = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];\n" +
                        "\n" +
                        "    for (float f = 1.0; f <= radius; f++) {\n" +
                        "        blr += texture2D(textureIn, gl_TexCoord[0].st + f * offset).rgb * (weights[int(abs(f))]);\n" +
                        "        blr += texture2D(textureIn, gl_TexCoord[0].st - f * offset).rgb * (weights[int(abs(f))]);\n" +
                        "    }\n" +
                        "\n" +
                        "    gl_FragColor = vec4(blr, 1.0);\n" +
                        "}");
    }

    public void drawBlur(float blurRadius) {
        glEnable(GL_BLEND);
        glColor4f(1, 1, 1, 1);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        framebuffer = RenderUtil.createFrameBuffer(framebuffer);

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        start();
        setupUniformVariables(1, 0, blurRadius);

        glBindTexture(GL_TEXTURE_2D, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);

        drawCanvas();
        framebuffer.unbindFramebuffer();
        stop();

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        start();
        setupUniformVariables(0, 1, blurRadius);

        glBindTexture(GL_TEXTURE_2D, framebuffer.framebufferTexture);
        drawCanvas();
        stop();

        glColor4f(0, 0, 0, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void setupUniformVariables(float dir1, float dir2, float radius) {
        setUniform1i("textureIn", 0);
        setUniform2f("texelSize", 1.0F / (float) Minecraft.getMinecraft().displayWidth, 1.0F / (float) Minecraft.getMinecraft().displayHeight);
        setUniform2f("direction", dir1, dir2);
        setUniform1f("radius", radius);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; i++) {
            weightBuffer.put(MathUtil.calculateGaussianValue(i, radius / 2));
        }

        weightBuffer.rewind();
        glUniform1(getUniform("weights"), weightBuffer);
    }

}
