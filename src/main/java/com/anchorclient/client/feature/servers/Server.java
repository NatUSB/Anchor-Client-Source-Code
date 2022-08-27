package com.anchorclient.client.feature.servers;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.multiplayer.ServerData;

@Getter
@Setter
public class Server extends ServerData {

    private String name, ip;
    private ServerType type;

    public Server(String name, String ip, ServerType type) {
        super(name, ip, false);

        this.name = name;
        this.ip = ip;
        this.type = type;
    }

}
