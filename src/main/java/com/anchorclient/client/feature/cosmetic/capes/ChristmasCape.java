package com.anchorclient.client.feature.cosmetic.capes;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.event.impl.EventUpdate;
import net.minecraft.util.ResourceLocation;

public class ChristmasCape extends Cosmetic {
    public ChristmasCape() {
        super("Christmas Cape", "Christmas Cape", CosmeticCategory.CAPE);
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
