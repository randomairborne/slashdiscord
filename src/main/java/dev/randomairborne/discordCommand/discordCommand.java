package dev.randomairborne.discordCommand;

// too many imports

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.server.command.ServerCommandSource;

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
        Settings config = new Settings();
        try {
            config = Config.getConfig();
        } catch (IOException e) {
            LOGGER.error("A read/write error occured, " + e);
        } catch (MissingFieldException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // tell Brigadier that we want this command to send back some text
        if (config == null) {
            LOGGER.error("Config returned NULL");
        }
        assert config != null;
        for (CommandSettings cSettings : config.commands) {
            for (String name : cSettings.names) {
                CommandRegistrationCallback.EVENT
                        .register((dispatcher, dedicated, environment) -> dispatcher
                                .register(literal(name).executes(context -> sendLinkResponse(context, cSettings))));
            }
        }
        LOGGER.info("Loaded /discord");
    }

    public static Integer sendLinkResponse(CommandContext<ServerCommandSource> context, CommandSettings cSettings) {
        context.getSource().getPlayer().sendMessage(
                Text.literal(cSettings.message)
                        .formatted(Formatting.UNDERLINE, Formatting.valueOf(cSettings.color))
                        .styled(style -> style.withClickEvent(
                                new ClickEvent(ClickEvent.Action.OPEN_URL, cSettings.link))),
                false);
        return 1;
    }

}