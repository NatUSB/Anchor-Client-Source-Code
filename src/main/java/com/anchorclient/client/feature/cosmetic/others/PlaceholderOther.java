package com.anchorclient.client.feature.cosmetic.others;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;

public class PlaceholderOther extends Cosmetic {

    public PlaceholderOther() {
        super("Placeholder Other", "Other For Holding A Place", CosmeticCategory.OTHER);

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
