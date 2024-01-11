package cc.dreamcode.shaman.vault;

import cc.dreamcode.shaman.SzamanPlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyStorage {
    private @Inject SzamanPlugin szamanPlugin;
    private @Getter Economy economy;

    public boolean setupEconomy() {
        try {
            RegisteredServiceProvider<Economy> rsp = this.szamanPlugin.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            }
            this.economy = rsp.getProvider();
            return this.economy != null;
        }
        catch (Exception e) {
            return false;
        }
    }
}
