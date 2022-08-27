package com.anchorclient.client.feature.module.impl.mechanic;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.event.impl.EventUpdate;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;

public class AutoSprint extends Module {

    public AutoSprint() {
        super("Auto Sprint", "Automatically sprints for you", "autosprint.png", Category.MECHANIC);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSprint.pressed = false;
    }

    @Override
    public void onEvent(Event<Event> e) {
        if(e instanceof EventUpdate) {
            mc.gameSettings.keyBindSprint.pressed = true;
        }
    }

}
