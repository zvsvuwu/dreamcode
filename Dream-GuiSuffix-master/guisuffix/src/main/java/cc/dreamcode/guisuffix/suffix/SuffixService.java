package cc.dreamcode.guisuffix.suffix;

import cc.dreamcode.guisuffix.hook.luckperms.LuckPermsHook;
import cc.dreamcode.guisuffix.suffix.menu.item.SuffixItem;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SuffixService {

    private final LuckPermsHook luckPermsHook;

    public void removeSuffix(@NonNull Player player) {
        User user = luckPermsHook.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);

        user.data().toCollection().forEach(node -> {
            if (node instanceof SuffixNode) {
                SuffixNode suffixNode = (SuffixNode) node;

                if (suffixNode.getPriority() >= 100) {
                    user.data().remove(suffixNode);
                }
            }
        });

        luckPermsHook.getLuckPerms().getUserManager().saveUser(user);
    }

    public boolean hasPurchased(cc.dreamcode.guisuffix.user.User user, SuffixItem suffixItem) {
        return user.getSuffixes().contains(suffixItem.getName());
    }

    public String getSuffix(@NonNull Player player) {
        User user = luckPermsHook.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);

        return user.getCachedData().getMetaData().getSuffix();
    }

    public boolean hasSuffix(@NonNull Player player) {
        User user = luckPermsHook.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        SuffixNode suffixNode = user.getCachedData().getMetaData().querySuffix().node();

        return suffixNode != null && suffixNode.getPriority() >= 100;
    }

    public void setSuffix(@NonNull Player player, @NonNull String suffix) {
        if (hasSuffix(player)) {
            removeSuffix(player);
        }

        User user = luckPermsHook.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        SuffixNode suffixNode = SuffixNode.builder(suffix, 100).build();

        user.data().add(suffixNode);
        luckPermsHook.getLuckPerms().getUserManager().saveUser(user);
    }
}
