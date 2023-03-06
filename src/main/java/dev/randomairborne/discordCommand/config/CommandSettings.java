package dev.randomairborne.discordCommand.config;

import com.google.gson.annotations.SerializedName;

import net.minecraft.text.ClickEvent;
import net.minecraft.util.Formatting;

public class CommandSettings {

    @SerializedName(value = "target", alternate = "link")
    public String target;

    public String message;

    public Formatting color;

    @SerializedName(value = "hover")
    public HoverText hoverText;

    public String[] names;

    @SerializedName(value = "on_click", alternate = "action")
    public ClickEvent.Action onClick;

    public CommandSettings(String message, String target, Formatting color, ClickEvent.Action onClick, String[] names) {
        this.message = message;
        this.target = target;
        this.color = color;
        this.names = names;
        this.onClick = onClick;
    }

}
