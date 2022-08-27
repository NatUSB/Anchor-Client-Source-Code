package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.event.impl.EventRender2D;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.features.DraggableModule;
import com.anchorclient.client.util.render.ColorUtil;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.animation.Easing;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import org.lwjgl.input.Keyboard;

public class Keystrokes extends DraggableModule {

    private Animation forwardAnimation;
    private Animation leftAnimation;
    private Animation backAnimation;
    private Animation rightAnimation;
    private Animation jumpAnimation;

    public Keystrokes() {
        super("Keystrokes", "Displays your movement keys on the HUD", "keystrokes.png", Category.HUD);

        this.forwardAnimation = new Animation(1, 0, 0);
        this.forwardAnimation.setValue(0);

        this.leftAnimation = new Animation(1, 0, 0);
        this.leftAnimation.setValue(0);

        this.backAnimation = new Animation(1, 0, 0);
        this.backAnimation.setValue(0);

        this.rightAnimation = new Animation(1, 0, 0);
        this.rightAnimation.setValue(0);

        this.jumpAnimation = new Animation(1, 0, 0);
        this.jumpAnimation.setValue(0);

        setRenderBackground(false);
        setWidth(83);
        setHeight(83);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event<Event> e) {
        if(e instanceof EventRender2D) {
            updateKeyAnimations();

            DrawingUtil.drawRoundedRect(this.getX() + 29, this.getY(), 25, 25, 8, ColorUtil.interpolate(0x90000000, 0x90ffffff, this.forwardAnimation.getValue())); // W
            DrawingUtil.drawRoundedRect(this.getX(), this.getY() + 29, 25, 25, 8, ColorUtil.interpolate(0x90000000, 0x90ffffff, this.leftAnimation.getValue())); // A
            DrawingUtil.drawRoundedRect(this.getX() + 29, this.getY() + 29, 25, 25, 8, ColorUtil.interpolate(0x90000000, 0x90ffffff, this.backAnimation.getValue())); // S
            DrawingUtil.drawRoundedRect(this.getX() + 58, this.getY() + 29, 25, 25, 8, ColorUtil.interpolate(0x90000000, 0x90ffffff, this.rightAnimation.getValue())); // D
            DrawingUtil.drawRoundedRect(this.getX(), this.getY() + 58, 83, 25, 8, ColorUtil.interpolate(0x90000000, 0x90ffffff, this.jumpAnimation.getValue())); // SPACE

            TTFFontRenderer font = AnchorClient.INSTANCE.fontManager.getFont("Verdana");

            int forwardKey = mc.gameSettings.keyBindForward.getKeyCode();
            int leftKey = mc.gameSettings.keyBindLeft.getKeyCode();
            int backKey = mc.gameSettings.keyBindBack.getKeyCode();
            int rightKey = mc.gameSettings.keyBindRight.getKeyCode();
            int jumpKey = mc.gameSettings.keyBindJump.getKeyCode();

            font.drawCenteredString(Keyboard.getKeyName(forwardKey), this.getX() + 41.5f, this.getY() + 25/2f - font.getHeight(Keyboard.getKeyName(forwardKey))/2f, ColorUtil.interpolate(-1, 0xff606060, this.forwardAnimation.getValue())); // W
            font.drawCenteredString(Keyboard.getKeyName(leftKey), this.getX() + 25/2f, this.getY() + 29 + 25/2f - font.getHeight(Keyboard.getKeyName(leftKey))/2f, ColorUtil.interpolate(-1, 0xff606060, this.leftAnimation.getValue())); // A
            font.drawCenteredString(Keyboard.getKeyName(backKey), this.getX() + 29 + 25/2f, this.getY() + 29 + 25/2f - font.getHeight(Keyboard.getKeyName(backKey))/2f, ColorUtil.interpolate(-1, 0xff606060, this.backAnimation.getValue())); // S
            font.drawCenteredString(Keyboard.getKeyName(rightKey), this.getX() + 58 + 25/2f, this.getY() + 29 + 25/2f - font.getHeight(Keyboard.getKeyName(rightKey))/2f, ColorUtil.interpolate(-1, 0xff606060, this.rightAnimation.getValue())); // D
            font.drawCenteredString(Keyboard.getKeyName(jumpKey), this.getX() + 83/2f, this.getY() + 58 + 25/2f - font.getHeight(Keyboard.getKeyName(jumpKey))/2f, ColorUtil.interpolate(-1, 0xff606060, this.jumpAnimation.getValue())); // SPACE
        }
    }

    private void updateKeyAnimations() {
        int forwardKey = mc.gameSettings.keyBindForward.getKeyCode();
        int leftKey = mc.gameSettings.keyBindLeft.getKeyCode();
        int backKey = mc.gameSettings.keyBindBack.getKeyCode();
        int rightKey = mc.gameSettings.keyBindRight.getKeyCode();
        int jumpKey = mc.gameSettings.keyBindJump.getKeyCode();

        if(Keyboard.isKeyDown(forwardKey)) {
            if(this.forwardAnimation.getEnd() == 0) {
                this.forwardAnimation = new Animation(150, this.forwardAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            }
        } else {
            if(this.forwardAnimation.getEnd() == 1) {
                this.forwardAnimation = new Animation(150, this.forwardAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }

        if(Keyboard.isKeyDown(leftKey)) {
            if(this.leftAnimation.getEnd() == 0) {
                this.leftAnimation = new Animation(150, this.leftAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            }
        } else {
            if(this.leftAnimation.getEnd() == 1) {
                this.leftAnimation = new Animation(150, this.leftAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }

        if(Keyboard.isKeyDown(backKey)) {
            if(this.backAnimation.getEnd() == 0) {
                this.backAnimation = new Animation(150, this.backAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            }
        } else {
            if(this.backAnimation.getEnd() == 1) {
                this.backAnimation = new Animation(150, this.backAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }

        if(Keyboard.isKeyDown(rightKey)) {
            if(this.rightAnimation.getEnd() == 0) {
                this.rightAnimation = new Animation(150, this.rightAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            }
        } else {
            if(this.rightAnimation.getEnd() == 1) {
                this.rightAnimation = new Animation(150, this.rightAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }

        if(Keyboard.isKeyDown(jumpKey)) {
            if(this.jumpAnimation.getEnd() == 0) {
                this.jumpAnimation = new Animation(150, this.jumpAnimation.getValue(), 1, Easing.EASE_OUT_CUBIC);
            }
        } else {
            if(this.jumpAnimation.getEnd() == 1) {
                this.jumpAnimation = new Animation(150, this.jumpAnimation.getValue(), 0, Easing.EASE_OUT_CUBIC);
            }
        }
    }

}
