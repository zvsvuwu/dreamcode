package xyz.ravis96.dreamfreeze.tasks;

import eu.okaeri.tasker.core.Tasker;
import org.bukkit.entity.Player;
import org.panda_lang.utilities.inject.annotations.Inject;
import xyz.ravis96.dreamfreeze.PluginLogger;
import xyz.ravis96.dreamfreeze.PluginMain;
import xyz.ravis96.dreamfreeze.config.MessageConfig;
import xyz.ravis96.dreamfreeze.config.PluginConfig;
import xyz.ravis96.dreamfreeze.features.plugin.PluginStorage;
import xyz.ravis96.dreamfreeze.nms.notice.Notice;

import java.util.Map;

public abstract class TaskUse implements Runnable {

    @Inject public PluginMain pluginMain;
    @Inject public PluginLogger pluginLogger;

    @Inject public PluginConfig pluginConfig;
    @Inject public MessageConfig messageConfig;

    @Inject public PluginStorage pluginStorage;

    @Inject public Tasker tasker;

    public void sendMessage(Player p, Notice notice) {
        this.pluginMain.getNmsAccessor().getNoticeAccessor().send(notice, p);
    }

    public void sendMessage(Player p, Notice notice, Map<String, String> replaceMap) {
        this.pluginMain.getNmsAccessor().getNoticeAccessor().send(notice, p, replaceMap);
    }

    @Override
    public void run() {
        runTask();
    }

    public abstract void runTask();
}
