package dev.randomairborne.discordCommand.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.Arrays;

public class Config {
    public static Settings getConfig() throws IOException {
        String configFilePath = FabricLoader.getInstance().getGameDir().resolve("config/slashdiscord.json").toString();
        // set up gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Settings settings;
        try {
            // attempt to read settings from json into the Settings class
            settings = gson.fromJson(new FileReader(configFilePath), Settings.class);
        } catch (java.io.FileNotFoundException exception) {
            // if the file doesn't exist we use the default and write it to the file
            Settings defaultSettings = new Settings();
            defaultSettings.message = "Click here to join our discord server!";
            defaultSettings.discord_link = "https://discord.gg/minecraft";
            defaultSettings.color = "GOLD";
            defaultSettings.names = Arrays.asList("discord");
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFilePath));
            writer.write(gson.toJson(defaultSettings));
            writer.close();
            return defaultSettings;
        }
        // if only some parts are filled then we use the defaults for the missing parts
        if (settings.message == null) {
            settings.message = "Click here to join our discord server!";
        }
        if (settings.discord_link == null) {
            settings.discord_link = "https://discord.gg/minecraft";
        }
        if (settings.color == null) {
            settings.color = "GOLD";
        }
        if (settings.names == null) {
            settings.names = Arrays.asList("discord");
        }
        return settings;
    }
}

