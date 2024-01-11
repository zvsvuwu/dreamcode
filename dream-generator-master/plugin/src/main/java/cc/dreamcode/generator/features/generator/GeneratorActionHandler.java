package cc.dreamcode.generator.features.generator;

import cc.dreamcode.generator.PluginMain;
import cc.dreamcode.generator.builder.ItemReplacer;
import cc.dreamcode.generator.config.MessageConfig;
import cc.dreamcode.generator.config.PluginConfig;
import cc.dreamcode.generator.config.subconfig.GeneratorsConfig;
import cc.dreamcode.generator.features.generator.persistence.Generator;
import cc.dreamcode.generator.features.generator.persistence.GeneratorRepository;
import cc.dreamcode.generator.features.generator.template.GeneratorTemplate;
import cc.dreamcode.generator.features.notice.NoticeService;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class GeneratorActionHandler implements Listener, NoticeService {

    private @Inject PluginMain pluginMain;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject GeneratorRepository generatorRepository;
    
    @EventHandler
    public void onGeneratorPlace(BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        final Block block = e.getBlockPlaced();

        if (this.generatorRepository.contains(block.getLocation())) {
            this.send(this.messageConfig.generatorIsAlreadyPlaceHere, player);
            e.setCancelled(true);
            return;
        }

        final ItemStack itemInHand = e.getItemInHand();
        final GeneratorsConfig generatorsConfig = this.pluginConfig.generatorsConfig;

        generatorsConfig.generatorList
                .stream()
                .filter(generatorTemplate -> new ItemReplacer(generatorTemplate.getItemStack()).fixColors()
                        .isSimilar(itemInHand))
                .findAny()
                .ifPresent(generatorTemplate -> {
                    this.generatorRepository.createByTemplate(generatorTemplate, block.getLocation());
                    block.setType(Material.STONE);
                    this.send(this.messageConfig.successfulCreateGenerator, player);
                });
    }

    @EventHandler
    public void generatorBreakEvent(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final Block block = e.getBlock();

        this.generatorRepository.getByKey(block.getLocation()).ifPresent(generator -> {
            if (player.getItemInHand().getType().equals(XMaterial.GOLDEN_PICKAXE.parseMaterial())) {
                this.generatorRepository.remove(block.getLocation());
                this.send(this.messageConfig.successfulDeleteGenerator, player);
                return;
            }
            this.pluginMain.getServer().getScheduler().runTaskLater(
                    this.pluginMain,
                    () -> block.setType(Material.STONE),
                    generator.getRegenerationSpeed() * 20L
            );
        });
    }
}
