package com.anchorclient.client.feature.cosmetic;

public enum CosmeticCategory {

    ALL("ALL"),
    CAPE("CAPE"),
    HAT("HAT"),
    PETS("PETS"),
    WING("WING"),
    OTHER("OTHER");

    public final String name;

    CosmeticCategory(String name) {
        this.name = name;
    }

}
