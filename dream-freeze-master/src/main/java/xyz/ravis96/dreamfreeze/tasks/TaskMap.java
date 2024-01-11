package xyz.ravis96.dreamfreeze.tasks;

import xyz.ravis96.dreamfreeze.PluginMain;
import xyz.ravis96.dreamfreeze.tasks.list.FreezeTask;

public class TaskMap {

    private final PluginMain plugin;

    public TaskMap(PluginMain plugin) {
        this.plugin = plugin;

        this.registerRepeatTask(new FreezeTask(), 0, 20, true);
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
