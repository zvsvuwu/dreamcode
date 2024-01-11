package pl.virtual.strefa.task;


import pl.virtual.strefa.main.Main;
import pl.virtual.strefa.task.use.ActionBarTask;
import pl.virtual.strefa.task.use.AfkTask;

import java.util.stream.Stream;

public class TaskMap {

    private final Main plugin;

    public TaskMap(Main plugin) {
        this.plugin = plugin;
        Stream.of(
                new AfkTask(20L),
                new ActionBarTask(20L)
        ).forEach(this::runTaskTimer);
    }


    private void runTaskTimer(TaskUse cmd) {
        cmd.runTaskTimer(plugin, cmd.time, cmd.time);
    }
}
