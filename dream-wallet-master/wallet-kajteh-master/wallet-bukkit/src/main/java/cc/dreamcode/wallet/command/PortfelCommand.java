package cc.dreamcode.wallet.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.wallet.WalletPlugin;
import cc.dreamcode.wallet.config.MessageConfig;
import cc.dreamcode.wallet.config.PluginConfig;
import cc.dreamcode.wallet.menu.BuyHistoryMenu;
import cc.dreamcode.wallet.menu.WalletMenu;
import cc.dreamcode.wallet.shared.MoneyUtil;
import cc.dreamcode.wallet.user.User;
import cc.dreamcode.wallet.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PortfelCommand extends BukkitCommand {

    private final WalletPlugin walletPlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final UserManager userManager;
    private final WalletMenu walletMenu;
    private final BuyHistoryMenu buyHistoryMenu;
    private final Tasker tasker;

    @Inject
    public PortfelCommand(WalletPlugin walletPlugin, PluginConfig pluginConfig, MessageConfig messageConfig, UserManager userManager, WalletMenu walletMenu, BuyHistoryMenu buyHistoryMenu, Tasker tasker) {
        super("portfel", "itemshop", "is", "wallet");
        this.walletPlugin = walletPlugin;
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
        this.userManager = userManager;
        this.walletMenu = walletMenu;
        this.buyHistoryMenu = buyHistoryMenu;
        this.tasker = tasker;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("add")) {

                if (!sender.hasPermission("dream.portfel.add")) {
                    this.messageConfig.noPermission.send(sender, new MapBuilder<String, Object>()
                            .put("permission", "dream.portfel.add")
                            .build());
                    return;
                }

                if (args.length != 3) {
                    this.messageConfig.invalidUsage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/portfel add [nick] [money]")
                            .build());
                    return;
                }

                final Optional<Double> optionalDouble = ParseUtil.parseDouble(args[2]);
                if (!optionalDouble.isPresent()) {
                    this.messageConfig.invalidNumberValue.send(sender, new MapBuilder<String, Object>()
                            .put("argument", args[1])
                            .put("index", 3)
                            .build());
                    return;
                }

                final double money = optionalDouble.get();
                if (args[1].equalsIgnoreCase("all")) {
                    this.walletPlugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> {
                        final User customer = this.userManager.getUserByPlayer(onlinePlayer);

                        this.tasker.newSharedChain("dbops:" + customer.getUniqueId())
                                .async(() -> {
                                    customer.addMoney(money);
                                    customer.save();
                                })
                                .execute();
                    });

                    this.messageConfig.moneyAddedToAll.send(sender, new MapBuilder<String, Object>()
                            .put("added", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                            .build());

                    this.messageConfig.moneyAddedToAllBroadcast.sendAll(new MapBuilder<String, Object>()
                            .put("nick", sender.getName())
                            .put("added", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                            .build());
                    return;
                }

                final Player customer = this.walletPlugin.getServer().getPlayerExact(args[1]);
                if (customer == null) {
                    this.messageConfig.noPlayer.send(sender);
                    return;
                }

                final User userCostumer = this.userManager.getUserByPlayer(customer);
                this.tasker.newSharedChain("dbops:" + customer.getUniqueId())
                        .async(() -> {
                            userCostumer.addMoney(money);
                            userCostumer.save();
                        })
                        .sync(() -> this.messageConfig.moneyAdded.send(sender, new MapBuilder<String, Object>()
                                .put("nick", customer.getName())
                                .put("added", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                                .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, userCostumer.getMoney()))
                                .build()))
                        .execute();
                return;
            }

            if(args[0].equalsIgnoreCase("balance")) {
                if (!sender.hasPermission("dream.portfel.balance")) {
                    this.messageConfig.noPermission.send(sender, new MapBuilder<String, Object>()
                            .put("permission", "dream.portfel.balance")
                            .build());
                    return;
                }

                if (args.length != 2) {
                    this.messageConfig.invalidUsage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/portfel balance [nick]")
                            .build());
                    return;
                }

                final Player customer = this.walletPlugin.getServer().getPlayerExact(args[1]);

                if (customer == null || !customer.isOnline()) {
                    this.messageConfig.noPlayer.send(sender);
                    return;
                }

                final User user = this.userManager.getUserByPlayer(customer);

                this.messageConfig.playerBalance.send(sender, new MapBuilder<String, Object>()
                        .put("nick", customer.getName())
                        .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney()))
                        .build());
                return;
            }

            if(args[0].equalsIgnoreCase("history")) {
                if (!sender.hasPermission("dream.portfel.history")) {
                    this.messageConfig.noPermission.send(sender, new MapBuilder<String, Object>()
                            .put("permission", "dream.portfel.history")
                            .build());
                    return;
                }

                if (!(sender instanceof Player)) {
                    this.messageConfig.invalidCommandSender.send(sender, new MapBuilder<String, Object>()
                            .put("sender", "Player")
                            .build());
                    return;
                }

                final Player player = (Player) sender;

                if (args.length != 2) {
                    this.messageConfig.invalidUsage.send(player, new MapBuilder<String, Object>()
                            .put("usage", "/portfel history [nick]")
                            .build());
                    return;
                }

                final Player customer = this.walletPlugin.getServer().getPlayerExact(args[1]);

                if (customer == null || !customer.isOnline()) {
                    this.messageConfig.noPlayer.send(player);
                    return;
                }

                final User user = this.userManager.getUserByPlayer(customer);

                if(user.getProductBuyList().isEmpty()) {
                    new BukkitNotice(MinecraftNoticeType.CHAT, "&cHistoria zakupów tego gracza jest pusta!").send(player);
                    return;
                }

                this.buyHistoryMenu.setUser(user);
                this.buyHistoryMenu.build().openPage(player);
                return;
            }

            if (args[0].equalsIgnoreCase("take")) {

                if (!sender.hasPermission("dream.portfel.take")) {
                    this.messageConfig.noPermission.send(sender, new MapBuilder<String, Object>()
                            .put("permission", "dream.portfel.take")
                            .build());
                    return;
                }

                if (args.length != 3) {
                    this.messageConfig.invalidUsage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/portfel take [nick] [money]")
                            .build());
                    return;
                }

                final Optional<Double> optionalDouble = ParseUtil.parseDouble(args[2]);
                if (!optionalDouble.isPresent()) {
                    this.messageConfig.invalidNumberValue.send(sender, new MapBuilder<String, Object>()
                            .put("argument", args[2])
                            .put("index", 3)
                            .build());
                    return;
                }

                final double money = optionalDouble.get();
                if (args[1].equalsIgnoreCase("all")) {
                    this.walletPlugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> {
                        final User customer = this.userManager.getUserByPlayer(onlinePlayer);

                        this.tasker.newSharedChain("dbops:" + customer.getUniqueId())
                                .async(() -> {
                                    customer.takeMoney(money);
                                    customer.save();
                                })
                                .execute();
                    });

                    this.messageConfig.moneyTakedFromAll.send(sender, new MapBuilder<String, Object>()
                            .put("taked", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                            .build());

                    this.messageConfig.moneyTakedFromAllBroadcast.sendAll(new MapBuilder<String, Object>()
                            .put("nick", sender.getName())
                            .put("taked", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                            .build());
                    return;
                }

                final Player customer = this.walletPlugin.getServer().getPlayerExact(args[1]);
                if (customer == null) {
                    this.messageConfig.noPlayer.send(sender);
                    return;
                }

                final User userCostumer = this.userManager.getUserByPlayer(customer);
                this.tasker.newSharedChain("dbops:" + customer.getUniqueId())
                        .async(() -> {
                            userCostumer.takeMoney(money);
                            userCostumer.save();
                        })
                        .sync(() -> this.messageConfig.moneyTaked.send(sender, new MapBuilder<String, Object>()
                                .put("nick", customer.getName())
                                .put("taked", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                                .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, userCostumer.getMoney()))
                                .build()))
                        .execute();
                return;
            }

            if(args[0].equalsIgnoreCase("reload")) {
                this.pluginConfig.load();
                this.messageConfig.load();
                new BukkitNotice(MinecraftNoticeType.CHAT, "&aPrzeładowano konfiguracje pluginu!").send(sender);
                return;
            }

            this.messageConfig.invalidUsage.send(sender, new MapBuilder<String, Object>()
                    .put("usage", "/portfel [add, take, reload, balance, history]")
                    .build());
            return;
        }

        if (!(sender instanceof Player)) {
            this.messageConfig.invalidCommandSender.send(sender, new MapBuilder<String, Object>()
                    .put("sender", "Player")
                    .build());
            return;
        }

        final Player player = (Player) sender;
        this.walletMenu.build(player).open(player);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        final ListBuilder<String> listBuilder = new ListBuilder<>();

        if (args.length == 3) {
            if (sender.hasPermission("dream.portfel.add")) {
                listBuilder.add("<money>");
            }

            if (sender.hasPermission("dream.portfel.take")) {
                listBuilder.add("<money>");
            }
        }

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("add") && sender.hasPermission("dream.portfel.add")) {
                listBuilder.add("all");
            }
            if (args[1].equalsIgnoreCase("take") && sender.hasPermission("dream.portfel.take")) {
                listBuilder.add("all");
            }
            this.walletPlugin.getServer().getOnlinePlayers().forEach(player ->
                    listBuilder.add(player.getName()));
        }

        if (args.length == 1) {
            if(sender.hasPermission("dream.portfel.reload")) {
                listBuilder.add("reload");
            }
            if (sender.hasPermission("dream.portfel.add")) {
                listBuilder.add("add");
            }

            if (sender.hasPermission("dream.portfel.take")) {
                listBuilder.add("take");
            }

            if(sender.hasPermission("dream.portfel.balance")) {
                listBuilder.add("balance");
            }
            if(sender.hasPermission("dream.portfel.history")) {
                listBuilder.add("history");
            }
        }

        return listBuilder.build();
    }
}
