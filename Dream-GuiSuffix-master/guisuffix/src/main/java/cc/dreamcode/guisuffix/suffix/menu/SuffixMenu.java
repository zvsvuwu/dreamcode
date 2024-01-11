package cc.dreamcode.guisuffix.suffix.menu;

import cc.dreamcode.guisuffix.config.MessageConfig;
import cc.dreamcode.guisuffix.config.PluginConfig;
import cc.dreamcode.guisuffix.config.subconfig.MenuConfig;
import cc.dreamcode.guisuffix.hook.vault.VaultHook;
import cc.dreamcode.guisuffix.suffix.SuffixService;
import cc.dreamcode.guisuffix.suffix.menu.item.SuffixItem;
import cc.dreamcode.guisuffix.user.User;
import cc.dreamcode.guisuffix.user.UserCache;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.platform.hook.DreamHookManager;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class SuffixMenu implements BukkitMenuPlayerSetup {

    private final MessageConfig messageConfig;

    private final SuffixService suffixService;

    private final DreamHookManager dreamHookManager;

    private final MenuConfig menuConfig;

    private final UserCache userCache;

    private final Tasker tasker;

    @Inject
    public SuffixMenu(PluginConfig pluginConfig, MessageConfig messageConfig, SuffixService suffixService, DreamHookManager dreamHookManager, UserCache userCache, Tasker tasker) {
        this.messageConfig = messageConfig;
        this.suffixService = suffixService;
        this.dreamHookManager = dreamHookManager;
        this.menuConfig = pluginConfig.menuConfig;
        this.userCache = userCache;
        this.tasker = tasker;
    }

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final Player player = (Player) humanEntity;

        final User user = this.userCache.get(player);

        BukkitMenuBuilder bukkitMenuBuilder = this.menuConfig.suffixMenu;
        BukkitMenu bukkitMenu = bukkitMenuBuilder.buildWithItems();

        for (SuffixItem suffixItem : this.menuConfig.suffixes) {
            ItemStack item = ItemBuilder.of(suffixItem.getItemStack()).fixColors(new MapBuilder<String, Object>()
                    .put("owned", this.suffixService.hasPurchased(user, suffixItem) ? this.menuConfig.owned : "")
                    .put("details", getDetails(player, user, suffixItem))
                    .put("price", suffixItem.getPrice())
                    .build()).toItemStack();
            Optional<VaultHook> vaultHook = this.dreamHookManager.get(VaultHook.class);

            bukkitMenu.setItem(suffixItem.getSlot(), item, event -> {
                event.setCancelled(true);
                player.closeInventory();

                if (this.suffixService.getSuffix(player) != null && this.suffixService.getSuffix(player).equals(suffixItem.getSuffix())) {
                    this.messageConfig.suffixInUse.send(player);
                    return;
                }

                if (!user.getSuffixes().contains(suffixItem.getName())) {
                    if (suffixItem.getPrice() == -1 || !vaultHook.isPresent()) {
                        this.messageConfig.noPermissionSuffix.send(player);
                        return;
                    }

                    double playerBalance = vaultHook.get().getVaultApi().getEconomy().getBalance(player);

                    if (suffixItem.getPrice() > playerBalance) {
                        this.messageConfig.notEnoughMoney.send(player);
                        return;
                    }

                    if (!vaultHook.get().getVaultApi().isSuccess(vaultHook.get().getVaultApi().getEconomy().withdrawPlayer(player, suffixItem.getPrice()))) {
                        this.messageConfig.unknownProblem.send(player);
                        return;
                    }

                    this.messageConfig.suffixPurchased.send(player, new MapBuilder<String, Object>()
                            .put("price", suffixItem.getPrice())
                            .put("suffix", suffixItem.getName())
                            .put("formatted", suffixItem.getSuffix().trim())
                            .build());
                }

                this.suffixService.setSuffix(player, suffixItem.getSuffix());

                this.messageConfig.suffixChanged.send(player, new MapBuilder<String, Object>()
                        .put("suffix", suffixItem.getName())
                        .put("formatted", suffixItem.getSuffix().trim())
                        .build());

                if (!user.getSuffixes().contains(suffixItem.getName())) {
                    this.tasker.newSharedChain("userupdate:" + user.getName())
                            .async(() -> {
                                user.getSuffixes().add(suffixItem.getName());
                                user.save();
                            })
                            .execute();
                }
            });
        }

        bukkitMenu.setCancelInventoryClick(true);
        bukkitMenu.setDisposeWhenClose(true);

        return bukkitMenu;
    }


    public String getDetails(Player player, User user, SuffixItem suffixItem) {
        Optional<VaultHook> vaultHook = this.dreamHookManager.get(VaultHook.class);

        if (user.getSuffixes().contains(suffixItem.getName())) {
            return this.menuConfig.chooseClick;
        }

        if (suffixItem.getPrice() == -1) {
            return this.menuConfig.noPermission;
        }

        if (vaultHook.isPresent()) {
            double playerBalance = vaultHook.get().getVaultApi().getEconomy().getBalance(player);

            if (playerBalance >= suffixItem.getPrice()) {
                return this.menuConfig.buyClick;
            }
            return this.menuConfig.notEnoughMoney;
        }

        return this.menuConfig.noPermission;
    }
}