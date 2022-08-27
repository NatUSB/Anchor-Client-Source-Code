package com.anchorclient.client.feature.cosmetic.capes;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;
import net.minecraft.util.ResourceLocation;

public class AnchorCape extends Cosmetic {
    public AnchorCape() {
        super("Anchor Cape", "Anchor Special Cape", CosmeticCategory.CAPE);
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
