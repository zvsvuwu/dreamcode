package cc.dreamcode.dropsmp.features.command;

import cc.dreamcode.dropsmp.PluginMain;
import cc.dreamcode.dropsmp.config.MessageConfig;
import cc.dreamcode.dropsmp.features.optional.CustomOptional;
import cc.dreamcode.dropsmp.features.user.persistence.User;
import cc.dreamcode.dropsmp.features.user.persistence.UserRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DropsmpCommand extends CommandUse {
    public DropsmpCommand() {
        super("dropsmp", Collections.emptyList());
    }

    private @Inject MessageConfig messageConfig;

    private @Inject UserRepository repository;

    private @Inject PluginMain pluginMain;

    @Override
    public void run(@NonNull CommandSender sender, @NonNull String[] args) {
        whenNot(sender.hasPermission("dropsmp." + this.getName()), this.messageConfig.noPermission);
        when(args.length < 3, this.messageConfig.usage, new ImmutableMap.Builder<String, Object>()
                .put("usage", "/dropsmp [gracz] [sila/ochrona/predkosc] [wartosc]")
                .build());
        CustomOptional.of(Optional.ofNullable(this.pluginMain.getServer().getPlayerExact(args[0]))).ifPresentOrElse(playerCustomer -> {
            final User user = repository.get(playerCustomer, false);
            double amount;
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                this.send(this.messageConfig.notNumber, sender);
                return;
            }
            if (args[1].equals("sila")) {
                user.setStrength(amount);
                this.send(this.messageConfig.susscusfulGiveStat, sender);
            } else if (args[1].equals("ochrona")) {
                user.setResistance(amount);
                this.send(this.messageConfig.susscusfulGiveStat, sender);
            } else if (args[1].equals("predkosc")) {
                user.setSpeed(amount);
                this.send(this.messageConfig.susscusfulGiveStat, sender);
                playerCustomer.setWalkSpeed((float) ((amount / 1000) * 2));
            }
        }, () -> this.send(this.messageConfig.noPlayer, sender));
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (!player.hasPermission("dropsmp." + this.getName())) {
            return null;
        }
        if (args.length == 3) {
            return Collections.singletonList("[wartosc]");
        }
        if (args.length == 2) {
            return Arrays.asList("sila", "predkosc", "ochrona");
        }
        if (args.length == 1) {
            return this.pluginMain.getServer().getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
