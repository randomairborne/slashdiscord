package dev.randomairborne.discordCommand;

// too many imports

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
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
        LOGGER.info("Loading custom commands");
        // get the path and output an error if we can't read or create the file
        Settings config = new Settings(new CommandSettings[] {});
        try {
            config = Config.getConfig();
        } catch (IOException e) {
            LOGGER.error("A read/write error occured, " + e);
        } catch (MissingFieldException e) {
            e.printStackTrace();
        }

        // tell Brigadier that we want this command to send back some text
        if (config == null) {
            LOGGER.error("Config returned NULL");
        }
        for (CommandSettings cSettings : config.commands) {
            for (String name : cSettings.names) {
                CommandRegistrationCallback.EVENT
                        .register((dispatcher, dedicated, environment) -> dispatcher
                                .register(literal(name).executes(context -> sendResponse(context, cSettings))));
                LOGGER.info("Loaded /" + name);
            }
        }
    }

    public static Integer sendResponse(CommandContext<ServerCommandSource> context, CommandSettings cSettings) {

        MutableText msg = Text.literal(cSettings.message);
        msg = msg.formatted(cSettings.color);
        if (cSettings.hoverText != null) {
            Text mText = Text.literal(cSettings.hoverText.message).styled(style -> style.withColor(cSettings.hoverText.color));
            msg = msg.styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, mText)));
        }
        msg = msg.styled(style -> style.withClickEvent(
                new ClickEvent(cSettings.onClick, cSettings.target)));
        context.getSource().getPlayer().sendMessage(msg, false);
        return 1;
    }

}