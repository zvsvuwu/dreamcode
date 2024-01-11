package cc.dreamcode.guisuffix.hook.luckperms;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;


public class LuckPermsHook {

    private LuckPerms luckPerms;

    public LuckPermsHook() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (provider != null) {
            this.luckPerms = provider.getProvider();
        }
    }

    public LuckPerms getLuckPerms() {
        return this.luckPerms;
    }
}
