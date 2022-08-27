package com.anchorclient.client.feature.module;

public enum Category {

    ALL("ALL"),
    HUD("HUD"),
    SERVER("SERVER"),
    MECHANIC("MECHANIC");

    public final String name;

    Category(String name) {
        this.name = name;
    }

}
