package cc.dreamcode.leaderboard.menus;

import cc.dreamcode.leaderboard.LeaderboardPlugin;
import cc.dreamcode.leaderboard.LeaderboardService;
import cc.dreamcode.leaderboard.LeaderboardType;
import cc.dreamcode.leaderboard.config.PluginConfig;
import cc.dreamcode.leaderboard.config.leaderboard.LeaderboardConfig;
import cc.dreamcode.leaderboard.exception.PluginRuntimeException;
import cc.dreamcode.leaderboard.model.FutureImpl;
import cc.dreamcode.leaderboard.model.user.User;
import cc.dreamcode.leaderboard.model.user.UserRepository;
import cc.dreamcode.leaderboard.optional.CustomOptional;
import cc.dreamcode.leaderboard.utilities.TimeUtil;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LeaderboardMenu implements BukkitMenuPlayerSetup, FutureImpl {

    private @Inject LeaderboardPlugin leaderboardPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject LeaderboardService leaderboardService;
    private @Inject UserRepository userRepository;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final LeaderboardConfig leaderboardConfig = this.pluginConfig.leaderboardConfig;
        final BukkitMenuBuilder bukkitMenuBuilder = leaderboardConfig.menuBuilder;
        final BukkitMenu bukkitMenu = bukkitMenuBuilder.build();

        bukkitMenuBuilder.getItems().forEach((integer, itemStack) -> {
            final ItemStack copy = new ItemStack(itemStack);
            final ItemMeta itemMeta = copy.getItemMeta();

            if (itemMeta.hasLore()) {
                final List<String> list = itemMeta.getLore();
                final AtomicInteger pos = new AtomicInteger(0);

                list.forEach(text -> {
                    final CompiledMessage compiledMessage = CompiledMessage.of(text);
                    final PlaceholderContext context = PlaceholderContext.of(compiledMessage);

                    compiledMessage.getUsedFields().forEach(usedField -> {
                        final String[] split = usedField.split("_");

                        if (split.length == 3) {
                            final String id = split[0];
                            final LeaderboardType leaderboardType = Arrays.stream(LeaderboardType.values())
                                    .filter(type -> type.getId().equalsIgnoreCase(id))
                                    .findFirst()
                                    .orElseThrow(() -> new PluginRuntimeException("Leaderboard type not found. (" + id + ")"));

                            final long position;
                            try {
                                position = Long.parseLong(split[1]);
                            }
                            catch (NumberFormatException e) {
                                throw new PluginRuntimeException("Position must be number.");
                            }

                            final LeaderboardService.Top top = this.leaderboardService.getLeaderboardMap().get(leaderboardType)
                                    .getOrDefault(position, new LeaderboardService.Top(leaderboardConfig.unknownUserName, 0));

                            final String value = split[2];
                            final String returnValue;
                            if (value.equalsIgnoreCase("nick")) {
                                returnValue = top.getUsername();
                            }
                            else if (value.equalsIgnoreCase("count")) {
                                if (leaderboardType.equals(LeaderboardType.WALK_DISTANCE_COUNT)) {
                                    double kilometers = top.getCount() * 0.001;
                                    returnValue = new DecimalFormat("0.00").format(kilometers) + "km";
                                }
                                else if (leaderboardType.equals(LeaderboardType.PLAY_TIME_COUNT)) {
                                    returnValue = TimeUtil.convertSeconds(top.getCount());
                                }
                                else {
                                    returnValue = String.valueOf(top.getCount());
                                }
                            }
                            else {
                                throw new PluginRuntimeException("Value must be nick or count. (" + value + ")");
                            }

                            context.with(usedField, returnValue);
                            return;
                        }

                        if (split.length == 2) {
                            Arrays.stream(LeaderboardType.values())
                                    .filter(type -> split[0].equalsIgnoreCase(type.getId()) &&
                                            split[1].equalsIgnoreCase("position"))
                                    .findFirst()
                                    .ifPresent(leaderboardType -> {
                                        if (!(humanEntity instanceof Player)) {
                                            return;
                                        }

                                        final Player player = (Player) humanEntity;
                                        context.with(usedField, this.leaderboardService.getPosition(leaderboardType, player.getName()));
                                    });
                        }

                        Arrays.stream(LeaderboardType.values())
                                .filter(type -> type.getId().equalsIgnoreCase(usedField))
                                .findFirst()
                                .ifPresent(leaderboardType -> {
                                    if (!(humanEntity instanceof Player)) {
                                        return;
                                    }

                                    final Player player = (Player) humanEntity;
                                    final User user = this.userRepository.findOrCreateByPlayer(player).join();

                                    final String returnValue;
                                    if (leaderboardType.equals(LeaderboardType.WALK_DISTANCE_COUNT)) {
                                        double kilometers = leaderboardType.getFunction().applyAsLong(user) * 0.001;
                                        returnValue = new DecimalFormat("0.00").format(kilometers) + "km";
                                    }
                                    else if (leaderboardType.equals(LeaderboardType.PLAY_TIME_COUNT)) {
                                        returnValue = TimeUtil.convertSeconds(leaderboardType.getFunction().applyAsLong(user));
                                    }
                                    else {
                                        returnValue = String.valueOf(leaderboardType.getFunction().applyAsLong(user));
                                    }

                                    context.with(usedField, returnValue);
                                });
                    });

                    list.set(pos.getAndIncrement(), context.apply());
                });

                itemMeta.setLore(list);
                copy.setItemMeta(itemMeta);
            }

            CustomOptional.ofNullable(leaderboardConfig.topTypeSlots.get(integer)).ifPresentOrElse(leaderboardType ->
                    bukkitMenu.setItem(integer, copy, e -> future(() -> {
                        LeaderboardTopMenu leaderboardTopMenu = this.leaderboardPlugin.createInstance(LeaderboardTopMenu.class);
                        leaderboardTopMenu.setLeaderboardType(leaderboardType);
                        return leaderboardTopMenu.build();
                    }).join().openFirstPage(e.getWhoClicked())),
                    () -> bukkitMenu.setItem(integer, copy));
        });

        return bukkitMenu;
    }
}
