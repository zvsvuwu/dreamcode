package cc.dreamcode.totem.controller;

import cc.dreamcode.totem.TotemEffect;
import cc.dreamcode.totem.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class UserController implements Listener {
    @Inject private UserRepository userRepository;
    @Inject private Tasker tasker;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.tasker.newSharedChain(player.getUniqueId().toString())
                .async(() -> this.userRepository.findOrCreateByHumanEntity(player))
                .acceptSync(user -> {
                    user.setName(player.getName());
                })
                .acceptAsync(user -> {
                    user.save();
                })
                .execute();
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        this.tasker.newSharedChain(player.getUniqueId().toString())
                .async(() -> this.userRepository.findOrCreateByHumanEntity(player))
                .acceptAsync(user -> {
                    user.save();
                })
                .execute();
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event){
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        this.tasker.newSharedChain(player.getUniqueId().toString())
                .async(() -> this.userRepository.findOrCreateByHumanEntity(player))
                .acceptSync(user -> {
                    if(user.getTotemEffect() == null) return;
                    TotemEffect totemEffect = user.getTotemEffect();
                    PotionEffect potionEffect = user.getTotemEffect().getPotionEffect();
                    user.setTotemEffect(null);

                    //For opponent
                    if(totemEffect.isForOpponent()){
                        Player opponent = Bukkit.getPlayer(user.getLastDamagerUUID());
                        if(opponent == null) return;

                        opponent.addPotionEffect(potionEffect);
                        return;
                    }

                    player.addPotionEffect(potionEffect);
                }).acceptAsync(user -> {
                    user.save();
                })
                .execute();
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        this.tasker.newChain()
                .async(() -> this.userRepository.findOrCreateByHumanEntity(player))
                .acceptSync(user -> {
                    user.setLastDamagerUUID(damager.getUniqueId());
                })
                .acceptAsync(user -> {
                    user.save();
                })
                .execute();
    }
}
