package cc.dreamcode.guisuffix.hook.vault;


import cc.dreamcode.platform.bukkit.hook.PluginHook;
import lombok.Getter;

public class VaultHook implements PluginHook {

    @Getter
    private VaultApi vaultApi;

    @Override
    public String getPluginName() {
        return "Vault";
    }

    @Override
    public boolean isPresent() {
        return PluginHook.super.isPresent();
    }

    public void register() {
        this.vaultApi = new VaultApi();
    }
}
