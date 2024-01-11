package cc.dreamcode.nagroda.model;

import cc.dreamcode.nagroda.NagrodaPlugin;
import cc.dreamcode.nagroda.exception.PluginRuntimeException;
import cc.dreamcode.nagroda.scheduler.SchedulerService;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public interface FutureImpl {
    default <T> CompletableFuture<T> future(Callable<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.call();
            }
            catch (Exception e) {
                throw new PluginRuntimeException("Failed to run future supplier method.", e);
            }
        }, NagrodaPlugin.getNagrodaPlugin().getInject(SchedulerService.class)
                .orElseThrow(() -> new PluginRuntimeException("SchedulerService not found. It is required to working repository.")).getAsync());
    }

    default CompletableFuture<Void> future(Runnable runnable) {
        return CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            }
            catch (Exception e) {
                throw new PluginRuntimeException("Failed to run future method.", e);
            }
        }, NagrodaPlugin.getNagrodaPlugin().getInject(SchedulerService.class)
                .orElseThrow(() -> new PluginRuntimeException("SchedulerService not found. It is required to working repository.")).getAsync());
    }
}
