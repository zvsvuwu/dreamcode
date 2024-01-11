package cc.dreamcode.killreward.controller;

import cc.dreamcode.killreward.KillRewardPlugin;
import cc.dreamcode.killreward.boost.BoostManager;
import cc.dreamcode.killreward.config.MessageConfig;
import cc.dreamcode.killreward.config.PluginConfig;
import cc.dreamcode.killreward.manager.CooldownManager;
import cc.dreamcode.killreward.util.LuckPermsUtil;
import cc.dreamcode.utilities.ChanceUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class KillRewardController implements Listener {

    private final PluginConfig config;
    private final MessageConfig messageConfig;
    private final BoostManager boostManager;
    private final CooldownManager cooldownManager;
    private double chance;
    private List<String> commands;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if(!this.config.killRewardEnabled || player.getKiller() == null) {
            return;
        }

        this.messageConfig.killBroadcast.sendAll(new MapBuilder<String, Object>()
                .put("killer", killer.getName())
                .put("player", player.getName())
                .build());

        this.messageConfig.kill.send(killer, new MapBuilder<String, Object>()
                .put("killer", killer.getName())
                .put("player", player.getName())
                .build());

        this.config.killSounds.forEach(sound -> killer.playSound(killer.getLocation(), sound.parseSound(), 1.0F, 1.0F));

        this.chance = this.config.killRewardChance;

        this.config.killRewardGroupChance.keySet().forEach(groupName -> {
            if(LuckPermsUtil.isPlayerInGroup(killer, groupName)) {
                this.chance = this.config.killRewardGroupChance.get(groupName);
            }
        });

        this.boostManager.getBoost(killer).ifPresent(boost -> this.chance = boost.getChance());

        if(ChanceUtil.reachChance(this.chance)) {
            if(this.cooldownManager.hasCooldown(killer, player)) {
                this.messageConfig.cooldown.send(killer, new MapBuilder<String, Object>()
                        .put("time", TimeUtil.convertMills(this.cooldownManager.getRemainingTime(killer, player)))
                        .build());
                return;
            }

            this.commands = this.config.killRewardCommands;

            this.config.killRewardGroupCommands.keySet().forEach(group -> {
                if(LuckPermsUtil.isPlayerInGroup(player, group)) {
                    this.commands = this.config.killRewardGroupCommands.get(group);
                }
            });

            this.commands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                    .replace("{player}", player.getName())
                    .replace("{killer}", killer.getName())
            ));

            this.messageConfig.reward.send(killer, new MapBuilder<String, Object>()
                    .put("player", player.getName())
                    .put("killer", killer.getName())
                    .build());

            this.cooldownManager.setCooldown(killer, player, this.config.killRewardCooldown.toMillis());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();

        if (entity.getKiller() == null || !this.config.killRewardEnabled) return;

        this.config.entityKillRewardCommands.keySet().forEach(entityType -> {
            if (entity.getType() == entityType) {
                this.chance = this.config.killRewardChance;

                this.config.killRewardGroupChance.keySet().forEach(groupName -> {
                    if(LuckPermsUtil.isPlayerInGroup(player, groupName)) {
                        this.chance = this.config.killRewardGroupChance.get(groupName);
                    }
                });

                this.boostManager.getBoost(player).ifPresent(boost -> this.chance = boost.getChance());

                if(ChanceUtil.reachChance(this.chance)) {
                    this.config.entityKillRewardCommands.get(entityType).forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            command.replace("{killer}", player.getName()).replace("{entity}", entity.getType().toString())));
                    this.messageConfig.entityReward.send(player, new MapBuilder<String, Object>()
                            .put("killer", player.getName())
                            .put("entity", entity.getType())
                            .build());
                }
            }
        });
    }
}
