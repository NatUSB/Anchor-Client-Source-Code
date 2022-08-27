package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;

public class ArmorStatus extends Module {

    public ArmorStatus() {
        super("Armor Status", "Displays information about your armor on the HUD", "armorstatus.png", Category.HUD);
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
