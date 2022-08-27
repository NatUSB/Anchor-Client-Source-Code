package com.anchorclient.client.feature.module.impl.hud;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.features.DraggableModule;
import net.minecraft.client.Minecraft;

public class Ping extends DraggableModule {

    public Ping() {
        super("Ping", "Displays your ping on the HUD", "ping.png", Category.HUD);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event<Event> e) {
        if(Minecraft.getMinecraft().isSingleplayer()) {
            setText("Singleplayer");
        } else {
            setText(getPing() + " ms");
        }
    }

    private int getPing() {
        if(mc.thePlayer == null || mc.theWorld == null || this.mc.getNetHandler().getPlayerInfo(mc.thePlayer.getGameProfile().getId()) == null || mc.isSingleplayer()) return 0;
        return this.mc.getNetHandler().getPlayerInfo(mc.thePlayer.getGameProfile().getId()).getResponseTime();
    }

}
