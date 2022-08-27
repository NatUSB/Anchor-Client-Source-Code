package com.anchorclient.client.feature.module.impl.mechanic;


import com.anchorclient.client.feature.event.Event;
import com.anchorclient.client.feature.event.impl.EventPacket;
import com.anchorclient.client.feature.module.Category;
import com.anchorclient.client.feature.module.Module;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.util.ChatComponentText;

public class NickHider extends Module {
    public NickHider() {
        super("NameHider", "Hides you ingame name", "nickhider.png", Category.MECHANIC);
    }

    public void onEvent(Event e) {
        if (e instanceof EventPacket) {

            EventPacket packetEvent = (EventPacket) e;
            if (packetEvent.packet instanceof S02PacketChat) {

                S02PacketChat packet = (S02PacketChat) packetEvent.packet;

                if (packet.getChatComponent().getUnformattedText().replaceAll("", "").contains(mc.getSession().getUsername())) {
                    packet.chatComponent = new ChatComponentText(packet.getChatComponent().getFormattedText().replaceAll("", "").replaceAll(mc.getSession().getUsername(), "You"));
                }

            }
            else if (packetEvent.packet instanceof S3CPacketUpdateScore) {
                S3CPacketUpdateScore packet = (S3CPacketUpdateScore) packetEvent.packet;

                if (packet.getObjectiveName().replaceAll("", "").contains(mc.getSession().getUsername())){
                    packet.setObjective(packet.getObjectiveName().replaceAll("", "").replaceAll(mc.getSession().getUsername(), "You"));
                }

                if (packet.getPlayerName().replaceAll("", "").contains(mc.getSession().getUsername())){
                    packet.setName(packet.getPlayerName().replaceAll("", "").replaceAll(mc.getSession().getUsername(), "You"));
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}