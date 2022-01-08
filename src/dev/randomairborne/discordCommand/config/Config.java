package dev.randomairborne.discordCommand.config;

import com.google.gson.*;
import dev.randomairborne.discordCommand.discordCommand;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {
    public static Settings getConfig() throws IOException, MissingFieldException {
        String configFilePath = FabricLoader.getInstance().getGameDir().resolve("config/slashdiscord.json").toString();
        // set up gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Settings settings = new Settings();
        try {
            // attempt to read settings from json into the Settings class
            settings = gson.fromJson(new FileReader(configFilePath), Settings.class);
        } catch (FileNotFoundException exception) {
            discordCommand.LOGGER.fatal("No configuration file found!");
        } catch (JsonSyntaxException exception) {
            discordCommand.LOGGER.fatal("Your JSON has improper syntax!");
        } catch (JsonParseException exception) {
            discordCommand.LOGGER.fatal("Error parsing JSON!");
        }
        for (CommandSettings cmdSettings : settings.commands) {
            if (cmdSettings.color == null) {
                throw new MissingFieldException("Color field is missing in one of your JSON entries!");
            }
            if (cmdSettings.link == null) {
                    throw new MissingFieldException("Link field is missing in one of your JSON entries!");
            }
            if (cmdSettings.message == null) {
                    throw new MissingFieldException("Message field is missing in one of your JSON entries!");
            }
            if (cmdSettings.names == null) {
                    throw new MissingFieldException("Names field is missing in one of your JSON entries!");
            }
        }
        return settings;
    }
}

