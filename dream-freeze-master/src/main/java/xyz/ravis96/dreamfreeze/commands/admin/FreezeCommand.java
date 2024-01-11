package xyz.ravis96.dreamfreeze.commands.admin;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ravis96.dreamfreeze.commands.CommandUse;

@FunnyComponent
public class FreezeCommand extends CommandUse {

    @FunnyCommand(
            name = "${admin.freeze.name}",
            description = "${admin.freeze.description}",
            aliases = "${admin.freeze.aliases}",
            permission = "dc.${admin.freeze.name}",
            acceptsExceeded = true
    )
    public void execute() {
        if(this.pluginStorage.isFreeze()) {
            this.pluginStorage.setFreeze(false);
            this.pluginMain.getServer().getOnlinePlayers().forEach(po ->
                    this.sendMessage(po, this.messageConfig.freezeDisable));
        } else {
            this.pluginStorage.setFreeze(true);
            this.pluginMain.getServer().getOnlinePlayers().forEach(po ->
                    this.sendMessage(po, this.messageConfig.freezeEnable));
        }
    }
}
