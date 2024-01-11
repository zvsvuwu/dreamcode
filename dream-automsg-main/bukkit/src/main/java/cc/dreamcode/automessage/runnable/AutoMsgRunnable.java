package cc.dreamcode.automessage.runnable;

import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.automessage.AutoMessagePlugin;
import cc.dreamcode.automessage.config.PluginConfig;
import cc.dreamcode.automessage.service.AutoMsgService;
import eu.okaeri.injector.annotation.Inject;

public class AutoMsgRunnable implements Runnable {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject AutoMsgService autoMsgService;

    private int currentMessageIndex = 0;

    @Override
    public void run() {
        if (this.pluginConfig.messages.isEmpty() || !this.pluginConfig.shouldSendMessages) {
            return;
        }

        if (currentMessageIndex >= this.pluginConfig.messages.size()) {
            currentMessageIndex = 0;
        }

        BukkitNotice message = this.pluginConfig.messages.get(currentMessageIndex);
        this.autoMessagePlugin.getServer().getOnlinePlayers()
                .forEach(message::send);

        currentMessageIndex++;
    }
}