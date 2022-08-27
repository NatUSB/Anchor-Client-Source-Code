package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.ui.clickgui.ClickGui;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("Click GUI", "Customize the Click GUI", "", Category.HUD, Keyboard.KEY_RSHIFT);
        this.setVisible(false);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGui());
        setEnabled(false);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event<Event> e) {

    }

}
