package com.anchorclient.client.feature.cosmetic.pets;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;

public class PlaceholderPet extends Cosmetic {

    public PlaceholderPet() {
        super("Placeholder Pet", "Pet For Holding A Place", CosmeticCategory.PETS);

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
