package com.anchorclient.client.util.render;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

@UtilityClass
public class GLUtil {

    /**
     * Rotates and draws the given GL context
     * @param x The origin x position
     * @param y The origin y position
     * @param rotate The rotation in degrees
     * @param f The GL context
     */
    public void rotate(float x, float y, float rotate, Runnable f) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.rotate(rotate, 0, 0, -1);
        GlStateManager.translate(-x, -y, 0);
        f.run();
        GlStateManager.popMatrix();
    }

    /**
     * Scales and draws the given GL context
     * @param x The origin x position
     * @param y The origin y position
     * @param scale The scale
     * @param f The GL context
     */
    public void scale(float x, float y, float scale, Runnable f) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x,y,1);
        GL11.glScalef(scale,scale,1);
        GL11.glTranslatef(-x,-y,1);
        f.run();
        GL11.glPopMatrix();
    }

    /**
     * Translates and draws the given GL context
     * @param x The x amount
     * @param y The y amount
     * @param f The GL context
     */
    public void translate(float x, float y, Runnable f) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 1);
        f.run();
        GL11.glTranslatef(-x, -y, 1);
        GL11.glPopMatrix();
    }

    /**
     * Scissors the given area and draws the given GL context
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The width
     * @param f The GL context
     */
    public void scissor(float x, float y, float width, float height, Runnable f) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        x = x * res.getScaleFactor();
        height = height * res.getScaleFactor();
        y = Minecraft.getMinecraft().displayHeight - (y * res.getScaleFactor()) - height;
        width = width * res.getScaleFactor();
        GL11.glScissor((int) x, (int) y, (int) width, (int) height);
        f.run();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

}
