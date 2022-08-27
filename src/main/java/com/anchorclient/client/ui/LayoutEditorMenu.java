package com.anchorclient.client.ui;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.feature.module.features.DraggableModule;
import com.anchorclient.client.ui.clickgui.ClickGui;
import com.anchorclient.client.util.misc.HoveredUtil;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import com.anchorclient.client.util.render.font.FontManager;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class LayoutEditorMenu extends GuiScreen {

    public Animation blurAnimation;

    @Override
    public void initGui() {
        blurAnimation = new Animation(200, 10, 0, Easing.EASE_IN_CUBIC);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        TTFFontRenderer info = AnchorClient.INSTANCE.fontManager.getFont("Verdana");
        info.drawCenteredString("Drag Modules To Your Needs. Press ESC to exit", sr.getScaledWidth()/2f, sr.getScaledHeight()/2.5f,  new Color(255, 255, 255, 255).getRGB());
//480, 200,
        for (Module m : AnchorClient.INSTANCE.moduleManager.getModules()) {
            if(!(m instanceof DraggableModule)) continue;
            DraggableModule module = (DraggableModule) m;

            if(!m.isEnabled())
                continue;

            if(module.getConfigMenuHoverAnimation() == null) {
                module.setConfigMenuHoverAnimation(new Animation(1, 0, 0));
                module.getConfigMenuHoverAnimation().setValue(0);
            }

            boolean mouseDown = Mouse.isButtonDown(0);

            boolean hovered = HoveredUtil.isRectHovered(module.getX(), module.getY(), module.getWidth(), module.getHeight(), mouseX, mouseY);

            if(hovered || module.isDragging()) {
                if(module.getConfigMenuHoverAnimation().getEnd() == 0) {
                    module.setConfigMenuHoverAnimation(new Animation(200, module.getConfigMenuHoverAnimation().getValue(), 255, Easing.EASE_OUT_CUBIC));
                }
            } else {
                if (module.getConfigMenuHoverAnimation().getEnd() == 255) {
                    module.setConfigMenuHoverAnimation(new Animation(200, module.getConfigMenuHoverAnimation().getValue(), 0, Easing.EASE_OUT_CUBIC));
                }
            }

            DrawingUtil.drawOutlinedRect(module.getX(), module.getY(), module.getWidth(), module.getHeight(),1, new Color(255, 255, 255, (int) module.getConfigMenuHoverAnimation().getValue()).getRGB());

            if(mouseDown) {
                if(module.isDragging()) {
                    float xx = mouseX + module.getPrevX();
                    float yy = mouseY + module.getPrevY();

                    if(xx < 0) {
                        xx = 0;
                    }

                    if(yy < 0) {
                        yy = 0;
                    }

                    if(xx + module.getWidth() > sr.getScaledWidth()) {
                        xx = sr.getScaledWidth() - module.getWidth();
                    }

                    if(yy + module.getHeight() > sr.getScaledHeight()) {
                        yy = sr.getScaledHeight() - module.getHeight();
                    }

                    module.setX(xx);
                    module.setY(yy);
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(new ClickGui());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Module m : AnchorClient.INSTANCE.moduleManager.getModules()) {
            if(!(m instanceof DraggableModule)) continue;
            DraggableModule module = (DraggableModule) m;
            if(!(m.isEnabled())) continue;

            boolean dragging = HoveredUtil.isRectHovered(module.getX(), module.getY(), module.getWidth(), module.getHeight(), mouseX, mouseY);
            module.setDragging(dragging);
            if(dragging) {
                module.setPrevX(module.getX() - mouseX);
                module.setPrevY(module.getY() - mouseY);
                break;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Module m : AnchorClient.INSTANCE.moduleManager.getModules()) {
            if(!(m instanceof DraggableModule)) continue;
            DraggableModule module = (DraggableModule) m;

            module.setDragging(false);
        }
    }

    @Override
    public void onGuiClosed() {

    }

}
