package com.anchorclient.client;

import com.anchorclient.client.account.AccountManager;
import com.anchorclient.client.feature.cosmetic.CosmeticManager;
import com.anchorclient.client.feature.cosmetic.capes.BetaCape;
import com.anchorclient.client.feature.module.ModuleManager;
import com.anchorclient.client.feature.servers.ServerManager;
import com.anchorclient.client.connection.Connection;
import com.anchorclient.client.util.discord.DiscordRP;
import com.anchorclient.client.util.render.font.FontManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import static com.anchorclient.client.feature.module.impl.mechanic.ItemPhysics.mc;

public enum AnchorClient {
    INSTANCE;

    public final String version = "0.1.6-ALPHA-DEV";

    public FontManager fontManager;
    public ModuleManager moduleManager;
    public CosmeticManager cosmeticManager;
    public ServerManager serverManager;
    public AccountManager accountManager;
    public DiscordRP discordRP;

    public Connection connection;

    public void initializeMain() {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "false"); // Just in-case [log4j patch]

//        try {
//            this.connection = new Connection(new URI("ws://fi1.serversify.net:27010/"));
//            this.connection.connect();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

        //Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));
        System.out.println("Anchor Client | Main Initialized");

        Display.setTitle("Anchor Client | " + version);
    }

    public void initializeFont() {
        fontManager = new FontManager();
        System.out.println("Anchor Client | Font Manager Initialized");
    }
    public void initializeModules() {
        moduleManager = new ModuleManager();
        System.out.println("Anchor Client | Module Manager Initialized");
    }
    public void initializeCosmetics() {
        cosmeticManager = new CosmeticManager();
        System.out.println("Anchor Client | Cosmetic Manager Initialized");
    }
    public void initializeDiscordRPC() {
        discordRP = new DiscordRP();
        System.out.println("Anchor Client | Discord Rich Presence Initialized");
    }
    public void initializeServers() {
        serverManager = new ServerManager();
        System.out.println("Anchor Client | Servers Initialized");
    }
    public void initializeAccountManager() {
        accountManager = new AccountManager();
        System.out.println("Anchor Client | Account Manager Initialized");
    }

    public void shutDown() {
        System.out.println("Anchor Client | Shutting Down");
    }

}
