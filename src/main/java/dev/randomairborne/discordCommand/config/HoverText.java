package dev.randomairborne.discordCommand.config;

import net.minecraft.util.Formatting;;

public class HoverText {
    public String message;
    public Formatting color;
    public HoverText(String message, Formatting color) {
        this.message = message;
        this.color = color;
    }
}
