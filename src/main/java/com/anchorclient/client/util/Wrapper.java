package com.anchorclient.client.util;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.ui.KickMenu;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;

@UtilityClass
public class Wrapper {

    public boolean shouldKickUser = false;
    public String kickReason = "";

    public void onEvent(Event<Event> e) {
        for(Module m : AnchorClient.INSTANCE.moduleManager.getEnabledModules()) {
            m.onEvent(e);
        }
    }

    public void onKeyPress(int key) {
        for(Module m : AnchorClient.INSTANCE.moduleManager.getModules()) {
            if(key == m.getKey()) {
                m.toggle();
            }
        }
    }


    public void onGameLoop() {
        if(shouldKickUser && !(Minecraft.getMinecraft().currentScreen instanceof KickMenu)) {
            Minecraft.getMinecraft().displayGuiScreen(new KickMenu(kickReason));
        }
    }

}
