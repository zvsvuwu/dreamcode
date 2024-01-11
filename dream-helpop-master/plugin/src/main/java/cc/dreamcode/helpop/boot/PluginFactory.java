package cc.dreamcode.helpop.boot;

import cc.dreamcode.helpop.exception.PluginRuntimeException;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.concurrent.atomic.AtomicBoolean;

public final class PluginFactory {

    @Deprecated
    public static boolean checkPlugin(final AtomicBoolean atomicBoolean, final PluginDescriptionFile pluginDescriptionFile) {
        if (!pluginDescriptionFile.getRawName().equals("Dream-Helpop")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin name is changed...");
            return false;
        }
        if (!pluginDescriptionFile.getAuthors().contains("torobolin")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin authors is changed...");
            return false;
        }
        if (pluginDescriptionFile.getDescription() != null &&
                !pluginDescriptionFile.getDescription().equals("Helpop plugin by DreamCode.")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin description is changed...");
            return false;
        }
        if (pluginDescriptionFile.getWebsite() != null &&
                !pluginDescriptionFile.getWebsite().equals("DreamCode - https://discord.gg/dreamcode")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin website is changed...");
            return false;
        }
        return true;
    }

    private static void throwRuntimeException(String name) {
        throw new PluginRuntimeException(name);
    }

}
