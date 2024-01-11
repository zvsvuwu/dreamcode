package cc.dreamcode.customitems.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.customitems.config.MessageConfig;
import cc.dreamcode.customitems.config.PluginConfig;
import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ItemUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dream-customitems.command")
public class CustomItemsCommand extends BukkitCommand {

    private final PluginConfig config;
    private final MessageConfig messageConfig;

    @Inject
    public CustomItemsCommand(final PluginConfig config, final MessageConfig messageConfig) {
        super("customitems", "customitem");

        this.config = config;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if(args.length == 0 && sender instanceof Player) {
            Player player = (Player) sender;
            Inventory inventory = Bukkit.createInventory(null, 9, "Customowe przedmioty:");

            List<ItemStack> inventoryItems = Arrays.asList(
                    config.buildSpiderGrenade(),
                    config.buildBaseballBat(),
                    config.buildDamagePlate(),
                    config.buildCrossbow(),
                    config.buildBone(),
                    config.buildBow(),
                    config.buildTotem(),
                    config.buildSnowball()
            );

            for (ItemStack itemStack : inventoryItems) {
                inventory.addItem(itemStack);
            }

            player.openInventory(inventory);
        } else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            try {
                this.config.load();
                this.messageConfig.load();
            } catch (OkaeriException | NullPointerException e) {
                e.printStackTrace();
            }
            this.messageConfig.reload.send(sender);
        } else if(args.length > 1) {
            if ("give".equalsIgnoreCase(args[0])) {
                if (args.length < 4) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>().put("usage", "/customitems give [nick] [pajeczy_granat/kij_bejsbolowy/plyta_obrazen/kusza_przyciagania/kosc_ogluszenia/luk_podrzucenia/totem_ekwipunku] [ilość]").build());
                    return;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                ItemStack itemStack = this.getItemByName(args[2], Integer.parseInt(args[3]));

                if (target == null || !target.isOnline()) {
                    this.messageConfig.offlinePlayer.send(sender);
                    return;
                }

                if (itemStack == null) return;

                ItemUtil.addItem(itemStack, target.getInventory());
                new BukkitNotice(MinecraftNoticeType.CHAT, "&aDodano graczowi customowy przedmiot!");
            }
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if(sender.hasPermission("dream-customitems.command")) {
            if (args.length == 3){
                return Arrays.asList("pajeczy_granat", "kij_bejsbolowy", "plyta_obrazen", "kusza_przyciagania", "kosc_ogluszenia", "luk_podrzucenia", "totem_ekwipunku", "sniezka_teleportacji");
            } else if(args.length == 4) {
                List<String> complete = new ArrayList<>();
                for(int i = 1; i < 99; i++) {
                    complete.add(String.valueOf(i));
                }
                return complete;
            } else if(args.length == 1) {
                return Arrays.asList("reload", "give");
            } else if(args.length == 2) {
                List<String> complete = new ArrayList<>();
                for(Player player : Bukkit.getOnlinePlayers()) {
                    complete.add(player.getName());
                }
                return complete;
            }
        }
        return null;
    }

    private ItemStack getItemByName(String itemName, int amount) {
        switch (itemName) {
            case "pajeczy_granat":
                return ItemBuilder.of(config.spiderGrenade).setAmount(amount).fixColors().toItemStack();
            case "kij_bejsbolowy":
                return ItemBuilder.of(config.baseballBat).setAmount(amount).fixColors().toItemStack();
            case "plyta_obrazen":
                return ItemBuilder.of(config.damagePlate).setAmount(amount).fixColors().toItemStack();
            case "kusza_przyciagania":
                return ItemBuilder.of(config.crossbow).setAmount(amount).fixColors().toItemStack();
            case "kosc_ogluszenia":
                return ItemBuilder.of(config.bone).setAmount(amount).fixColors().toItemStack();
            case "luk_podrzucenia":
                return ItemBuilder.of(config.bow).setAmount(amount).fixColors().toItemStack();
            case "totem_ekwipunku":
                return ItemBuilder.of(config.totem).setAmount(amount).fixColors().toItemStack();
            case "sniezka_teleportacji":
                return ItemBuilder.of(config.snowball).setAmount(amount).fixColors().toItemStack();
            default:
                return null;
        }
    }
}
