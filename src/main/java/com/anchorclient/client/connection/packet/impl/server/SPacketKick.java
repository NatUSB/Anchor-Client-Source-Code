package com.anchorclient.client.connection.packet.impl.server;

import com.anchorclient.client.connection.packet.Packet;
import com.anchorclient.client.util.Wrapper;
import lombok.Getter;

@Getter
public class SPacketKick extends Packet {

    private final String reason;

    public SPacketKick(String reason) {
        this.reason = reason;
    }

    @Override
    public void process() {
        Wrapper.shouldKickUser = true;
        Wrapper.kickReason = this.reason;
    }

}
