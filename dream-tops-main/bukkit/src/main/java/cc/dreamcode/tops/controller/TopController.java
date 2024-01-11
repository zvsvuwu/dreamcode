package cc.dreamcode.tops.controller;

import cc.dreamcode.tops.user.User;
import cc.dreamcode.tops.user.UserManager;
import cc.dreamcode.tops.user.top.TopManager;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class TopController implements Listener {

    private @Inject UserManager userManager;
    private @Inject TopManager topManager;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        final Player player = e.getEntity();
        final User user = this.userManager.getUserByPlayer(player);

        final Player attacker = e.getEntity().getKiller();

        if (attacker != null) {
            final User attackerUser = this.userManager.getUserByPlayer(attacker);
            attackerUser.setKills(attackerUser.getKills() + 1);
        }

        user.setDeaths(user.getDeaths() + 1);
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e) {
        final Material mat = e.getItem().getType();
        final Player player = e.getPlayer();
        final User user = this.userManager.getUserByPlayer(player);


        if (mat == XMaterial.GOLDEN_APPLE.parseMaterial()) {
            user.setEatengapple(user.getEatengapple() + 1);
        }

        if (mat == XMaterial.ENCHANTED_GOLDEN_APPLE.parseMaterial()) {
            user.setEatenkoxapple(user.getEatenkoxapple() + 1);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final User user = this.userManager.getUserByPlayer(player);
        final Material block = e.getBlock().getType();

        if (block == XMaterial.DIAMOND_ORE.parseMaterial()) {
            user.setMinediamonds(user.getMinediamonds() + 1);
        }
        if(block == XMaterial.DIAMOND_BLOCK.parseMaterial()) {
            user.setMinediamonds(user.getMinediamonds() + 1);
        }
        if (block == XMaterial.IRON_ORE.parseMaterial()) {
            user.setMinediron(user.getMinediron() + 1);
        }
        if (block == XMaterial.IRON_BLOCK.parseMaterial()) {
            user.setMinediron(user.getMinediron() + 1);
        }
        if (block == XMaterial.EMERALD_ORE.parseMaterial()) {
            user.setMinedemeralds(user.getMinedemeralds() + 1);
        }
        if (block == XMaterial.EMERALD_BLOCK.parseMaterial()) {
            user.setMinedemeralds(user.getMinedemeralds() + 1);
        }
        if (block == XMaterial.GOLD_ORE.parseMaterial()) {
            user.setMinedgold(user.getMinedgold() + 1);
        }
        if (block == XMaterial.GOLD_BLOCK.parseMaterial()) {
            user.setMinedgold(user.getMinedgold() + 1);
        }
        if (block == XMaterial.LAPIS_ORE.parseMaterial()) {
            user.setMinedlapis(user.getMinedlapis() + 1);
        }
        if (block == XMaterial.LAPIS_BLOCK.parseMaterial()) {
            user.setMinedlapis(user.getMinedlapis() + 1);
        }

        user.setMinedblocks(user.getMinedblocks() + 1);
    }

}
