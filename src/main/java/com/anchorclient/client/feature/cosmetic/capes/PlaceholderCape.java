package com.anchorclient.client.feature.cosmetic.capes;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;

public class PlaceholderCape extends Cosmetic {

    public PlaceholderCape() {
        super("Placeholder Cape", "Cape For Holding A Place", CosmeticCategory.CAPE);

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
