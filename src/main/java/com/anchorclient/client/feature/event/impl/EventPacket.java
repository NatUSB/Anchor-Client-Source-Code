package com.anchorclient.client.feature.event.impl;
import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.event.*;
import net.minecraft.network.Packet;

public class EventPacket extends Event<EventPacket> {
    public static Packet packet;
    private final EventDirection direction;

    public EventPacket(Packet packet, EventDirection direction) {
        this.packet = packet;
        this.direction = direction;
    }


    public <T extends Packet> T getPacket() {
        return (T) packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}