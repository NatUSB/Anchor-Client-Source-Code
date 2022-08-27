package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;

public class Crosshair extends Module {

    public Crosshair() {
        super("Crosshair", "Allows you to customize the crosshair", "crosshair.png", Category.HUD);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event<Event> e) {

    }

}
