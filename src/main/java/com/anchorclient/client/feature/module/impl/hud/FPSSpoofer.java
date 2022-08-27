package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.features.DraggableModule;

public class FPSSpoofer extends DraggableModule {
    public FPSSpoofer() {
        super("FPS Spoofer", "Displays Fake FPS IT DOESN'T GIVE YOU EXTRA FPS, your fps + 30", "", Category.HUD);
    }

    public void onEvent(Event<Event> e) {
        setText("FPS: 30" + mc.getDebugFPS());
    }

}
