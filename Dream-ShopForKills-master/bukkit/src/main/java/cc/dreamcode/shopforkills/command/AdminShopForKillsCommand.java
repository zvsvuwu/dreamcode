package cc.dreamcode.shopforkills.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.shopforkills.config.MessageConfig;
import cc.dreamcode.shopforkills.config.PluginConfig;
import cc.dreamcode.shopforkills.offer.Offer;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import com.google.common.collect.Lists;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

@RequiredPermission
public class AdminShopForKillsCommand extends BukkitCommand {

    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;

    public AdminShopForKillsCommand() {
        super("adminshopkill");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length != 3) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>().put("usage", "/adminshopkill <add> <slot> <kille>").build());
                    return;
                }
                ItemStack itemStack = player.getItemInHand();
                if (itemStack == null || itemStack.getType().equals(Material.AIR)){
                    this.messageConfig.notItem.send(sender);
                    return;
                }

                Optional<Integer> slotOptional = ParseUtil.parseInteger(args[1]);
                Optional<Integer> killsOptional = ParseUtil.parseInteger(args[2]);

                if (!slotOptional.isPresent() || !killsOptional.isPresent()) {
                    this.messageConfig.notNumber.send(player);
                    return;
                }
                int slot = slotOptional.get();
                int kills = killsOptional.get();

                Offer offer = this.pluginConfig.offers.stream().filter(offer1 -> offer1.getSlot() == slot).findAny().orElse(null);
                if (offer != null) {
                    this.messageConfig.offerNotNull.send(sender);
                    return;
                }

                this.pluginConfig.offers.add(new Offer(itemStack, slot, kills));
                this.pluginConfig.save();
                this.messageConfig.itemAdd.send(sender);
                return;
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                Optional<Integer> slotOptional = ParseUtil.parseInteger(args[1]);
                if (!slotOptional.isPresent()) {
                    this.messageConfig.notNumber.send(player);
                    return;
                }
                int slot = slotOptional.get();

                Offer offer = this.pluginConfig.offers.stream().filter(offer1 -> offer1.getSlot() == slot).findAny().orElse(null);
                if (offer == null) {
                    this.messageConfig.offerNull.send(sender);
                    return;
                }

                this.pluginConfig.offers.remove(offer);
                this.pluginConfig.save();
                this.messageConfig.offerDelete.send(sender);
                return;
            }
        }

        if (args[0].equalsIgnoreCase("reload")) {
            final long time = System.currentTimeMillis();

            try {
                this.messageConfig.load();
                this.pluginConfig.load();

                this.messageConfig.reloaded.send(sender, new MapBuilder<String, Object>()
                        .put("time", TimeUtil.convertMills(System.currentTimeMillis() - time))
                        .build());
            }
            catch (NullPointerException | OkaeriException e) {
                e.printStackTrace();

                this.messageConfig.reloadError.send(sender, new MapBuilder<String, Object>()
                        .put("error", e.getMessage())
                        .build());
            }
            return;
        }

        this.messageConfig.usage.send(sender, new MapBuilder<String, Object>().put("usage", "/adminshopkill <reload/add/delete> [<slot> <kille>]").build());
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList("add", "delete", "reload");
        }
        return null;
    }
}
