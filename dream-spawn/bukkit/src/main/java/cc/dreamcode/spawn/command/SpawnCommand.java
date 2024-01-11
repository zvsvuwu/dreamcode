package cc.dreamcode.spawn.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.spawn.SpawnManager;
import cc.dreamcode.spawn.config.MessageConfig;
import cc.dreamcode.spawn.config.PluginConfig;
import cc.dreamcode.spawn.hook.PluginHookManager;
import cc.dreamcode.spawn.hook.worldguard.WorldGuardHook;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.time.Instant;
import java.util.List;

public class SpawnCommand extends BukkitCommand {

    private final SpawnManager spawnManager;
    private final PluginConfig config;
    private final MessageConfig messageConfig;
    private final PluginHookManager pluginHookManager;
    private long time;

    @Inject
    public SpawnCommand(final SpawnManager spawnManager, final PluginConfig config, final MessageConfig messageConfig, final PluginHookManager pluginHookManager) {
        super("spawn");
        this.spawnManager = spawnManager;
        this.config = config;
        this.messageConfig = messageConfig;
        this.pluginHookManager = pluginHookManager;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        switch (args.length) {
            case 0:
                if(!(sender instanceof Player)) {
                    messageConfig.notPlayer.send(sender);
                    return;
                }

                Player player = (Player) sender;

                if (spawnManager.isPlayerTeleporting(player)) {
                    messageConfig.alreadyTeleporting.send(player);
                    return;
                }

                if(player.hasPermission(config.bypassPermission)) {
                    messageConfig.successMessage.send(player);
                    player.teleport(spawnManager.getSpawnLocation());
                    return;
                }

                this.time = config.teleportTime.toMillis();

                config.groupCooldowns.forEach(groupCooldown -> {
                    for(String key : groupCooldown.keySet()) {
                        if(isPlayerInGroup(player, key)) {
                            this.time = groupCooldown.get(key).toMillis();
                        }
                    }
                });

                if (config.regionCooldownEnabled) {
                    this.pluginHookManager.get(WorldGuardHook.class).ifPresent(worldGuardHook -> config.regionCooldowns.forEach(regionCooldown -> {
                        for (String key : regionCooldown.keySet()) {
                            ApplicableRegionSet playerRegions = worldGuardHook.getRegions(player.getLocation());
                            if (playerRegions.getRegions().stream().anyMatch(playerRegion -> playerRegion.getId().equals(key))) {
                                this.time = regionCooldown.get(key).toMillis();
                            }
                        }
                    }));

                }

                if(config.teleportEffectsEnabled) {
                    config.teleportEffects.forEach(effect -> player.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, 0, false)));
                }

                spawnManager.addTeleport(player, Instant.now().plusSeconds(1).plusMillis(time).toEpochMilli());
                break;
            case 1:
                if(!sender.hasPermission(config.adminPermission)) {
                    messageConfig.noPermission.send(sender);
                    return;
                }

                Player targetPlayer = Bukkit.getPlayerExact(args[0]);

                if(targetPlayer == null || !targetPlayer.isOnline()) {
                    messageConfig.noPlayer.send(sender);
                    return;
                }

                messageConfig.successMessage.send(targetPlayer);
                targetPlayer.teleport(spawnManager.getSpawnLocation());
                break;
        }
    }

    private boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
