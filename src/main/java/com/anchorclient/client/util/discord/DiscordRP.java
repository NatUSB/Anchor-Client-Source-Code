package com.anchorclient.client.util.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.anchorclient.client.util.misc.ScheduleUtil;

import java.util.concurrent.TimeUnit;

public class DiscordRP {

    public DiscordRichPresence presence;
    public DiscordRPC discordRPC;

    public boolean running;


     // Starts the Discord Rich Presence

    public void start() {
        System.out.println("Starting Discord RPC");
        presence = new DiscordRichPresence();
        discordRPC = DiscordRPC.INSTANCE;
        String applicationID = "971346169219399700";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Discord RPC Started with user: " + user.username + "#" + user.discriminator);
        discordRPC.Discord_Initialize(applicationID, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        running = true;
        ScheduleUtil.schedule(() -> {
            if(!Thread.currentThread().isInterrupted() && running) {
                discordRPC.Discord_RunCallbacks();
            } else {
                discordRPC.Discord_Shutdown();
                running = false;
            }
        }, 500, TimeUnit.MILLISECONDS);
        updatePresence("Starting...", "");
    }
    /**
     * Updates the presence with the specified details
     * @param l1 Line 1
     * @param l2 Line 2
     **/
    public void updatePresence(String l1, String l2) {
        presence.details = l1;
        presence.state = l2;
        presence.largeImageKey = "logo";
        presence.largeImageText = "Anchor Client";
        discordRPC.Discord_UpdatePresence(presence);
    }

}
