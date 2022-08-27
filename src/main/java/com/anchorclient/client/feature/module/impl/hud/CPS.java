package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.feature.module.features.DraggableModule;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class CPS extends DraggableModule {
    private List<Long> allCLicks = new ArrayList<Long>();
    private long lastPressed;
    private boolean isPressed;

    public CPS() {
        super("CPS", "Displays your clicks per second", "cps.png", Category.MECHANIC);
    }

    private int getCPS() {
        final long time = System.currentTimeMillis();
        this.allCLicks.removeIf(aLong -> aLong + 1000 < time);
        return this.allCLicks.size();
    }

    @Override
    public void onEnable() {
        final long time = System.currentTimeMillis();
        this.allCLicks.removeIf(aLong -> aLong + 1000 < time);
    }

    @Override
    public void onDisable() {
        final long time = System.currentTimeMillis();
        this.allCLicks.removeIf(aLong -> aLong < time);
    }

    @Override
    public void onEvent(Event<Event> e) {
        final boolean pressed = Mouse.isButtonDown(0);
        if(pressed != this.isPressed) {
            this.lastPressed = System.currentTimeMillis();
            this.isPressed = pressed;
            if(pressed) {
                this.allCLicks.add(this.lastPressed);
            }
        }
        setText("CPS: " + getCPS());
    }
}
