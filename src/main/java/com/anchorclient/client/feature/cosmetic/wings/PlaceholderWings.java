package com.anchorclient.client.feature.cosmetic.wings;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;

public class PlaceholderWings extends Cosmetic {

    public PlaceholderWings() {
        super("Placeholder Wing", "Wings For Holding A Place", CosmeticCategory.WING);

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
