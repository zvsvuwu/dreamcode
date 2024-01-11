package cc.dreamcode.leaderboard.config;

import cc.dreamcode.leaderboard.notice.NoticeSerdes;
import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
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
        registry.register(new MenuBuilderSerdes());
    }
}
