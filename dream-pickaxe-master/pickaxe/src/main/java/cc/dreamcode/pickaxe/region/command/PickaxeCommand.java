package cc.dreamcode.pickaxe.region.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.pickaxe.config.MessageConfig;
import cc.dreamcode.pickaxe.config.PluginConfig;
import cc.dreamcode.pickaxe.region.Region;
import cc.dreamcode.pickaxe.user.User;
import cc.dreamcode.pickaxe.user.UserRepository;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredPermission(permission = "dream.pickaxe")
public class PickaxeCommand extends BukkitCommand {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    public PickaxeCommand() {
        super("dreampickaxe");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player)) return;

        Player source = (Player) sender;
        User user = this.userRepository.findOrCreateByHumanEntity(source);

        if (args.length == 0) {
            this.messageConfig.correctUsage.send(sender);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "wand": {
                ItemStack itemStack = this.pluginConfig.regionWand.clone();

                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) {
                    this.messageConfig.regionWandDoesNotHaveItemMeta.send(source);
                    return;
                }
                itemMeta.setDisplayName(ChatUtil.fixColor(itemMeta.getDisplayName()));
                if (itemMeta.getLore() != null) {
                    itemMeta.setLore(ChatUtil.fixColor(itemMeta.getLore()));
                }

                itemStack.setItemMeta(itemMeta);
                source.getInventory().addItem(itemStack);
                break;
            }
            case "set": {
                if (args.length < 2) {
                    this.messageConfig.correctUsage.send(sender);
                    return;
                }

                if (!user.isRegionReady()) {
                    this.messageConfig.youForgotToSelectCorners.send(sender);
                    return;
                }

                String regionName = args[1];
                for (Region region : this.pluginConfig.regions) {
                    if (region.getRegion().equalsIgnoreCase(regionName)) {
                        this.messageConfig.regionWithThisNameAlreadyExist.send(sender);
                        return;
                    }
                }

                user.setRegionName(regionName);
                this.messageConfig.setRegionName.send(sender);

                this.pluginConfig.regions.add(Region.fromCorners(user.getRegionName(), user.getFirstCorner(), user.getSecondCorner()));
                this.pluginConfig.save();

                user.setRegionName(null);
                user.setFirstCorner(null);
                user.setSecondCorner(null);

                user.save();
                break;
            }
            case "delete": {
                if (args.length < 2) {
                    this.messageConfig.correctUsage.send(sender);
                    return;
                }

                String regionName = args[1];
                for (Region region : new ArrayList<>(this.pluginConfig.regions)) {
                    if (region.getRegion().equalsIgnoreCase(regionName)) {
                        this.pluginConfig.regions.remove(region);
                        this.pluginConfig.save();
                        this.messageConfig.deletedRegion.send(sender);
                        return;
                    }
                }
                this.messageConfig.regionWithThisNameWasNotFound.send(sender);
                break;
            }
            case "reload": {
                this.pluginConfig.load(true);
                this.messageConfig.load(true);
                this.messageConfig.reloaded.send(sender);
                break;
            }
            case "level": {
                if (args.length < 3) {
                    this.messageConfig.correctUsage.send(sender);
                    return;
                }

                String regionName = args[1];
                for (Region region : new ArrayList<>(this.pluginConfig.regions)) {
                    if (region.getRegion().equalsIgnoreCase(regionName)) {
                        String efficiencyLevel = args[2];
                        int efficiency;
                        try {
                            efficiency = Integer.parseInt(efficiencyLevel);
                            if (efficiency <= 0) {
                                this.messageConfig.efficiencyCantBeLessThenZero.send(sender);
                                return;
                            }
                            region.setMinEfficiencyLevel(efficiency);
                            this.messageConfig.successfullySetMinLevel.send(sender);
                        } catch (NumberFormatException exception) {
                            this.messageConfig.notNumber.send(sender);
                        }
                        return;
                    }
                }
                this.messageConfig.regionWithThisNameWasNotFound.send(sender);
                break;
            }
            case "blocks": {
                if (args.length < 4) {
                    this.messageConfig.correctUsage.send(sender);
                    return;
                }

                String regionName = args[1];
                for (Region region : new ArrayList<>(this.pluginConfig.regions)) {
                    if (region.getRegion().equalsIgnoreCase(regionName)) {
                        switch (args[2].toLowerCase()) {
                            case "add": {
                                Material material;
                                try {
                                    material = Material.valueOf(args[3].toUpperCase());
                                } catch (Exception e) {
                                    this.messageConfig.invalidMaterial.send(sender);
                                    return;
                                }
                                this.messageConfig.addedMaterial.send(sender);
                                region.getAllowedMaterials().add(material.name());
                                break;
                            }
                            case "remove": {
                                Material material;
                                try {
                                    material = Material.valueOf(args[3].toUpperCase());
                                } catch (Exception e) {
                                    this.messageConfig.invalidMaterial.send(sender);
                                    return;
                                }
                                this.messageConfig.removedMaterial.send(sender);
                                region.getAllowedMaterials().remove(material.name());
                                break;
                            }
                            default: {
                                this.messageConfig.correctUsage.send(sender);
                                break;
                            }
                        }
                        return;
                    }
                }
                this.messageConfig.regionWithThisNameWasNotFound.send(sender);
                break;
            }
            default: {
                this.messageConfig.correctUsage.send(sender);
                break;
            }
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        List<String> result = new ArrayList<>();

        if (!sender.hasPermission("dream.pickaxe")) {
            return result;
        }

        if (args.length == 1) {
            result.addAll(Arrays.asList("wand", "reload", "set", "delete", "level", "blocks"));
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "wand":
                case "reload": {
                    break;
                }
                case "set": {
                    result.add("przykladowa_nazwa_regionu");
                    break;
                }
                case "delete":
                case "level":
                case "blocks": {
                    result.addAll(this.pluginConfig.regions.stream().map(Region::getRegion).collect(Collectors.toList()));
                    break;
                }
            }
        } else if (args.length == 3) {
            switch (args[0].toLowerCase()) {
                case "level": {
                    result.addAll(Arrays.asList("1", "2", "3", "4", "5"));
                    break;
                }
                case "blocks": {
                    result.addAll(Arrays.asList("add", "remove"));
                    break;
                }
            }
        } else if (args.length == 4 && args[0].equalsIgnoreCase("blocks")) {
            result.addAll(Arrays.asList("STONE", "COBBLESTONE"));
        }

        return result;
    }
}