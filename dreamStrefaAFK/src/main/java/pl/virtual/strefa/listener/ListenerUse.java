package pl.virtual.strefa.listener;

import org.bukkit.event.Listener;
import pl.virtual.strefa.config.ConfigPlugin;
import pl.virtual.strefa.main.Main;
import pl.virtual.strefa.manager.UserManager;
import pl.virtual.strefa.util.DataUtil;
import pl.virtual.strefa.util.Message;

public class ListenerUse implements Listener {
    public Main plugin = Main.getPlugin();
    public UserManager userManager = plugin.getUserManager();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();
    public DataUtil dataUtil = plugin.getDataUtil();
    public Message message = plugin.getMessage();
}
