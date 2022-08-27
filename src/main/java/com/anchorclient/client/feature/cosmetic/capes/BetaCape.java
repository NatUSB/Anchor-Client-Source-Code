package com.anchorclient.client.feature.cosmetic.capes;

import com.anchorclient.client.feature.cosmetic.Cosmetic;
import com.anchorclient.client.feature.cosmetic.CosmeticCategory;
import com.anchorclient.client.feature.event.Event;
import net.minecraft.util.ResourceLocation;

public class BetaCape extends Cosmetic {
    public BetaCape() {
        super("Beta Cape", "Cape For Beta Users", CosmeticCategory.CAPE);
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
