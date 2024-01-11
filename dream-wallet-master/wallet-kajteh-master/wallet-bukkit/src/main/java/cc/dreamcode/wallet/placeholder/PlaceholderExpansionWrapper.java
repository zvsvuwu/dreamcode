package cc.dreamcode.wallet.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public abstract class PlaceholderExpansionWrapper {

    private final String identifier;
    private final String author;
    private final String version;

    public abstract String onWrappedRequest(OfflinePlayer player, String params);

    public PlaceholderExpansion wrap() {
        return new PlaceholderExpansion() {
            @Override
            public @NotNull String getIdentifier() {
                return identifier;
            }

            @Override
            public @NotNull String getAuthor() {
                return author;
            }

            @Override
            public @NotNull String getVersion() {
                return version;
            }

            @Override
            public String onRequest(OfflinePlayer player, @NotNull String params) {
                return onWrappedRequest(player, params);
            }
        };
    }
}
