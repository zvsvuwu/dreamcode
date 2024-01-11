package cc.dreamcode.itemchange.hook.vault;


import cc.dreamcode.itemchange.ItemChangePlugin;
import cc.dreamcode.itemchange.config.PluginConfig;
import cc.dreamcode.itemchange.hook.PluginHook;
import cc.dreamcode.itemchange.hook.PluginHookType;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements PluginHook, PluginHook.Initializer {
    private @Inject ItemChangePlugin itemChangePlugin;
    private @Inject PluginConfig pluginConfig;

    @Override
    public PluginHookType getPluginHookType() {
        return PluginHookType.VAULT;
    }

    @Getter Economy economy;

    @Override
    public void onInitialize() {
        RegisteredServiceProvider<Economy> rsp = this.itemChangePlugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            this.itemChangePlugin.getDreamLogger().info("Nie wykryto pluginu Vault lub wystapil nieoczekiwany blad.");
            return;
        }

        economy = rsp.getProvider();
    }

    public boolean canAfford(Player player) {
        return this.economy.has(player, this.pluginConfig.price);
    }
}
