package cc.dreamcode.enderchest.menu;

import cc.dreamcode.enderchest.config.MessageConfig;
import cc.dreamcode.enderchest.config.PluginConfig;
import cc.dreamcode.enderchest.user.User;
import cc.dreamcode.enderchest.user.UserManager;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnderchestMainMenu implements BukkitMenuPlayerSetup {

    private @Inject PluginConfig pluginConfig;
    private @Inject BukkitMenuProvider bukkitMenuProvider;
    private @Inject MessageConfig messageConfig;
    private @Inject UserManager userManager;
    private @Inject EnderchestMenu enderchestMenu;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        BukkitMenuBuilder bukkitMenuBuilder = this.pluginConfig.enderchestMainMenu;
        BukkitMenu bukkitMenu = bukkitMenuBuilder.buildWithItems();
        Optional<User> user = this.userManager.getUser(humanEntity.getUniqueId());

        this.pluginConfig.enderchestTypes.forEach(enderchestType -> {
            int slot = enderchestType.getSlot() - 1;
            List<String> lore = new ArrayList<>();
            this.pluginConfig.enderchestLore.forEach(s -> lore.add(ChatUtil.fixColor(
                    PlaceholderContext.of(CompiledMessage.of(s))
                            .with(
                                    new MapBuilder<String, Object>()
                                            .put("page", enderchestType.getSlot())
                                            .put("group", enderchestType.getGroup())
                                            .build()
                            )
                            .apply())
            ));
            ItemStack itemStack = new ItemBuilder(XMaterial.ENDER_CHEST.parseMaterial())
                    .setName(ChatUtil.fixColor(
                            PlaceholderContext.of(CompiledMessage.of(this.pluginConfig.enderchestName))
                                    .with(
                                            new MapBuilder<String, Object>()
                                                    .put("page", enderchestType.getSlot())
                                                    .put("group", enderchestType.getGroup())
                                                    .build()
                                    )
                                    .apply()))
                    .setLore(lore)
                    .toItemStack();
            bukkitMenu.setItem(slot, itemStack, event -> user.ifPresent(user1 -> {
                if (enderchestType.getPermission() != null && !humanEntity.hasPermission(enderchestType.getPermission())) {
                    this.messageConfig.enderchestPermission.send(humanEntity);
                    return;
                }
                this.enderchestMenu.build(humanEntity, user1, enderchestType.getSlot()).open(humanEntity);
            }));
        });
        return bukkitMenu;
    }

}
