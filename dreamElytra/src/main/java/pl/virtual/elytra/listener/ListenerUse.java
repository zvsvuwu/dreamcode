package pl.virtual.elytra.listener;

import org.bukkit.event.Listener;
import pl.virtual.elytra.config.ConfigPlugin;
import pl.virtual.elytra.main.Main;
import pl.virtual.elytra.util.DataUtil;
import pl.virtual.elytra.util.Message;

public class ListenerUse implements Listener {
    public Main plugin = Main.getPlugin();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();
    public DataUtil dataUtil = plugin.getDataUtil();
    public Message message = plugin.getMessage();
}
