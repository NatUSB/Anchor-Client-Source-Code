package com.anchorclient.client.feature.cosmetic;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.util.render.animation.Animation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

@Getter
@Setter
public abstract class Cosmetic {

    private String name, desc;
    private CosmeticCategory category;
    private boolean enabled, visible;
    private int key;
    private Animation toggleAnimation;

    public Minecraft mc = Minecraft.getMinecraft();

    public Cosmetic(String name, String desc, CosmeticCategory cosmeticCategory, int key) {
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.key = key;
        this.visible = true;
    }

    public Cosmetic(String name, String desc, CosmeticCategory category) {
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.visible = true;
    }

    /**
     * Called when the module is enabled
     */
    public abstract void onEnable();

    /**
     * Called when the module is disabled
     */
    public abstract void onDisable();

    /**
     * Called when an event is fired
     * @param e The event fired
     */
    public abstract void onEvent(Event<Event> e);

    /**
     * Toggles the module
     */
    public void toggle() {
        enabled = !enabled;

        if(enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

}
