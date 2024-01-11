package xyz.ravis96.dreamkit.commands.user;

import com.google.common.collect.ImmutableMap;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.ravis96.dreamkit.commands.CommandUse;
import xyz.ravis96.dreamkit.config.subconfig.KitConfig;
import xyz.ravis96.dreamkit.features.kit.Kit;
import xyz.ravis96.dreamkit.features.menu.IMenu;
import xyz.ravis96.dreamkit.features.menu.MenuBase;
import xyz.ravis96.dreamkit.features.menu.MenuHolder;
import xyz.ravis96.dreamkit.features.user.User;
import xyz.ravis96.dreamkit.helpers.ItemReplacer;
import xyz.ravis96.dreamkit.utils.ChatUtil;
import xyz.ravis96.dreamkit.utils.TimeUtil;
import xyz.ravis96.dreamkit.utils.optional.OptionalConsumer;

import java.util.HashMap;
import java.util.Optional;

@FunnyComponent
public class KitCommand extends CommandUse {

    @FunnyCommand(
            name = "${user.kit.name}",
            description = "${user.kit.description}",
            aliases = "${user.kit.aliases}",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void execute(Player p, String[] args) {
        if(!this.pluginStorage.isDisabledKits()){
            this.sendMessage(p, this.messageConfig.kitsIsDisabled);
            return;
        }

        this.build().open(p);
    }

    public IMenu build() {
        final KitConfig config = this.pluginConfig.getKitConfig();
        final MenuBase menuBase = new MenuBase(ChatUtil.fixColor(config.name), config.rows);

        config.items.forEach((integer, itemStack) -> {
            Optional<Kit> optionalKit = config.kitList
                    .stream()
                    .filter(kit -> kit.getName().equals(config.itemEvent.get(integer)))
                    .findFirst();

            OptionalConsumer.of(optionalKit).ifPresentOrElse(kit -> {
                ItemStack is = new ItemReplacer(itemStack).fixColorsWithReplace(new HashMap<String, String>() {{
                    this.put("%NAME%", kit.getName());
                    this.put("%TIME%", TimeUtil.convertLong(kit.getCoolDownTime()));
                }});

                menuBase.setItem(integer, is, e -> {
                    if(!(e.getWhoClicked() instanceof Player)) return;
                    final Player p = (Player) e.getWhoClicked();
                    final User u = this.userManager.getOrCreate(p);

                    if(this.pluginStorage.getDisabledKit().contains(kit.getName())) {
                        this.sendMessage(p, this.messageConfig.kitIsDisabled);
                        return;
                    }

                    if (e.isLeftClick()) {
                        if (!p.hasPermission("dc.kit.bypass")) {
                            if (!p.hasPermission(kit.getPermission())) {
                                this.sendMessage(p, this.messageConfig.noKitPermission);
                                p.closeInventory();
                                return;
                            }

                            if (u.getKitCoolDownMap().containsKey(kit)) {
                                long seconds = (u.getKitCoolDownMap().get(kit) / 1000) + kit.getCoolDownTime() - (System.currentTimeMillis() / 1000);
                                if (seconds > 0) {
                                    this.sendMessage(p, this.messageConfig.kitCoolDown, new ImmutableMap.Builder<String, String>()
                                            .put("%TIME%", TimeUtil.convertLong(seconds)).build());
                                    p.closeInventory();
                                    return;
                                }
                            }

                            this.pluginMain.getServer().getScheduler().runTaskAsynchronously(this.pluginMain, () -> {
                                u.getKitCoolDownMap().put(kit, System.currentTimeMillis());
                                u.save();
                            });
                        }

                        Inventory inv = Bukkit.createInventory(null, config.rowsKit * 9, ChatUtil.fixColor(config.nameKit));
                        kit.getItemStackList().forEach(inv::addItem);
                        p.openInventory(inv);
                        this.sendMessage(p, this.messageConfig.kitPickedUp);
                    }
                    if (e.isRightClick()) {
                        MenuBase kitView = new MenuBase(ChatUtil.fixColor(config.nameKitView), config.rowsKit);
                        kit.getItemStackList().forEach(kitView::addItem);
                        kitView.setItem(config.rowsKit * 9 - 1, new ItemReplacer(config.backButton).fixColors(), e1 ->
                                this.build().open(p));
                        new MenuHolder(kitView).open(p);
                    }
                });
            }, () -> menuBase.setItem(integer, new ItemReplacer(itemStack).fixColors()));
        });
        return new MenuHolder(menuBase);
    }
}
