package pl.virtual.strefa.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.virtual.strefa.config.ConfigPlugin;
import pl.virtual.strefa.main.Main;
import pl.virtual.strefa.manager.UserManager;
import pl.virtual.strefa.util.DataUtil;
import pl.virtual.strefa.util.Message;

public abstract class TaskUse extends BukkitRunnable {

    public Main plugin = Main.getPlugin();
    public UserManager userManager = plugin.getUserManager();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();
    public DataUtil dataUtil = plugin.getDataUtil();
    public Message message = plugin.getMessage();
    public final long time;

    public TaskUse(long time) { this.time = time; }
    public abstract void run();
}
