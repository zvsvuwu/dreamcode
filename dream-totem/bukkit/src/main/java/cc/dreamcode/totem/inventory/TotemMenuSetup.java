package cc.dreamcode.totem.inventory;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.totem.TotemEffect;
import cc.dreamcode.totem.config.MessageConfig;
import cc.dreamcode.totem.config.PluginConfig;
import cc.dreamcode.totem.user.UserRepository;
import cc.dreamcode.totem.vault.VaultApi;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ItemUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TotemMenuSetup {
    @Inject private Tasker tasker;
    @Inject private VaultApi vaultApi;
    @Inject private PluginConfig pluginConfig;
    @Inject private MessageConfig messageConfig;
    @Inject private UserRepository userRepository;
    @Inject private TotemMenuHolder totemMenuHolder;


    @NonNull
    public BukkitMenu setup() {
        final BukkitMenuBuilder bukkitMenuBuilder = this.pluginConfig.totemMenu;
        final BukkitMenu bukkitMenu = bukkitMenuBuilder.buildEmpty();

        if(pluginConfig.fillInventory){
            ItemStack fillItem = new ItemBuilder(pluginConfig.fillMenuItem).fixColors().toItemStack();
            for(int i = 0; i < bukkitMenuBuilder.getRows()*9; i++){
                bukkitMenu.setItem(i, fillItem);
            }
        }

        bukkitMenuBuilder.getItems().forEach((integer, itemStack) ->
                bukkitMenu.setItem(integer, new ItemBuilder(itemStack).fixColors().toItemStack(), e -> e.setCancelled(true)));

        for(TotemEffect totemEffect : this.pluginConfig.effects.values()){
            ItemStack totemItem = new ItemBuilder(totemEffect.getMaterial())
                    .setName(totemEffect.getDisplayName())
                    .setLore(totemEffect.getLore())
                    .fixColors(new MapBuilder<String, Object>()
                            .put("price", totemEffect.getPrice())
                            .build())
                    .toItemStack();

            bukkitMenu.setItem(totemEffect.getSlot(), totemItem, event -> {
                Player player = (Player) event.getWhoClicked();

                if(!hasItems(player, totemEffect.getCost())){
                    messageConfig.missingRequiredItems.send(player);
                    return;
                }

                if(!vaultApi.hasMoney(player, totemEffect.getPrice())){
                    messageConfig.noEnoughMoney.send(player, new MapBuilder<String, Object>()
                            .put("price", totemEffect.getPrice())
                            .put("balance", (int) vaultApi.getBalance(player))
                            .build());
                    return;
                }

                this.tasker.newSharedChain(player.getUniqueId().toString())
                        .async(() -> this.userRepository.findOrCreateByHumanEntity(player))
                        .acceptSync(user -> {
                            if(user.getTotemEffect() != null && totemEffect.getSlot() == user.getTotemEffect().getSlot()) {
                                messageConfig.alreadyPurchased.send(player, new MapBuilder<String, Object>()
                                        .put("effect_name", totemEffect.getDisplayName())
                                        .build());
                                return;
                            }

                            removeItems(player, totemEffect.getCost());
                            vaultApi.removeMoney(player, totemEffect.getPrice());
                            user.setTotemEffect(totemEffect);
                            messageConfig.purchasedTotemEffect.send(player, new MapBuilder<String, Object>()
                                    .put("totem_effect", totemEffect.getDisplayName())
                                    .build());
                        })
                        .acceptAsync(user -> {
                            user.save();
                        })
                        .execute();
            });
        }
        return bukkitMenu;
    }


    private void removeItems(Player player, List<ItemStack> items){
        for(ItemStack itemStack : items){
            removeItem(player, itemStack.getType(), itemStack.getAmount());
        }
    }

    private void removeItem(Player player, Material material, int amount) {
        Inventory inventory = player.getInventory();
        int remainingAmount = amount;

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null || itemStack.getType() != material) continue;

            if (itemStack.getAmount() <= remainingAmount) {
                remainingAmount -= itemStack.getAmount();
                inventory.removeItem(itemStack);
            } else {
                itemStack.setAmount(itemStack.getAmount() - remainingAmount);
                break;
            }
        }
    }

    private boolean hasItems(Player player, List<ItemStack> items) {
        for(ItemStack itemStack : items){
            int required = itemStack.getAmount();
            int count = getCountItems(player, itemStack.getType());
            if(required > count) return false;
        }

        return true;
    }

    private int getCountItems(Player player, Material material){
        int x = 0;

        for(ItemStack item : player.getInventory().getContents()) {
            if(item == null) continue;
            if(item.getType() != material) continue;
            x+=item.getAmount();
        }

        return x;
    }
}
