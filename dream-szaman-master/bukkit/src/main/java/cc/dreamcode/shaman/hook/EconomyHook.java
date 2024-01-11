package cc.dreamcode.shaman.hook;

import cc.dreamcode.shaman.SzamanPlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyHook {

    @Inject private SzamanPlugin szamanPlugin;
    @Getter private Economy economy;

    public boolean setupEconomy() {
        if (this.szamanPlugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
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
