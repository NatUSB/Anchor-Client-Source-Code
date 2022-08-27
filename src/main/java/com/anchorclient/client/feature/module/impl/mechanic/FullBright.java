package com.anchorclient.client.feature.module.impl.mechanic;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;

public class FullBright extends Module {

    public FullBright() {
        super("Fullbright", "makes your gamma bigger so it's easier to see", "fullbright.png", Category.MECHANIC);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1f;
    }

    @Override
    public void onEvent(Event<Event> e) {
        if (mc.gameSettings.gammaSetting == 1f || mc.gameSettings.gammaSetting < 1f) {
            mc.gameSettings.gammaSetting = 100f;
        }
    }
}
