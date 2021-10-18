package dev.randomairborne.discordCommand;

// too many imports

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dev.randomairborne.discordCommand.config.*;

import java.io.IOException;

import static net.minecraft.server.command.CommandManager.literal;

public class discordCommand implements ModInitializer {
    // set up logger
    public static final Logger LOGGER = LogManager.getLogger("discordCommand");

    @Override
    public void onInitialize() {
        LOGGER.info("Loading /discord");
        // get the path and output an error if we can't read or create the file
        Settings config = null;
        try {
            config = Config.getConfig();
        } catch (IOException e) {
            LOGGER.error("A read/write error occured, " + e);
        }

        // tell Brigadier that we want this command to send back some text
        Settings finalConfig = config;
        assert finalConfig != null;
        for (String name : finalConfig.names) {
            CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(literal(name).executes(context -> {
                context.getSource().getPlayer().sendSystemMessage(new LiteralText(finalConfig.message).formatted(Formatting.UNDERLINE, Formatting.valueOf(finalConfig.color)).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, finalConfig.discord_link))), Util.NIL_UUID);
                return 1;
            })));
        }
        LOGGER.info("Loaded /discord");
    }


}