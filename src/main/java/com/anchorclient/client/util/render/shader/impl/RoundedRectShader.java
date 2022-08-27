package com.anchorclient.client.util.render.shader.impl;

import com.anchorclient.client.util.render.shader.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class RoundedRectShader extends Shader {

    public RoundedRectShader() {
        super(
                "#version 120\n" +
                "\n" +
                "void main() {\n" +
                "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n" +
                "}",

                "#version 120\n" +
                        "\n" +
                        "uniform vec2 location, size;\n" +
                        "uniform vec4 radius;\n" +
                        "uniform float r, g, b, a, shadowBlur;\n" +
                        "\n" +
                        "float roundedBoxSDF(vec2 CenterPosition, vec2 Size, vec4 Radius) {\n" +
                        "    Radius.xy = (CenterPosition.x>0.0)?Radius.xy : Radius.zw;\n" +
                        "    Radius.x  = (CenterPosition.y>0.0)?Radius.x  : Radius.y;\n" +
                        "    \n" +
                        "    vec2 q = abs(CenterPosition)-Size+Radius.x;\n" +
                        "    return min(max(q.x,q.y),0.0) + length(max(q,0.0)) - Radius.x;\n" +
                        "}\n" +
                        "\n" +
                        "void main() {\n" +
                        "    float edgeSoftness   = 1.0;\n" +
                        "    float distance \t = roundedBoxSDF(gl_FragCoord.xy - location - size/2.0, size / 2.0, radius);\n" +
                        "    float smoothedAlpha  =  1.0-smoothstep(0.0, edgeSoftness * 2.0, distance);\n" +
                        "    vec4 quadColor\t = mix(vec4(r, g, b, 0), vec4(r, g, b, smoothedAlpha*a), smoothedAlpha*a);\n" +
                        "    float shadowSoftness = shadowBlur;\n" +
                        "    float shadowDistance = roundedBoxSDF(gl_FragCoord.xy - location - (size/2.0), size / 2.0, radius);\n" +
                        "    float shadowAlpha \t = 1.0-smoothstep(-shadowSoftness, shadowSoftness, shadowDistance);\n" +
                        "    gl_FragColor \t\t = mix(quadColor, vec4(r, g, b, a), shadowAlpha - smoothedAlpha);\n" +
                        "}");
    }

    public void drawShader(float x, float y, float width, float height, float rtl, float rtr, float rbl, float rbr, float shadowBlur, float r, float g, float b, float a) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);

        start();

        setupUniformVariables(x, y, width, height, rtl, rtr, rbl, rbr, shadowBlur, r, g, b, a);

        if(shadowBlur > 0) {
            // If we are using a shadow we want to make room on the canvas, so it is rendered
            drawCanvas(x - 30, y - 30, width + 60, height + 60);
        } else {
            drawCanvas(x - 4, y - 4, width + 8, height + 8);
        }

        stop();
//        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public void setupUniformVariables(float x, float y, float width, float height, float rtl, float rtr, float rbl, float rbr, float shadowBlur, float r, float g, float b, float a) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        setUniform2f("location", (x)*sr.getScaleFactor(), (((sr.getScaledHeight() - height) - y)-1)*sr.getScaleFactor());
        setUniform2f("size", width*sr.getScaleFactor(), height*sr.getScaleFactor());
        setUniform4f("radius", rtr*sr.getScaleFactor()/2, rbr*sr.getScaleFactor()/2, rtl*sr.getScaleFactor()/2, rbl*sr.getScaleFactor()/2);
        setUniform1f("r", r);
        setUniform1f("g", g);
        setUniform1f("b", b);
        setUniform1f("a", a);
        setUniform1f("shadowBlur", shadowBlur);
    }

}
