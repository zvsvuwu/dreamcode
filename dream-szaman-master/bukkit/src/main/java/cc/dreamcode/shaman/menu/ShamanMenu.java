package cc.dreamcode.shaman.menu;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.shaman.config.PluginConfig;
import cc.dreamcode.shaman.perk.Perk;
import cc.dreamcode.shaman.perk.PerkUpgrade;
import cc.dreamcode.shaman.user.User;
import cc.dreamcode.shaman.user.UserManager;
import cc.dreamcode.shaman.util.RomanUtil;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShamanMenu implements BukkitMenuPlayerSetup {

    @Inject private PluginConfig pluginConfig;
    @Inject private BukkitMenuProvider bukkitMenuProvider;
    @Inject private UserManager userManager;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder shamanMenuBuilder = this.pluginConfig.shamanMenu;
        final BukkitMenu shamanMenu = shamanMenuBuilder.buildEmpty();

        shamanMenuBuilder.getItems().forEach(((integer, itemStack) -> shamanMenu.setItem(integer, itemStack, e -> e.setCancelled(true))));

        Optional<User> user = this.userManager.getUser(humanEntity.getUniqueId());
        if (user.isPresent()) {
            User u = user.get();
            display(shamanMenu.getInventory(), this.pluginConfig.perks.healthPerk, (u.getHealthLvl() + 1));
            display(shamanMenu.getInventory(), this.pluginConfig.perks.speedPerk, (u.getSpeedLvl() + 1));
            display(shamanMenu.getInventory(), this.pluginConfig.perks.damagePerk, (u.getDamageLvl() + 1));
            display(shamanMenu.getInventory(), this.pluginConfig.perks.lifeStealPerk, (u.getLifeStealLvl() + 1));
            display(shamanMenu.getInventory(), this.pluginConfig.perks.fallPerk, (u.getFallLvl() + 1));
        }
        return shamanMenu;
    }

    public void display(Inventory inventory, Perk perk, int userLvl) {
        ItemStack itemStack = inventory.getItem(perk.getSlot());
        if (itemStack != null) {
            ItemBuilder itemBuilder = new ItemBuilder(itemStack.clone());
            itemBuilder.setName(ChatUtil.fixColor(perk.getGuiName()));
            List<String> lore = this.pluginConfig.perkDisplay;
            List<String> newLore = new ArrayList<>();
            for (String line : lore) {
                if (line.contains("{level}")) {
                    newLore.add(PlaceholderContext.of(CompiledMessage.of(line))
                            .with("level", RomanUtil.arabicToRoman((userLvl - 1)) + "")
                            .apply());
                    continue;
                }
                else if (line.contains("{levels}")) {
                    for (Map.Entry<Integer, PerkUpgrade> entry : perk.getUpgrades().entrySet()) {
                        PerkUpgrade perkUpgrade = entry.getValue();
                        int valueInt = (int) perkUpgrade.getValue();
                        newLore.add(PlaceholderContext.of(CompiledMessage.of(this.pluginConfig.levelsDisplay))
                                .with("lvl", RomanUtil.arabicToRoman(entry.getKey()))
                                .with("perk-desc", PlaceholderContext.of(CompiledMessage.of(perk.getPerkDesc()))
                                        .with("value", (perkUpgrade.getValue() % 1 == 0 ? valueInt + "" : "" + perkUpgrade.getValue())).apply())
                                /*.with("perk-desc", perk.getPerkDesc())

                                .with("value", (perkUpgrade.getValue() % 1 == 0 ? valueInt + "" : "" + perkUpgrade.getValue()) + "")*/
                                .apply());
                    }
                    continue;
                }
                else if (line.contains("{cost}")) {
                    PerkUpgrade perkUpgrade = perk.getUpgrades().getOrDefault(userLvl, null);
                    if (perkUpgrade == null) {
                        newLore.add(PlaceholderContext.of(CompiledMessage.of(line))
                                .with("cost", this.pluginConfig.costMax)
                                .apply());
                        continue;
                    }
                    String cost;
                    if (perkUpgrade.getMoney() != 0 && perkUpgrade.getCost() != null) {
                        cost = PlaceholderContext.of(CompiledMessage.of(this.pluginConfig.costDisplayBoth))
                                .with("item", "x" + perkUpgrade.getCost().getAmount() + " " + perkUpgrade.getCost().getType().toString().toLowerCase().replace("_", " "))
                                .with("money", perkUpgrade.getMoney())
                                .apply();
                    }
                    else if (perkUpgrade.getMoney() == 0 && perkUpgrade.getCost() != null) {
                        cost = PlaceholderContext.of(CompiledMessage.of(this.pluginConfig.costDisplayOnlyItem))
                                .with("item", "x" + perkUpgrade.getCost().getAmount() + " " + perkUpgrade.getCost().getType().toString().toLowerCase().replace("_", " "))
                                .apply();
                    }
                    else {
                        cost = PlaceholderContext.of(CompiledMessage.of(this.pluginConfig.costDisplayOnlyMoney))
                                .with("money", perkUpgrade.getMoney())
                                .apply();
                    }
                    newLore.add(PlaceholderContext.of(CompiledMessage.of(line))
                            .with("cost", cost)
                            .apply());
                    continue;
                }
                newLore.add(line);
            }
            newLore.replaceAll(ChatUtil::fixColor);
            itemBuilder.setLore(newLore);
            inventory.setItem(perk.getSlot(), itemBuilder.toItemStack());
        }
    }
}
