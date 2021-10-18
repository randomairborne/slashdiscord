package dev.randomairborne.discordCommand;
// too many imports
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;

import static net.minecraft.server.command.CommandManager.literal;

public class discordCommand implements ModInitializer {
    // set up logger
    public static final Logger LOGGER = LogManager.getLogger("discordCommand");

    @Override
    public void onInitialize() {
        LOGGER.info("Loading /discord");
        // get the path and output an error if we can't read or create the file
        Path serverPath = FabricLoader.getInstance().getGameDir();
        Path configPath = serverPath.resolve("config/discordlink.txt");
        FileReader fr = null;
        try {
            fr = new FileReader(configPath.toString());
        } catch (FileNotFoundException efnf) {
            try (FileOutputStream stream = new FileOutputStream(configPath.toString())) {
                byte[] data = "https://discord.gg/minecraft".getBytes();
                stream.write(data);
            } catch (IOException e) {
                LOGGER.error("Could not load or create config file!");
                return;
            }
        }
        assert fr != null;
        BufferedReader br = new BufferedReader(fr);
        String link;
        try {
            link = br.readLine();
        } catch (IOException e) {
            LOGGER.error("Could not read file for link..");
            return;
        }
        String finalLink = link;
        // tell Brigadier that we want this command to send back some text
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("discord").executes(context -> {
                context.getSource().getPlayer().sendSystemMessage(new LiteralText("Click here to join our discord!").formatted(Formatting.UNDERLINE, Formatting.GOLD).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, finalLink))), Util.NIL_UUID);
                return 1;
            }));
        });
        LOGGER.info("Loaded /discord");
    }


}