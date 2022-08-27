package com.anchorclient.client.util.render.shader.impl;

import com.anchorclient.client.util.render.shader.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class OutlinedRoundedRectShader extends Shader {

    public OutlinedRoundedRectShader() {
        super("#version 120\n" +
                "\n" +
                "void main() {\n" +
                "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n" +
                "}",
                "uniform vec2 location, size;\n" +
                        "uniform float radius, thickness, r, g, b, a;\n" +
                        "\n" +
                        "float roundedSDF(vec2 centerPos, vec2 size, float radius) {\n" +
                        "    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n" +
                        "}\n" +
                        "\n" +
                        "void main() {    \n" +
                        "    float distance = roundedSDF(gl_FragCoord.xy - location - (size * .5), (size * .5) + (thickness *.5) - 1.0, radius);\n" +
                        "\n" +
                        "    float blendAmount = smoothstep(0., 2., abs(distance) - (thickness * .5));\n" +
                        "\n" +
                        "    vec4 insideColor = (distance < 0.) ? vec4(0, 0, 0, 0) : vec4(r, g, b,  0.0);\n" +
                        "    gl_FragColor = mix(vec4(r, g, b, a), vec4(0, 0, 0, 0), blendAmount);\n" +
                        "}");
    }

    public void drawShader(float x, float y, float width, float height, float radius, float thickness, float r, float g, float b, float a) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);

        start();

        setupUniformVariables(x, y, width, height, radius, thickness, r, g, b, a);

        drawCanvas(x - 4, y - 4, width + 8, height + 8);

        stop();
//        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public void setupUniformVariables(float x, float y, float width, float height, float radius, float thickness, float r, float g, float b, float a) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        setUniform2f("location", (x)*sr.getScaleFactor(), (((sr.getScaledHeight() - height) - y)-1)*sr.getScaleFactor());
        setUniform2f("size", width*sr.getScaleFactor(), height*sr.getScaleFactor());
        setUniform1f("radius", radius*sr.getScaleFactor()/2);
        setUniform1f("thickness", thickness*sr.getScaleFactor()/2);
        setUniform1f("r", r);
        setUniform1f("g", g);
        setUniform1f("b", b);
        setUniform1f("a", a);
    }

}
