package com.anchorclient.client.util.render;

import com.anchorclient.client.util.render.shader.impl.OutlinedRoundedRectShader;
import com.anchorclient.client.util.render.shader.impl.RoundedRectShader;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@UtilityClass
public class DrawingUtil {

    private final RoundedRectShader roundedRectShader = new RoundedRectShader();
    private final OutlinedRoundedRectShader outlinedRoundedRectShader = new OutlinedRoundedRectShader();

    /**
     * Draws a normal rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param color The color
     */
    public void drawRect(float x, float y, float width, float height, int color) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        GL11.glColor4f(r, g, b, a);

        Gui.drawRect(x, y, x + width, y + height, color);
    }

    /**
     * Draws a rounded rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param radius The rounded radius
     * @param color The color
     */
    public void drawRoundedRect(float x, float y, float width, float height, float radius, int color) {
        drawRoundedRect(x, y, width, height, radius, radius, radius, radius, 0, color);
    }

    /**
     * Draws a rounded rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param rtl The top left radius
     * @param rtr The top right radius
     * @param rbl The bottom left radius
     * @param rbr The button right radius
     * @param color The color
     */
    public void drawRoundedRect(float x, float y, float width, float height, float rtl, float rtr, float rbl, float rbr, int color) {
        drawRoundedRect(x, y, width, height, rtl, rtr, rbl, rbr, 0, color);
    }

    /**
     * Draws a rounded rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param radius The rounded radius
     * @param shadowBlur The shadow blur amount
     * @param color The color
     */
    public void drawRoundedRect(float x, float y, float width, float height, float radius, float shadowBlur, int color) {
        drawRoundedRect(x, y, width, height, radius, radius, radius, radius, shadowBlur, color);
    }

    /**
     * Draws a rounded rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param rtl The top left radius
     * @param rtr The top right radius
     * @param rbl The bottom left radius
     * @param rbr The button right radius
     * @param shadowBlur The shadow blur amount
     * @param color The color
     */
    public void drawRoundedRect(float x, float y, float width, float height, float rtl, float rtr, float rbl, float rbr, float shadowBlur, int color) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        roundedRectShader.drawShader(x, y, width, height, rtl, rtr, rbl, rbr, shadowBlur, r, g, b, a);
    }

    /**
     * Draws a rounded outlined rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param radius The rounded radius
     * @param thickness The thickness
     * @param color The color
     */
    public void drawOutlinedRoundedRect(float x, float y, float width, float height, float radius, float thickness, int color) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        outlinedRoundedRectShader.drawShader(x, y, width, height, radius, thickness, r, g, b, a);
    }

    /**
     * Draws an outlined rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param thickness The thickness
     * @param color The color
     */
    public void drawOutlinedRect(float x, float y, float width, float height, float thickness, int color) {
        drawRect(x - thickness, y - thickness, width + thickness*2, thickness, color);
        drawRect(x + width, y, thickness, height, color);
        drawRect(x - thickness, y + height, width + thickness*2, thickness, color);
        drawRect(x - thickness, y - thickness, thickness, height + thickness*2, color);
    }

    /**
     * Draws a shadow around a rounded rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param roundedRadius The rounded radius
     * @param shadowRadius The shadow radius
     * @param offset The shadow offset
     * @param color The color
     */
    public void drawShadow(float x, float y, float width, float height, float roundedRadius, float shadowRadius, float offset, int color) {
        BloomUtil.bloom(shadowRadius, offset, color, () -> drawRoundedRect(x, y, width, height, roundedRadius, 0xff000000));
    }

    /**
     * Draws a shadow around a rounded rectangle
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param roundedRadius The rounded radius
     * @param shadowRadius The shadow radius
     * @param offset The shadow offset
     */
    public void drawShadow(float x, float y, float width, float height, float roundedRadius, float shadowRadius, float offset) {
        drawShadow(x, y, width, height, roundedRadius, shadowRadius, offset, 0xff000000);
    }

    /**
     * Draws an image
     * @param loc The image location
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     */
    public void drawImg(String loc, float x, float y, float width, float height) {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(loc));
        Gui.drawModalRectWithCustomSizedTexture(x, y, width, height, (int) width, (int) height, width, height);
    }

    /**
     * Draws am image with a shadow
     * @param loc The image location
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param radius The shadow radius
     * @param offset The shadow offset
     * @param color The shadow color
     */
    public void drawImgWithShadow(String loc, float x, float y, float width, float height, float radius, float offset, int color) {
        BloomUtil.bloom(radius, offset, color, () -> {
            drawImg(loc, x, y, width, height);
        });

        drawImg(loc, x, y, width, height);
    }

    /**
     * Draws am image with a shadow
     * @param loc The image location
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param radius The shadow radius
     * @param offset The shadow offset
     */
    public void drawImgWithShadow(String loc, float x, float y, float width, float height, float radius, float offset) {
        drawImgWithShadow(loc, x, y, width, height, radius, offset, 0xff000000);
    }

}
