package com.anchorclient.client.feature.servers;

import com.anchorclient.client.feature.servers.impl.featured.*;
import com.anchorclient.client.feature.servers.impl.partner.Anchor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerManager {

    public ArrayList<Server> servers = new ArrayList<>();

    public ServerManager() {
        // Featured
        servers.add(new Hypixel());
        servers.add(new PixelEdge());
        servers.add(new PikaNetwork());
        servers.add(new JartexNetwork());
        servers.add(new BedwarsPractice());
        servers.add(new CavePVP());
        servers.add(new MinemenClub());

        // Partnered
        servers.add(new Anchor());
    }

    public List<Server> getServersByType(ServerType type) {
        return this.servers.stream().filter(s -> s.getType() == type).collect(Collectors.toList());
    }

}
