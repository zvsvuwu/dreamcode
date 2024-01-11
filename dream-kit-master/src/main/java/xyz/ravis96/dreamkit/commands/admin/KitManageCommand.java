package xyz.ravis96.dreamkit.commands.admin;

import com.google.common.collect.ImmutableMap;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import xyz.ravis96.dreamkit.commands.CommandUse;
import xyz.ravis96.dreamkit.features.kit.Kit;
import xyz.ravis96.dreamkit.utils.optional.OptionalConsumer;

import java.util.Optional;

@FunnyComponent
public class KitManageCommand extends CommandUse {

    @FunnyCommand(
            name = "${admin.kitmanage.name}",
            description = "${admin.kitmanage.description}",
            aliases = "${admin.kitmanage.aliases}",
            permission = "dc.${admin.kitmanage.name}",
            completer = "kits:3",
            acceptsExceeded = true
    )
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            this.pluginStorage.setDisabledKits(!this.pluginStorage.isDisabledKits());
            this.sendMessage(sender, this.pluginStorage.isDisabledKits() ?
                    this.messageConfig.kitsEnabled : this.messageConfig.kitsDisabled);
            return;
        }

        final String kitName = args[0];
        final Optional<Kit> optionalKit = this.pluginConfig.getKitConfig().kitList
                .stream()
                .filter(kit -> kit.getName().equalsIgnoreCase(kitName))
                .findFirst();

        OptionalConsumer.of(optionalKit).ifPresentOrElse(kit -> {
            if(this.pluginStorage.getDisabledKit().contains(kit.getName())) {
                this.pluginStorage.getDisabledKit().remove(kit.getName());
                this.sendMessage(sender, this.messageConfig.kitDisabled, new ImmutableMap.Builder<String, String>()
                        .put("%NAME%", kit.getName()).build());
            } else {
                this.pluginStorage.getDisabledKit().add(kit.getName());
                this.sendMessage(sender, this.messageConfig.kitEnabled, new ImmutableMap.Builder<String, String>()
                        .put("%NAME%", kit.getName()).build());
            }
        }, () -> this.sendMessage(sender, this.messageConfig.kitNotFound));
    }
}
