package dev.randomairborne.discordCommand.config;

import com.google.gson.*;
import dev.randomairborne.discordCommand.discordCommand;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.*;

public class Config {
    public static Settings getConfig() throws IOException, MissingFieldException {
        Path configFilePath = FabricLoader.getInstance().getGameDir().resolve("config/slashdiscord.json");
        // set up gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Settings settings = new Settings();
        try {
            // attempt to read settings from json into the Settings class
            settings = gson.fromJson(new FileReader(configFilePath.toString()), Settings.class);
        } catch (FileNotFoundException exception) {
            discordCommand.LOGGER.info("No configuration file found, creating empty one!");
            Files.createDirectories(FabricLoader.getInstance().getGameDir().resolve("config"));
            configFilePath.toFile().createNewFile();
            FileWriter newConf = new FileWriter(configFilePath.toString());
            newConf.write("{\"commands\":[{\"link\":\"https://github.com/randomairborne/slashdiscord/blob/master/README.md\",\"message\":\"Click to configure slashdiscord!\",\"color\":\"AQUA\",\"names\":[\"discord\"]}]}");
            newConf.close();
            settings = gson.fromJson(new FileReader(configFilePath.toString()), Settings.class);
        } catch (JsonSyntaxException exception) {
            discordCommand.LOGGER.fatal("Your JSON has improper syntax!");
            exception.printStackTrace();
        } catch (JsonParseException exception) {
            discordCommand.LOGGER.fatal("Error parsing JSON!");
            exception.printStackTrace();
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
