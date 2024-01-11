package cc.dreamcode.restarts.config;

import cc.dreamcode.restarts.features.menu.MenuSerdes;
import cc.dreamcode.restarts.features.notice.NoticeSerdes;
import cc.dreamcode.restarts.features.randomizer.DoubleRandomizerSerdes;
import cc.dreamcode.restarts.features.randomizer.IntRandomizerSerdes;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import lombok.NonNull;

/**
 * Serialize/deserialize classes for objects in config.
 * See: ObjectSerializer.class
 */
public class ConfigSerdesPack implements OkaeriSerdesPack {
    @Override
    public void register(@NonNull SerdesRegistry registry) {
        registry.register(new NoticeSerdes());
        registry.register(new MenuSerdes());
        registry.register(new DoubleRandomizerSerdes());
        registry.register(new IntRandomizerSerdes());
    }
}
