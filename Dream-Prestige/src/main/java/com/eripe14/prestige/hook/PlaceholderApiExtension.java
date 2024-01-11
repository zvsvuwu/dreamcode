package com.eripe14.prestige.hook;

import com.eripe14.prestige.features.user.User;
import com.eripe14.prestige.features.user.UserManager;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class PlaceholderApiExtension extends PlaceholderExpansion {

    private final UserManager userManager;

    @Override
    public @NotNull String getIdentifier() {
        return "prestize";
    }

    @Override
    public @NotNull String getAuthor() {
        return "eripe14";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public boolean canRegister() {
        return true;
    }

    public boolean persist() {
        return true;
    }

    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) return "";

        User user = userManager.getOrCreate(player);

        if (!params.equalsIgnoreCase("prestiz")) return "";
        if (user.getPrestige() == 0) return "";

        return String.valueOf(user.getPrestige());
    }
}
