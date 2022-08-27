package com.anchorclient.client.feature.module;

import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.util.render.animation.Animation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

@Getter
@Setter
public abstract class Module {

    private String name, desc, icon;
    private Category category;
    private boolean enabled, visible;
    private int key;
    private Animation toggleAnimation;

    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String desc, String icon, Category category ,int key) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.category = category;
        this.key = key;
        this.visible = true;
    }

    public Module(String name, String desc, String icon, Category category) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;
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
