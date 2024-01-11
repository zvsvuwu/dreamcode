package cc.dreamcode.automessage.service;

import cc.dreamcode.automessage.AutoMessagePlugin;
import cc.dreamcode.automessage.config.PluginConfig;
import cc.dreamcode.automessage.event.AutoMsgStartEvent;
import cc.dreamcode.automessage.event.EventHandler;
import cc.dreamcode.automessage.runnable.AutoMsgRunnable;
import eu.okaeri.injector.annotation.Inject;

public class AutoMsgService {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject PluginConfig pluginConfig;

    public void callScheduler() {
        if (EventHandler.handle(new AutoMsgStartEvent())) {
            return;
        }

        long runIntervalLong = this.pluginConfig.msgInterval.getSeconds() * 20;
        int runInterval = Math.round((runIntervalLong));

        this.autoMessagePlugin.getServer().getScheduler().runTaskTimer(
                this.autoMessagePlugin,
                this.autoMessagePlugin.createInstance(AutoMsgRunnable.class),
                runInterval,
                runInterval
        );
    }
}
