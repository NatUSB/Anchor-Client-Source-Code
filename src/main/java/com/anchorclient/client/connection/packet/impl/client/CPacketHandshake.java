package com.anchorclient.client.connection.packet.impl.client;

import com.anchorclient.client.connection.packet.Packet;
import lombok.Getter;

@Getter
public class CPacketHandshake extends Packet {

    private final String uuid, name;

    public CPacketHandshake(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void process() {
    }

}
