package xyz.ravis96.dreamkit.tasks;

import xyz.ravis96.dreamkit.PluginMain;

public class TaskMap {

    private final PluginMain plugin;

    public TaskMap(PluginMain plugin) {
        this.plugin = plugin;

    }

    public void registerRepeatTask(Runnable runnable, int delay, int repeat, boolean async) {
        try {
            if (async) {
                this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin,
                        this.plugin.getInjector().newInstanceWithFields(runnable.getClass()), delay, repeat);
                return;
            }
            this.plugin.getServer().getScheduler().runTaskTimer(this.plugin,
                    this.plugin.getInjector().newInstanceWithFields(runnable.getClass()), delay, repeat);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
