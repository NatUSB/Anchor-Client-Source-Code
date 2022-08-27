package com.anchorclient.client.feature.module.impl.mechanic;

import com.anchorclient.client.AnchorClient;
import com.anchorclient.client.feature.cosmetic.capes.*;
import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.event.impl.EventUpdate;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;
import net.minecraft.util.ResourceLocation;

public class Cosmetics extends Module {
    public Cosmetics() {
        super("Cosmetics", "Turns On Cosmetics", Category.MECHANIC);
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event<Event> e) {
        if(AnchorClient.INSTANCE.cosmeticManager.getCosmetic(BetaCape.class).isEnabled()) {
            if(e instanceof EventUpdate) {
                mc.thePlayer.setLocationOfCape(new ResourceLocation("Anchor/capes/Beta.png"));
            }
        }
        if(AnchorClient.INSTANCE.cosmeticManager.getCosmetic(ResoluteCape.class).isEnabled()) {
            if(e instanceof EventUpdate) {
                mc.thePlayer.setLocationOfCape(new ResourceLocation("Anchor/capes/resolute.png"));
            }
        }
        if(AnchorClient.INSTANCE.cosmeticManager.getCosmetic(AnchorCape.class).isEnabled()) {
            if(e instanceof EventUpdate) {
                mc.thePlayer.setLocationOfCape(new ResourceLocation("Anchor/capes/Anchor.png"));
            }
        }
        if(AnchorClient.INSTANCE.cosmeticManager.getCosmetic(FishingCape.class).isEnabled()) {
            if(e instanceof EventUpdate) {
                mc.thePlayer.setLocationOfCape(new ResourceLocation("Anchor/capes/Fishing.png"));
            }
        }
        if(AnchorClient.INSTANCE.cosmeticManager.getCosmetic(PlaceholderCape.class).isEnabled()) {
            if(e instanceof EventUpdate) {
                mc.thePlayer.setLocationOfCape(new ResourceLocation("Anchor/capes/cape.png"));
            }
        }
        if(AnchorClient.INSTANCE.cosmeticManager.getCosmetic(ChristmasCape.class).isEnabled()) {
            if(e instanceof EventUpdate) {
                mc.thePlayer.setLocationOfCape(new ResourceLocation("Anchor/capes/Christmas_2010_Cape.png"));
            }
        }
    }
}
