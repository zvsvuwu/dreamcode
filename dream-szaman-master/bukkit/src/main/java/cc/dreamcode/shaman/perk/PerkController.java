package cc.dreamcode.shaman.perk;

import cc.dreamcode.shaman.config.MessageConfig;
import cc.dreamcode.shaman.config.PluginConfig;
import cc.dreamcode.shaman.menu.ShamanMenu;
import cc.dreamcode.shaman.user.UserManager;
import cc.dreamcode.shaman.util.RomanUtil;
import cc.dreamcode.shaman.vault.VaultHook;
import cc.dreamcode.utilities.ChanceUtil;
import cc.dreamcode.utilities.RoundUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PerkController implements Listener {

    @Inject private UserManager userManager;
    @Inject private PluginConfig pluginConfig;
    @Inject private MessageConfig messageConfig;
    @Inject private ShamanMenu shamanMenu;
    @Inject private PerkManager perkManager;
    @Inject private VaultHook vaultHook;

    public boolean sameChecks(PerkUpgrade perkUpgrade, HumanEntity humanEntity) {
        if (perkUpgrade == null) {
            this.messageConfig.upgradeMax.send(humanEntity);
            return false;
        }
        if (perkUpgrade.getCost() != null && !humanEntity.getInventory().containsAtLeast(perkUpgrade.getCost(), perkUpgrade.getCost().getAmount())) {
            this.messageConfig.upgradeHavingNoItems.send(humanEntity);
            return false;
        }
        if (perkUpgrade.getMoney() != 0 && this.vaultHook.isUseVault() &&
                !this.vaultHook.getEconomyStorage().getEconomy().has((OfflinePlayer) humanEntity, perkUpgrade.getMoney())) {
            this.messageConfig.upgradeHavingNoMoney.send(humanEntity);
            return false;
        }
        if (perkUpgrade.getCost() != null) {
            humanEntity.getInventory().removeItem(perkUpgrade.getCost().clone());
        }
        if (perkUpgrade.getMoney() != 0 && this.vaultHook.isUseVault()) {
            EconomyResponse economyResponse = this.vaultHook.getEconomyStorage().getEconomy().withdrawPlayer((OfflinePlayer) humanEntity, perkUpgrade.getMoney());
            if (!economyResponse.transactionSuccess()) {
                this.messageConfig.transactionFail.send(humanEntity);
                return false;
            }
        }
        return true;
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColor(this.pluginConfig.shamanMenu.getName()))) {
            HumanEntity humanEntity = event.getWhoClicked();
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            this.userManager.getUser(humanEntity.getUniqueId()).ifPresent(user -> {
                if (event.getRawSlot() == this.pluginConfig.perks.healthPerk.getSlot()) {
                    int userLvl = user.getHealthLvl();
                    PerkUpgrade perkUpgrade = this.pluginConfig.perks.healthPerk.getUpgrades().getOrDefault((userLvl + 1), null);
                    if (!sameChecks(perkUpgrade, humanEntity)) {
                        return;
                    }
                    user.setHealthLvl(user.getHealthLvl() + 1);
                    this.messageConfig.upgradeSuccess.send(humanEntity, new MapBuilder<String, Object>()
                            .put("perk", this.pluginConfig.perks.healthPerk.getName())
                            .put("level", RomanUtil.arabicToRoman(user.getHealthLvl()))
                            .build());
                    this.shamanMenu.display(event.getInventory(), this.pluginConfig.perks.healthPerk, (user.getHealthLvl() + 1));
                    this.userManager.setHealth(user, (Player) humanEntity);
                }
                else if (event.getRawSlot() == this.pluginConfig.perks.speedPerk.getSlot()) {
                    int userLvl = user.getSpeedLvl();
                    PerkUpgrade perkUpgrade = this.pluginConfig.perks.speedPerk.getUpgrades().getOrDefault((userLvl + 1), null);
                    if (!sameChecks(perkUpgrade, humanEntity)) {
                        return;
                    }
                    user.setSpeedLvl(user.getSpeedLvl() + 1);
                    this.messageConfig.upgradeSuccess.send(humanEntity, new MapBuilder<String, Object>()
                            .put("perk", this.pluginConfig.perks.speedPerk.getName())
                            .put("level", RomanUtil.arabicToRoman(user.getSpeedLvl()))
                            .build());
                    this.shamanMenu.display(event.getInventory(), this.pluginConfig.perks.speedPerk, (user.getSpeedLvl() + 1));
                    this.userManager.setSpeed(user, (Player) humanEntity);
                }
                else if (event.getRawSlot() == this.pluginConfig.perks.damagePerk.getSlot()) {
                    int userLvl = user.getDamageLvl();
                    PerkUpgrade perkUpgrade = this.pluginConfig.perks.damagePerk.getUpgrades().getOrDefault((userLvl + 1), null);
                    if (!sameChecks(perkUpgrade, humanEntity)) {
                        return;
                    }
                    user.setDamageLvl(user.getDamageLvl() + 1);
                    this.messageConfig.upgradeSuccess.send(humanEntity, new MapBuilder<String, Object>()
                            .put("perk", this.pluginConfig.perks.damagePerk.getName())
                            .put("level", RomanUtil.arabicToRoman(user.getDamageLvl()))
                            .build());
                    this.shamanMenu.display(event.getInventory(), this.pluginConfig.perks.damagePerk, (user.getDamageLvl() + 1));
                }
                else if (event.getRawSlot() == this.pluginConfig.perks.lifeStealPerk.getSlot()) {
                    int userLvl = user.getLifeStealLvl();
                    PerkUpgrade perkUpgrade = this.pluginConfig.perks.lifeStealPerk.getUpgrades().getOrDefault((userLvl + 1), null);
                    if (!sameChecks(perkUpgrade, humanEntity)) {
                        return;
                    }
                    user.setLifeStealLvl(user.getLifeStealLvl() + 1);
                    this.messageConfig.upgradeSuccess.send(humanEntity, new MapBuilder<String, Object>()
                            .put("perk", this.pluginConfig.perks.lifeStealPerk.getName())
                            .put("level", RomanUtil.arabicToRoman(user.getLifeStealLvl()))
                            .build());
                    this.shamanMenu.display(event.getInventory(), this.pluginConfig.perks.lifeStealPerk, (user.getLifeStealLvl() + 1));
                }
                else if (event.getRawSlot() == this.pluginConfig.perks.fallPerk.getSlot()) {
                    int userLvl = user.getFallLvl();
                    PerkUpgrade perkUpgrade = this.pluginConfig.perks.fallPerk.getUpgrades().getOrDefault((userLvl + 1), null);
                    if (!sameChecks(perkUpgrade, humanEntity)) {
                        return;
                    }
                    user.setFallLvl(user.getFallLvl() + 1);
                    this.messageConfig.upgradeSuccess.send(humanEntity, new MapBuilder<String, Object>()
                            .put("perk", this.pluginConfig.perks.fallPerk.getName())
                            .put("level", RomanUtil.arabicToRoman(user.getFallLvl()))
                            .build());
                    this.shamanMenu.display(event.getInventory(), this.pluginConfig.perks.fallPerk, (user.getFallLvl() + 1));
                }

            });
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            Player player = (Player) event.getEntity();
            this.userManager.getUser(player.getUniqueId()).ifPresent(user -> {
                if (user.getFallLvl() > 0) {
                    if (ChanceUtil.reachChance(this.pluginConfig.perks.fallPerk.getUpgrades().get(user.getFallLvl()).getValue())) {
                        event.setDamage(0.0);
                        this.messageConfig.playerFallPerkMessage.send(player);
                    }
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            this.userManager.getUser(player.getUniqueId()).ifPresent(user -> {
                double firstDamage = event.getDamage();
                double damage = 0;
                double spellVamp = 0;
                if (user.getDamageLvl() > 0) {
                    double percent = this.pluginConfig.perks.damagePerk.getUpgrades().get(user.getDamageLvl()).getValue();
                    damage = ((event.getDamage() * percent) / 100.0);
                    event.setDamage((event.getDamage() + damage));
                    damage = event.getDamage();
                }
                if (user.getLifeStealLvl() > 0 && player.getHealth() < player.getMaxHealth()) {
                    double percent = this.pluginConfig.perks.lifeStealPerk.getUpgrades().get(user.getLifeStealLvl()).getValue();
                    spellVamp = (event.getFinalDamage() * percent) / 100.0;
                    if ((player.getHealth() + spellVamp) >= player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                    }
                    else {
                        player.setHealth((player.getHealth() + spellVamp));
                    }
                }
                if (damage != 0 && spellVamp != 0) {
                    if (this.messageConfig.bothMessageInOne) {
                        this.messageConfig.playerDamageAndSpellVampMessage.send(player, new MapBuilder<String, Object>()
                                .put("damagemessage", PlaceholderContext.of(CompiledMessage.of(this.messageConfig.playerDamageMessage.getText()))
                                        .with("damage", RoundUtil.round(damage, 2))
                                        .with("predamage", firstDamage)
                                        .apply())
                                .put("spellvampmessage", PlaceholderContext.of(CompiledMessage.of(this.messageConfig.playerSpellVampMessage.getText()))
                                        .with("heal", RoundUtil.round(spellVamp, 2))
                                        .apply())
                                .build());
                        return;
                    }
                }
                if (damage != 0) {
                    this.messageConfig.playerDamageMessage.send(player, new MapBuilder<String, Object>()
                            .put("damage", RoundUtil.round(damage, 2))
                            .put("predamage", firstDamage)
                            .build()
                    );
                }
                if (spellVamp != 0) {
                    this.messageConfig.playerSpellVampMessage.send(player, new MapBuilder<String, Object>()
                            .put("heal", RoundUtil.round(spellVamp, 2))
                            .build()
                    );
                }
            });
        }
    }
}
