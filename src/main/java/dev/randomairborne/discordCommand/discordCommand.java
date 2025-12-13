package dev.randomairborne.discordCommand;

import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.context.CommandContext;
import dev.randomairborne.discordCommand.config.*;
import java.io.IOException;
import java.net.URI;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class discordCommand implements ModInitializer {
    // set up logger
    public static final Logger LOGGER = LogManager.getLogger("discordCommand");

    @Override
    public void onInitialize() {
        LOGGER.info("Loading custom commands");
        // get the path and output an error if we can't read or create the file
        Settings config;
        try {
            config = Config.getConfig();
        } catch (IOException e) {
            LOGGER.error("A read/write error occured, {}", String.valueOf(e));
            return;
        } catch (MissingFieldException e) {
            LOGGER.error(e.getMessage());
            return;
        }

        // tell Brigadier that we want this command to send back some text
        if (config == null) {
            LOGGER.error("Config returned NULL");
            return;
        }
        for (CommandSettings cSettings : config.commands) {
            for (String name : cSettings.names) {
                CommandRegistrationCallback.EVENT.register(
                        (dispatcher, dedicated, environment)
                                -> dispatcher.register(literal(name).executes(
                                context -> sendResponse(context, cSettings))));
                LOGGER.info("Loaded /{}", name);
            }
        }
    }

    public static Integer sendResponse(
            CommandContext<ServerCommandSource> context, CommandSettings cSettings) {
        MutableText msg = Text.literal(cSettings.message);
        msg = msg.formatted(cSettings.color);
        if (cSettings.hoverText != null) {
            Text mText =
                    Text.literal(cSettings.hoverText.message)
                            .styled(style -> style.withColor(cSettings.hoverText.color));
            msg = msg.styled(
                    style -> style.withHoverEvent(new HoverEvent.ShowText(mText)));
        }
        msg = msg.styled(style
                -> style.withClickEvent(
                new ClickEvent.OpenUrl(URI.create(cSettings.target))));
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            player.sendMessage(msg, false);
        }
        return 1;
    }
}