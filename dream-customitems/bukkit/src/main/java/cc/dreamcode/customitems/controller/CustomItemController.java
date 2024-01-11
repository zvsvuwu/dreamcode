package cc.dreamcode.customitems.controller;

import cc.dreamcode.customitems.CustomItemsPlugin;
import cc.dreamcode.customitems.config.MessageConfig;
import cc.dreamcode.customitems.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CustomItemController implements Listener {

    private final CustomItemsPlugin plugin;
    private final PluginConfig config;
    private final MessageConfig messageConfig;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();

        if(block.getType().equals(this.config.damagePlate.getType())) {
            player.damage(this.config.damagePlateDamage);
            block.setType(Material.AIR);
            player.setCooldown(this.config.damagePlate.getType(), this.convertMillisecondsToTicks(this.config.damagePlateCooldown.toMillis()));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        if(block.getType().equals(this.config.damagePlate.getType()) && player.hasCooldown(this.config.damagePlate.getType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        ThrownPotion potion = event.getPotion();

        if (!(potion.getShooter() instanceof Player) || !potion.getItem().isSimilar(this.config.buildSpiderGrenade())) return;

        Player player = (Player) potion.getShooter();

        event.setCancelled(true);

        player.setCooldown(this.config.spiderGrenade.getType(),
                this.convertMillisecondsToTicks(this.config.spiderGrenadeCooldown.toMillis()));
        this.placeCobweb(potion.getLocation());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) return;

        if(item.isSimilar(ItemBuilder.of(this.config.crossbow).fixColors().toItemStack())
                && player.hasCooldown(this.config.crossbow.getType())) {
            event.setCancelled(true);
        }

        if (item.isSimilar(ItemBuilder.of(this.config.spiderGrenade).fixColors().toItemStack()) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                && player.hasCooldown(this.config.spiderGrenade.getType())) {
            event.setCancelled(true);
        }

        if(item.isSimilar(ItemBuilder.of(this.config.snowball).fixColors().toItemStack()) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                && player.hasCooldown(this.config.snowball.getType())) {
            event.setCancelled(true);
        }

        if (item.isSimilar(ItemBuilder.of(this.config.bow).fixColors().toItemStack()) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                && player.hasCooldown(this.config.bow.getType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();

        if(itemInMainHand.isSimilar(this.config.buildTotem()) || itemInOffHand.isSimilar(this.config.buildTotem())) {
            event.getDrops().clear();
            event.setKeepInventory(true);
            if(itemInMainHand.isSimilar(this.config.buildTotem())) {
                player.getInventory().setItemInMainHand(null);
            } else if(itemInOffHand.isSimilar(this.config.buildTotem())) {
                player.getInventory().setItemInOffHand(null);
            }
        }
    }

    @EventHandler
    public void onItemConsume(EntityResurrectEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();

        if(itemInMainHand.isSimilar(this.config.buildTotem()) || itemInOffHand.isSimilar(this.config.buildTotem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();

        if(!(arrow.getShooter() instanceof Player) || event.isCancelled()) {
            return;
        }

        Player player = (Player) arrow.getShooter();
        Entity entity = event.getHitEntity();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack iteminOffHand = player.getInventory().getItemInOffHand();

        if (itemInMainHand.isSimilar(this.config.buildCrossbow()) || iteminOffHand.isSimilar(this.config.buildCrossbow())) {
            if (Math.random() <= this.config.crossbowPercent && entity != null && !entity.isDead()) {
                entity.teleport(player);
            }
            player.setCooldown(this.config.crossbow.getType(), this.convertMillisecondsToTicks(this.config.crossbowCooldown.toMillis()));
        } else if(itemInMainHand.isSimilar(this.config.buildBow()) || iteminOffHand.isSimilar(this.config.buildBow())) {
            if (Math.random() <= this.config.bowPercent && entity != null && !entity.isDead()) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> entity.setVelocity(entity.getVelocity().add(entity.getLocation().getDirection().setY(this.config.bowHeight))));
            }
            player.setCooldown(this.config.bow.getType(), this.convertMillisecondsToTicks(this.config.bowCooldown.toMillis()));
        }
    }

    @EventHandler
    public void onSnowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball)) {
            return;
        }

        Snowball snowball = (Snowball) event.getEntity();

        if(!(snowball.getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) snowball.getShooter();
        Entity entity = event.getHitEntity();

        if (player.getInventory().getItemInMainHand().isSimilar(this.config.buildSnowball())) {

            player.setCooldown(this.config.snowball.getType(), this.convertMillisecondsToTicks(this.config.snowballCooldown.toMillis()));

            if (Math.random() <= this.config.snowballPercent) {
                if (entity instanceof Player && !entity.isDead()) {
                    Location entityLocation = entity.getLocation();
                    Location playerLocation = player.getLocation();

                    entity.teleport(playerLocation);
                    player.teleport(entityLocation);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        Player victim = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if(!damager.getInventory().getItemInMainHand().isSimilar(this.config.buildBone())) return;

        if(damager.hasCooldown(this.config.bone.getType())) {
            event.setCancelled(true);
            return;
        }

        damager.setCooldown(this.config.bone.getType(), this.convertMillisecondsToTicks(this.config.boneCooldown.toMillis()));

        if(Math.random() < this.config.bonePercent) {
            for(PotionEffect effect : this.config.boneEffects) {
                victim.addPotionEffect(effect);
            }

            this.messageConfig.boneStun.send(damager, new MapBuilder<String, Object>().put("player", victim.getName()).build());
        }
    }

    private void placeCobweb(Location location) {
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        int height = this.config.spiderGrenadeHeight;
        int width = this.config.spiderGrenadeWidth;

        for (int xOffset = -width/2; xOffset <= width/2; xOffset++) {
            for (int yOffset = -height/2; yOffset <= height/2; yOffset++) {
                for (int zOffset = -width/2; zOffset <= width/2; zOffset++) {
                    assert world != null;
                    Block block = world.getBlockAt(x + xOffset, y + yOffset, z + zOffset);
                    if (block.getType() == XMaterial.AIR.parseMaterial() || block.getType() == XMaterial.SNOW.parseMaterial() || block.getType() == XMaterial.GRASS.parseMaterial()) {
                        assert XMaterial.COBWEB.parseMaterial() != null;
                        block.setType(XMaterial.COBWEB.parseMaterial());
                    }
                }
            }
        }
    }

    private int convertMillisecondsToTicks(long ticks) {
        double millisecondsPerTick = 1000.0 / 20.0;
        return (int) (ticks * millisecondsPerTick);
    }
}
