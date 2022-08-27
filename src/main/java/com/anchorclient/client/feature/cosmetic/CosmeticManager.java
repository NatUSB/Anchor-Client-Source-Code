package com.anchorclient.client.feature.cosmetic;

import com.anchorclient.client.feature.cosmetic.capes.*;
import com.anchorclient.client.feature.cosmetic.hats.*;
import com.anchorclient.client.feature.cosmetic.pets.*;
import com.anchorclient.client.feature.cosmetic.wings.*;
import com.anchorclient.client.feature.cosmetic.others.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class CosmeticManager {

    private final CopyOnWriteArrayList<Cosmetic> cosmetic = new CopyOnWriteArrayList<>();

    /**
     * Initializes all the cosmetics.
     */
    public CosmeticManager() {
        addCosmetics(
                //CAPE
                new PlaceholderCape(),
                new BetaCape(),
                new ChristmasCape(),
                new ResoluteCape(),
                new AnchorCape(),
                new FishingCape(),
                //HAT
                new PlaceholderHat(),
                //PET
                new PlaceholderPet(),
                //WING
                new PlaceholderWings(),
                //OTHER
                new PlaceholderOther()
        );
    }

    /**
     * Adds the specified cosmetics to the client.
     * @param cosmetic The specified cosmetics to add.
     */
    public void addCosmetics(Cosmetic... cosmetic) {
        this.cosmetic.addAll(Arrays.asList(cosmetic));
    }

    /**
     * Returns all the cosmetics.
     * @return cosmetic.
     */
    public List<Cosmetic> getCosmetics() {
        return this.cosmetic;
    }

    /**
     * @param clazz The class of the cosmetic to return
     * @return The cosmetic retrieved.
     */
    public Cosmetic getCosmetic(Class<?> clazz) {
        return cosmetic.stream().filter(m -> clazz.equals(m.getClass())).findFirst().orElse(null);
    }

    /**
     * Returns a cosmetic by its name
     * @param cosmetic Name of cosmetic.
     * @return The cosmetic.
     */
    public Cosmetic getCosmeticByName(String cosmetic) {
        return this.getCosmetics().stream().filter(m -> cosmetic.equalsIgnoreCase(m.getName())).findFirst().orElse(null);
    }

    /**
     * Returns all the cosmetics inside a certain category.
     * @param c The Category to search.
     * @return Retrieved cosmetics.
     */
    public List<Cosmetic> getCosmeticsInCategory(CosmeticCategory c) {
        if(c == CosmeticCategory.ALL) {
            return getCosmetics();
        }

        return this.cosmetic.stream().filter(m -> m.getCategory() == c).collect(Collectors.toList());
    }

    /**
     * Returns all the current enabled cosmetics.
     * @return The cosmetics.
     */
    public List<Cosmetic> getEnabledCosmetics() {
        return this.getCosmetics().stream().filter(Cosmetic::isEnabled).collect(Collectors.toList());
    }
}
