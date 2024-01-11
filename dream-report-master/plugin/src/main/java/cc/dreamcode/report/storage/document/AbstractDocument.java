package cc.dreamcode.report.storage.document;

import cc.dreamcode.report.ReportPlugin;
import cc.dreamcode.report.scheduler.SchedulerService;
import eu.okaeri.persistence.document.Document;

import java.util.concurrent.TimeUnit;

/**
 * Abstract class for asynchronous save method in document.
 */
public abstract class AbstractDocument extends Document {
    public void saveAsync() {
        ReportPlugin.getReportPlugin().getInject(SchedulerService.class).ifPresent(schedulerService ->
                schedulerService.asyncLater(this::save, 0L, TimeUnit.MILLISECONDS));
    }
}
