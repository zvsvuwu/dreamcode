package cc.dreamcode.nagroda.storage.document;

import cc.dreamcode.nagroda.NagrodaPlugin;
import cc.dreamcode.nagroda.scheduler.SchedulerService;
import eu.okaeri.persistence.document.Document;

import java.util.concurrent.TimeUnit;

/**
 * Abstract class for asynchronous save method in document.
 */
public abstract class AbstractDocument extends Document {
    public void saveAsync() {
        NagrodaPlugin.getNagrodaPlugin().getInject(SchedulerService.class).ifPresent(schedulerService ->
                schedulerService.asyncLater(this::save, 0L, TimeUnit.MILLISECONDS));
    }
}
