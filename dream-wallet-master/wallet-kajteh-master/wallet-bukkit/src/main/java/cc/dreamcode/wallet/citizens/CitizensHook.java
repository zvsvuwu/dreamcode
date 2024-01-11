package cc.dreamcode.wallet.citizens;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.wallet.config.PluginConfig;
import cc.dreamcode.wallet.top.TopManager;
import cc.dreamcode.wallet.top.TopType;
import cc.dreamcode.wallet.user.User;
import cc.dreamcode.wallet.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Scheduler(delay = 100L, interval = 100L)
public class CitizensHook extends BukkitRunnable {

    private final PluginConfig pluginConfig;
    private final TopManager topManager;
    private final DreamLogger dreamLogger;

    public void setSkin(int id, String player) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(id);
        if (npc == null) {
            if (pluginConfig.debug) {
                this.dreamLogger.debug("NPC with id " + id + " does not exists!");
            }
            return;
        }

        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);

        if (skinTrait == null) {
            return;
        }

        if (skinTrait.getSkinName() != null && skinTrait.getSkinName().equals(player)) {
            return;
        }

        skinTrait.setSkinName(player);
    }

    public void setSkins(TopType topType, List<NpcTop> list) {
        list.forEach(npcRank -> {
            Optional<User> userOptional = this.topManager.getTopUser(topType, npcRank.getTop());
            if (!userOptional.isPresent()) {
                setSkin(npcRank.getId(), this.pluginConfig.defaultPlayerNick);
                return;
            }

            final User user = userOptional.get();

            setSkin(npcRank.getId(), user.getName());
        });
    }

    @Override
    public void run() {
        if(this.pluginConfig.npcTopEnabled) {
            setSkins(TopType.MONEY, getNpcs(TopType.MONEY));
            setSkins(TopType.MONEY_SPENT, getNpcs(TopType.MONEY_SPENT));
        }
    }

    public List<NpcTop> getNpcs(TopType type) {
        return this.pluginConfig.npcTopSkins.stream().filter(npcTop -> npcTop.getType() == type).collect(Collectors.toList());
    }
}
