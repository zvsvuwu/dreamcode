package xyz.ravis96.dreamkit;

import lombok.RequiredArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public final class PluginLogger {

    private final Logger logger;

    public void info(String text) {
        this.logger.info(text);
    }

    public void debug(String text) {
        this.logger.info("[DEBUG] " + text);
    }

    public void warning(String text) {
        this.logger.warning(text);
    }

    public void error(String text) {
        this.logger.severe(text);
    }

    public void error(String text, Throwable throwable) {
        this.logger.log(Level.SEVERE, text, throwable);
    }
}
