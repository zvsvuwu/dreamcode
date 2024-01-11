package cc.dreamcode.report.storage;

import cc.dreamcode.report.model.report.ReportSerdes;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import lombok.NonNull;

public class StorageSerdesPack implements OkaeriSerdesPack {
    @Override
    public void register(@NonNull SerdesRegistry registry) {
        registry.register(new ReportSerdes());
    }
}
