package com.anchorclient.client.feature.module.impl.mechanic;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;

public class Zoom extends Module {

    public Zoom() {
        super("Zoom", "Zooms like a telescope!", "zoom.png", Category.MECHANIC);
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
