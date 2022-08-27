package com.anchorclient.client.feature.module.features;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;
import com.anchorclient.client.util.render.DrawingUtil;
import com.anchorclient.client.util.render.animation.Animation;
import com.anchorclient.client.util.render.font.TTFFontRenderer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraggableModule extends Module {

    private float x, y, prevX, prevY, width, height, scale, padding;
    private String text, icon;
    private boolean renderBackground, dragging;
    private Animation configMenuHoverAnimation;

    public TTFFontRenderer font;

    public DraggableModule(String name, String desc, String icon, Category category, int key) {
        super(name, desc, icon, category, key);

        this.x = 5;
        this.y = 5;
        this.width = 10;
        this.height = 10;
        this.scale = 1;
        this.padding = 4;
        this.text = "";
        this.icon = icon;
        this.renderBackground = true;

        this.font = AnchorClient.INSTANCE.fontManager.getFont("normal 20");
    }

    public DraggableModule(String name, String desc, String icon, Category category) {
        super(name, desc, icon, category);

        this.x = 5;
        this.y = 5;
        this.width = 10;
        this.height = 10;
        this.scale = 1;
        this.padding = 4;
        this.text = "";
        this.icon = icon;
        this.renderBackground = true;

        this.font = AnchorClient.INSTANCE.fontManager.getFont("normal 20");
    }

    public void onEnable() {  }
    public void onDisable() {  }
    public void onEvent(Event<Event> e) {  }

    public void drawBackground() {
        if(this.renderBackground) {
            setWidth(font.getWidth(text) + padding);
            setHeight(font.getHeight(text) + padding);

            DrawingUtil.drawRect(x, y, width, height, 0x70000000);
            font.drawString(text, x + padding/2, y + padding/2, -1);
        }
    }

}
