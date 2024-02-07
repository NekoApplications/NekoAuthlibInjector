package icu.takeneko.nai;

import icu.takeneko.nai.transformer.InstrumentationAccess;
import icu.takeneko.nai.transformer.YggdrasilEnvironmentTransformer;
import net.fabricmc.api.ModInitializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public class Mod implements ModInitializer {
    public static String authUrl;
    public static String originalUrl;
    private static boolean patched;

    public static void patch() {
        try {
            File configPath = new File("./neko-authlib-injector.properties");
            if (!configPath.exists()) {
                configPath.createNewFile();
            }
            Properties properties = new Properties();
            properties.load(new FileReader(configPath));
            boolean modified = false;
            authUrl = properties.getProperty("url");
            originalUrl = properties.getProperty("originalUrl");
            if (authUrl == null) {
                authUrl = "https://example.com";
                properties.put("url", "https://example.com");
                modified = true;
            }
            if (originalUrl == null) {
                originalUrl = "https://sessionserver.mojang.com";
                properties.put("originalUrl", "https://sessionserver.mojang.com");
                modified = true;
            }
            if (modified) {
                properties.store(new FileWriter(configPath), "NekoAuthlibInjector");
            }
            System.out.println("[NekoAuthlibInjector] Using url: " + authUrl);
            InstrumentationAccess.initAccess();
            new YggdrasilEnvironmentTransformer().apply("com.mojang.authlib.yggdrasil.YggdrasilEnvironment");
        } catch (IllegalStateException illegalStateException) {
            throw new RuntimeException("Failed to install ByteBuddyAgent, mod will not work", illegalStateException);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        patched = true;
    }

    @Override
    public void onInitialize() {
        if (!patched) {
            System.out.println("[NekoAuthlibInjector] Invoking transformer from mod initalize stage");
            patch();
        }
        System.out.println("[NekoAuthlibInjector] Hello World!");
    }
}
