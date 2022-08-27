package com.anchorclient.client.feature.servers.impl.featured;

import com.anchorclient.client.feature.servers.Server;
import com.anchorclient.client.feature.servers.ServerType;

public class PikaNetwork extends Server {
    public PikaNetwork() {
        super("Pika Network", "smc.pika.host", ServerType.FEATURED);
    }
}
