package cc.dreamcode.wallet.placeholder;

import cc.dreamcode.platform.bukkit.hook.PluginHook;
import lombok.NonNull;
import org.bukkit.Bukkit;

public class PlaceholderApiHook implements PluginHook {

    @Override
    public String getPluginName() {
        return "PlaceholderAPI";
    }

    @Override
    public boolean isPresent() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public void register(@NonNull PlaceholderExpansionWrapper placeholderExpansionWrapper) {
        placeholderExpansionWrapper.wrap().register();
    }
}
