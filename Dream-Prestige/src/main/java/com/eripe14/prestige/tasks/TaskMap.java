package com.eripe14.prestige.tasks;

import com.eripe14.prestige.PrestigePlugin;
import com.eripe14.prestige.tasks.list.UserTask;

public class TaskMap {

    private final PrestigePlugin plugin;

    public TaskMap(PrestigePlugin plugin) {
        this.plugin = plugin;

        registerRepeatTask(new UserTask(), 20, 20, true);
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
