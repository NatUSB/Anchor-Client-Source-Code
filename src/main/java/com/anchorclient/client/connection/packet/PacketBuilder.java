package com.anchorclient.client.connection.packet;

import com.anchorclient.client.connection.packet.impl.client.CPacketHandshake;
import com.anchorclient.client.connection.packet.impl.server.SPacketKick;
import com.anchorclient.client.connection.packet.impl.server.SPacketPlayerCount;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PacketBuilder {

    private static final Gson gson = new Gson();
    private static final JsonParser parser = new JsonParser();
    private static final Map<String, Class<?>> PACKET_REGISTRY = new HashMap<>();

    static {
        PACKET_REGISTRY.put("CPacketHandshake", CPacketHandshake.class);
        PACKET_REGISTRY.put("SPacketPlayerCount", SPacketPlayerCount.class);
        PACKET_REGISTRY.put("SPacketKick", SPacketKick.class);    }

    public static Packet build(String json) {
        JsonObject obj = parser.parse(json).getAsJsonObject();
        String id = obj.get("id").getAsString();
        return (Packet) gson.fromJson(json, getClassByID(id));
    }

    public static String encode(Packet p) {
        JsonObject object = new JsonObject();
        object.addProperty("id", getIDByClass(p.getClass()));
        JsonObject otherObj = parser.parse(gson.toJson(p)).getAsJsonObject();
        for (Map.Entry<String, JsonElement> setObj : otherObj.entrySet()) {
            object.add(setObj.getKey(), setObj.getValue());
        }
        return object + "";
    }

    public static Class<?> getClassByID(String id) {
        return PACKET_REGISTRY.get(id);
    }

    public static String getIDByClass(Class<?> clazz) {
        return Objects.requireNonNull(PACKET_REGISTRY.entrySet().stream().filter(c -> c.getValue() == clazz).findFirst().orElse(null)).getKey();
    }

}
