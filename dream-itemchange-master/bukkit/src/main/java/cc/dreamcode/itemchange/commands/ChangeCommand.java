package cc.dreamcode.itemchange.commands;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.itemchange.ItemChangePlugin;
import cc.dreamcode.itemchange.config.MessageConfig;
import cc.dreamcode.itemchange.config.PluginConfig;
import cc.dreamcode.itemchange.hook.PluginHookManager;
import cc.dreamcode.itemchange.hook.vault.VaultHook;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dream.change")
public class ChangeCommand extends BukkitCommand {
    private @Inject ItemChangePlugin itemChangePlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject PluginHookManager pluginHookManager;

    public ChangeCommand() {
        super("wymiana");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;

        if (this.pluginConfig.useVault) {

            if (!this.pluginHookManager.get(VaultHook.class).isPresent()) {
                this.itemChangePlugin.getDreamLogger().error("Wystapil blad! Wsparcie dla Vault jest wlaczone, ale nie wykryto pluginu Vault");
                return;
            }

            if (!this.pluginHookManager.get(VaultHook.class).get().canAfford(player)) {
                this.messageConfig.insufficientFunds.send(player);
                return;
            }

            this.pluginHookManager.get(VaultHook.class).get().getEconomy().withdrawPlayer(player, this.pluginConfig.price);
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!this.pluginConfig.itemsToChange.containsKey(XMaterial.matchXMaterial(itemInHand.getType()))) {
            this.messageConfig.changeFailure.send(player);
            return;
        }

        this.pluginConfig.itemsToChange.forEach((netherite, diamond) -> {
            if (itemInHand.getType() != netherite.parseMaterial()) return;
            itemInHand.setType(diamond.parseMaterial());
            this.messageConfig.changeSuccess.send(player);
        });
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
