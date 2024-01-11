package cc.dreamcode.amulets.manager;

import cc.dreamcode.amulets.BukkitAmuletsPlugin;
import cc.dreamcode.amulets.amulet.Amulet;
import cc.dreamcode.amulets.config.PluginConfig;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

@Scheduler(async = false, delay = 0, interval = 20)
public class PermanentEffectsManager implements Runnable {

    private @Inject BukkitAmuletsPlugin bukkitAmuletsPlugin;
    private @Inject PluginConfig pluginConfig;

    @Override
    public void run() {
        if (!this.bukkitAmuletsPlugin.isAmuletsEnabled()) {
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerInventory inventory = player.getInventory();
            ItemStack item = inventory.getItemInOffHand();
            if (item == null) {
                continue;
            }

            Optional<Amulet> optAmulet = this.pluginConfig.amulets
                    .stream()
                    .filter(amulet -> {
                        ItemStack amuletItemStack = ItemBuilder.of(amulet.getItemStack())
                                .fixColors()
                                .setAmount(item.getAmount())
                                .toItemStack();
                        return item.equals(amuletItemStack);
                    })
                    .findFirst();

            if (!optAmulet.isPresent()) {
                continue;
            }

            Amulet amulet = optAmulet.get();
            List<PotionEffect> effects = amulet.getAmuletEffects();

            if (!amulet.isPermanentEffectOffhand()) {
                return;
            }

            for (PotionEffect effect : effects) {
                PotionEffect newEffect = new PotionEffect(
                        effect.getType(), 21, effect.getAmplifier()
                );
                newEffect.apply(player);
            }
        }
    }
}
