package cc.dreamcode.totem.vault;

import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class VaultApi {
    @Getter private final boolean initialized;
    @Getter private Economy economy;

    @Inject
    public VaultApi() {
        if (!setupEconomy()) {
            getLogger().severe(String.format("Nie odnaleziono Pluginu `Vault`."));
        } else {
            getLogger().info("Pomyslnie załadowano hook: Vault");
        }

        this.initialized = economy != null;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public boolean hasMoney(Player player, double amount){
        return economy.has(player, amount);
    }

    public void removeMoney(Player player, double amount){
        economy.withdrawPlayer(player, amount);
    }

    public double getBalance(Player player) {
        return economy.getBalance(player);
    }
}
