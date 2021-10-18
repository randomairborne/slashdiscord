package dev.randomairborne.discordCommand.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.List;

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
            CommandSettings defaultSettings = new CommandSettings();
            defaultSettings.message = "Please click here for help configuring this mod.";
            defaultSettings.link = "https://github.com/randomairborne/slashdiscord#readme";
            defaultSettings.color = "GOLD";
            defaultSettings.names = List.of("discord", "setup", "discordsetup");
            Settings finalDefaultSettings = new Settings();
            finalDefaultSettings.commands = List.of(defaultSettings);
            return finalDefaultSettings;
        }
        for (CommandSettings cmdSettings : settings.commands) {
            assert cmdSettings.color != null;
            assert cmdSettings.link != null;
            assert cmdSettings.message != null;
            assert cmdSettings.names != null;
        }
        return settings;
    }
}

