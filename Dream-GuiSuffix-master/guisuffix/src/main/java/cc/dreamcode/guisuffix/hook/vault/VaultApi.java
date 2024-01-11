package cc.dreamcode.guisuffix.hook.vault;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultApi {

    @Getter
    private Economy economy;

    public VaultApi() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if (economyProvider != null) {
            this.economy = economyProvider.getProvider();
        }
    }

    public boolean isSuccess(EconomyResponse economyResponse) {
        return economyResponse.type == EconomyResponse.ResponseType.SUCCESS;
    }
}
