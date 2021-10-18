package dev.randomairborne.discordCommand.config;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Settings {
    @SerializedName("discord_link")
    public String discord_link;

    @SerializedName("message")
    public String message;

    @SerializedName("color")
    public String color;

    @SerializedName("names")
    public List<String> names;

}
