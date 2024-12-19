package dev.randomairborne.discordCommand.config;

import com.google.gson.*;
import dev.randomairborne.discordCommand.discordCommand;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.ClickEvent.Action;
import net.minecraft.util.Formatting;

import java.io.*;
import java.nio.file.*;

public class Config {
    public static Settings getConfig() throws IOException, MissingFieldException {
        Path configFilePath = FabricLoader.getInstance().getGameDir().resolve("config/slashdiscord.json");
        // set up gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Settings settings = new Settings();
        try {
            // attempt to read settings from json into the Settings class
            settings = gson.fromJson(new FileReader(configFilePath.toString()), Settings.class);
        } catch (FileNotFoundException exception) {
            discordCommand.LOGGER.info("No configuration file found, creating empty one!");
            CommandSettings setUp = new CommandSettings("Click to configure slashdiscord!",
                    "https://github.com/randomairborne/slashdiscord/blob/main/README.md", Formatting.AQUA,
                    Action.OPEN_URL, new String[] { "discord", "setup" });
            CommandSettings copyToClipboard = new CommandSettings("Click to copy world seed!",
                    "-7115307996784423713", Formatting.GREEN,
                    Action.COPY_TO_CLIPBOARD, new String[] { "worldseed" });
            copyToClipboard.hoverText = new HoverText("Click to copy", Formatting.GOLD);
            Files.createDirectories(FabricLoader.getInstance().getGameDir().resolve("config"));
            configFilePath.toFile().createNewFile();
            FileWriter newConfigurationFile = new FileWriter(configFilePath.toString());
            settings = new Settings(new CommandSettings[] { setUp, copyToClipboard });
            newConfigurationFile.write(gson.toJson(settings));
            newConfigurationFile.close();
        } catch (JsonSyntaxException exception) {
            discordCommand.LOGGER.fatal("Your JSON has improper syntax!");
            exception.printStackTrace();
            throw exception;
        } catch (JsonParseException exception) {
            discordCommand.LOGGER.fatal("Error parsing JSON!");
            exception.printStackTrace();
            throw exception;
        }
        for (CommandSettings cmdSettings : settings.commands) {
            if (cmdSettings.target == null) {
                throw new MissingFieldException("target field is missing in one of your JSON entries!");
            }
            if (cmdSettings.message == null) {
                throw new MissingFieldException("message field is missing in one of your JSON entries!");
            }
            if (cmdSettings.names == null) {
                throw new MissingFieldException("names field is missing in one of your JSON entries!");
            }
            if (cmdSettings.onClick == null) {
                cmdSettings.onClick = Action.OPEN_URL;
            }
            if (cmdSettings.color == null) {
                cmdSettings.color = Formatting.WHITE;
            }
        }
        return settings;
    }
}
