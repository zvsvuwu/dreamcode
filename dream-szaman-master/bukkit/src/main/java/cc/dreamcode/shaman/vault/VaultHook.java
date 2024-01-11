package cc.dreamcode.shaman.vault;

import cc.dreamcode.shaman.SzamanPlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;

public class VaultHook {

    @Inject private SzamanPlugin szamanPlugin;
    @Getter private EconomyStorage economyStorage;
    @Getter private boolean useVault;

    public boolean setup() {
        if (this.szamanPlugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            this.useVault = false;
            return false;
        }
        this.economyStorage = this.szamanPlugin.createInstance(EconomyStorage.class);
        boolean setup = this.economyStorage.setupEconomy();
        this.useVault = setup;
        return setup;
    }

}
