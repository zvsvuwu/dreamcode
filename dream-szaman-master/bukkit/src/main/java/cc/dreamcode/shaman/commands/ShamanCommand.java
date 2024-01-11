package cc.dreamcode.shaman.commands;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.shaman.SzamanPlugin;
import cc.dreamcode.shaman.config.MessageConfig;
import cc.dreamcode.shaman.config.PluginConfig;
import cc.dreamcode.shaman.menu.ShamanMenu;
import cc.dreamcode.shaman.perk.Perk;
import cc.dreamcode.shaman.user.User;
import cc.dreamcode.shaman.user.UserManager;
import cc.dreamcode.shaman.util.RomanUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShamanCommand extends BukkitCommand {
    public ShamanCommand() {
        super("szaman");
    }

    @Inject private SzamanPlugin szamanPlugin;
    @Inject private ShamanMenu shamanMenu;
    @Inject private MessageConfig messageConfig;
    @Inject private PluginConfig pluginConfig;
    @Inject private UserManager userManager;

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;
        if (args.length == 0 || !sender.hasPermission("szaman.command.admin")) {
            this.shamanMenu.build(player).open(player);
            return;
        }
        if (args[0].equalsIgnoreCase("help")) {
            this.messageConfig.perkAdminCommand.send(sender);
            return;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (args.length < 2) {
                this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                        .put("usage", "/szaman reset <nick>")
                        .build());
                return;
            }
            Optional<User> userOptional = this.userManager.getByName(args[1]);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getHealthLvl() == 0 && user.getSpeedLvl() == 0 && user.getDamageLvl() == 0 && user.getLifeStealLvl() == 0 && user.getFallLvl() == 0) {
                    this.messageConfig.resetFail.send(sender);
                    return;
                }
                user.setHealthLvl(0);
                user.setSpeedLvl(0);
                user.setDamageLvl(0);
                user.setLifeStealLvl(0);
                user.setFallLvl(0);
                Player other = this.szamanPlugin.getServer().getPlayer(user.getUniqueId());
                if (other != null && other.isOnline()) {
                    this.userManager.setHealth(user, player);
                    this.userManager.setSpeed(user, player);
                }
                this.messageConfig.resetSuccess.send(sender);
                return;
            }
            this.messageConfig.noPlayer.send(sender);
        }
        else if (args[0].equalsIgnoreCase("get")) {
            if (args.length < 2) {
                this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                        .put("usage", "/szaman get <nick>")
                        .build());
                return;
            }
            Optional<User> userOptional = this.userManager.getByName(args[1]);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                this.messageConfig.perkGetCommand.send(sender, new MapBuilder<String, Object>()
                        .put("perk-health", RomanUtil.arabicToRoman(user.getHealthLvl()))
                        .put("perk-speed", RomanUtil.arabicToRoman(user.getSpeedLvl()))
                        .put("perk-damage", RomanUtil.arabicToRoman(user.getDamageLvl()))
                        .put("perk-spellvamp", RomanUtil.arabicToRoman(user.getLifeStealLvl()))
                        .put("perk-fall", RomanUtil.arabicToRoman(user.getFallLvl()))
                        .build());
                return;
            }
            this.messageConfig.noPlayer.send(sender);
        }
        else if (args[0].equalsIgnoreCase("set")) {
            if (args.length < 4) {
                this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                        .put("usage", "/szaman set <health/speed/damage/spellVamp/fall> <lvl> <player>")
                        .build());
                return;
            }
            Perk perk = perk(args[1]);
            if (perk == null) {
                this.messageConfig.perkNotExists.send(sender);
                return;
            }
            CustomOptional.ofNullable(Integer.parseInt(args[2])).ifPresentOrElse(integer -> {
                Optional<User> userOptional = userManager.getByName(args[3]);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    Player other = this.szamanPlugin.getServer().getPlayer(user.getUniqueId());
                    if (perk.getUpgrades().getOrDefault(integer, null) == null) {
                        this.messageConfig.perkLevelNotExists.send(sender, new MapBuilder<String, Object>()
                                .put("level", RomanUtil.arabicToRoman(integer))
                                .build());
                        return;
                    }
                    if (perk.equals(this.pluginConfig.perks.healthPerk)) {
                        user.setHealthLvl(integer);
                        if (other != null && other.isOnline()) {
                            this.userManager.setHealth(user, other);
                        }
                    }
                    else if (perk.equals(this.pluginConfig.perks.speedPerk)) {
                        user.setSpeedLvl(integer);
                        if (other != null && other.isOnline()) {
                            this.userManager.setSpeed(user, other);
                        }
                    }
                    else if (perk.equals(this.pluginConfig.perks.damagePerk)) {
                        user.setDamageLvl(integer);
                    }
                    else if (perk.equals(this.pluginConfig.perks.lifeStealPerk)) {
                        user.setLifeStealLvl(integer);
                    }
                    else if (perk.equals(this.pluginConfig.perks.fallPerk)) {
                        user.setFallLvl(integer);
                    }
                    messageConfig.updateCommandLevel.send(sender, new MapBuilder<String, Object>()
                            .put("perk", perk.getName())
                            .put("player", user.getName())
                            .put("level", RomanUtil.arabicToRoman(integer))
                            .build());
                    return;
                }
                messageConfig.noPlayer.send(sender);
            }, () -> messageConfig.incorrectLvl.send(sender));

        }
        else if (args[0].equalsIgnoreCase("reload")) {
            this.pluginConfig.load();
            this.messageConfig.load();
            this.userManager.reloadPerks();

            this.messageConfig.reload.send(sender);
        }
        else {
            this.shamanMenu.build(player).open(player);
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!sender.hasPermission("szaman.command.admin")) {
            return null;
        }
        if (args.length == 1) {
            return Arrays.asList("help", "reset", "set", "get", "reload");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("get")) {
                return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
            }
            else if (args[0].equalsIgnoreCase("set")) {
                return Arrays.asList("health", "speed", "damage", "spellVamp", "fall");
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                Perk perk = perk(args[1]);
                if (perk == null) {
                    return null;
                }
                return perk.getUpgrades().keySet().stream().map(integer -> "" + integer).collect(Collectors.toList());
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("set")) {
                return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
            }
        }
        return null;
    }

    public Perk perk(String id) {
        if (id.equalsIgnoreCase("health")) {
            return this.pluginConfig.perks.healthPerk;
        }
        else if (id.equalsIgnoreCase("speed")) {
            return this.pluginConfig.perks.speedPerk;
        }
        else if (id.equalsIgnoreCase("damage")) {
            return this.pluginConfig.perks.damagePerk;
        }
        else if (id.equalsIgnoreCase("spellvamp")) {
            return this.pluginConfig.perks.lifeStealPerk;
        }
        else if (id.equalsIgnoreCase("fall")) {
            return this.pluginConfig.perks.fallPerk;
        }
        return null;
    }
}
