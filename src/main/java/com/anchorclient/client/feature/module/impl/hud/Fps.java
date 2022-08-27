package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.features.DraggableModule;
import net.minecraft.client.Minecraft;

public class FPS extends DraggableModule {

    public FPS() {
        super("FPS", "Displays your FPS on the HUD", "fps.png", Category.HUD);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event<Event> e) {
        setText(Minecraft.getDebugFPS() + " FPS");
    }

}
