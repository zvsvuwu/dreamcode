package xyz.ravis96.dreamkit.commands.binds;

import lombok.RequiredArgsConstructor;
import net.dzikoysk.funnycommands.resources.Bind;
import net.dzikoysk.funnycommands.resources.Context;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.OfflinePlayer;
import org.panda_lang.utilities.inject.Resources;
import xyz.ravis96.dreamkit.features.user.User;
import xyz.ravis96.dreamkit.features.user.UserManager;

@FunnyComponent
@RequiredArgsConstructor
public final class UserBind implements Bind {

    private final UserManager userManager;

    @Override
    public void accept(Resources resources) {
        resources.on(User.class).assignHandler((property, annotation, args) -> this.fetchUser((Context) args[1]));
    }

    private User fetchUser(Context context) {
        if(!(context.getCommandSender() instanceof OfflinePlayer)) {
            return null;
        }

        final OfflinePlayer offlinePlayer = (OfflinePlayer) context.getCommandSender();
        return this.userManager.getOrCreate(offlinePlayer);
    }
}
