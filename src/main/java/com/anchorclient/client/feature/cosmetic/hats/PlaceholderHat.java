package com.anchorclient.client.feature.cosmetic.hats;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;

public class PlaceholderHat extends Cosmetic {

    public PlaceholderHat() {
        super("Placeholder Hat", "Hat For Holding A Place", CosmeticCategory.HAT);

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
