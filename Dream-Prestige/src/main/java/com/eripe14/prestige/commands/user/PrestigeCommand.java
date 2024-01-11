package com.eripe14.prestige.commands.user;

import com.eripe14.prestige.bukkit.TimeUtils;
import com.eripe14.prestige.commands.CommandUse;
import com.eripe14.prestige.features.user.User;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import panda.std.Option;

@FunnyComponent
public class PrestigeCommand extends CommandUse {

    @FunnyCommand(
            name = "${user.prestige.name}",
            description = "${user.prestige.description}",
            aliases = "${user.prestige.aliases}",
            permission = "dc.${user.prestige.name}",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void execute(Player player, String[] args) {
        User user = this.userManager.getOrCreate(player);

        Option<net.dzikoysk.funnyguilds.user.User> funnyUserOption = this.funnyGuilds.getUserManager( ).findByPlayer(player);

        if (funnyUserOption.isEmpty( )) return;

        net.dzikoysk.funnyguilds.user.User funnyUser = funnyUserOption.get( );

        if (funnyUser.getRank( ).getPoints( ) < this.prestigeConfig.requiredRanking) {
            this.sendMessage(player, this.messageConfig.tooLittleAmountOfRanking, new ImmutableMap.Builder<String, String>( )
                    .put("%AMOUNT%", String.valueOf(this.prestigeConfig.requiredRanking))
                    .build( ));
            return;
        }

        if (!user.isCanExecuteCommand( )) {
            long expireIn = TimeUtils.parseTime(this.prestigeConfig.expireIn);

            this.sendMessage(player, this.messageConfig.firstMessage);

            user.setCanExecuteCommand(true);
            user.setExecutePossibilityExpireIn(System.currentTimeMillis( ) + expireIn);
            user.save( );

            return;
        }

        funnyUser.getRank( ).setPoints(this.prestigeConfig.rankingAfterReceivingPrestige);

        user.addPrestige( );
        user.setExecutePossibilityExpireIn(0);
        user.setCanExecuteCommand(false);
        user.save( );

        this.sendMessage(player, this.messageConfig.gotNewPrestige, new ImmutableMap.Builder<String, String>( )
                .put("%PRESTIGE%", String.valueOf(user.getPrestige( )))
                .build( ));
    }

}
