package cc.dreamcode.itemchange.hook;

import cc.dreamcode.itemchange.hook.vault.VaultHook;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PluginHookType {
    VAULT("Vault", "net.milkbowl.vault.Vault", VaultHook.class);

    private final String name;
    private final String classPackageName;
    private final Class<? extends PluginHook> pluginHookClass;
}
