package dev.randomairborne.discordCommand.config;

public class Settings {
    public CommandSettings[] commands;
    public Settings(CommandSettings[] commands) {
        this.commands = commands;
    }
    public Settings() {
        this.commands = new CommandSettings[] {};
    }
}

