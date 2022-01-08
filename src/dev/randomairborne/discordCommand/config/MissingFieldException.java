package dev.randomairborne.discordCommand.config;

public class MissingFieldException extends Exception {
    public MissingFieldException(String errorMessage) {
        super(errorMessage);
    }
}
