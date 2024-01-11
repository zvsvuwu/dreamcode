package cc.dreamcode.fortune.fortune;

import cc.dreamcode.fortune.config.MessageConfig;
import cc.dreamcode.fortune.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FortuneManager {

    @Inject
    private MessageConfig messageConfig;

    @Inject
    private PluginConfig pluginConfig;

    /**
     * @return Full fortune material list
     */
    public List<Material> getFortuneMaterialList() {
        return this.pluginConfig.fortuneList.stream()
                .map(Fortune::getMaterial)
                .collect(Collectors.toList());
    }

    /**
     * @param material material that will be searched for fortune
     * @return Gets fortune level by material
     */
    public int getMaterialFortune(Material material) {
        for (Fortune fortune : this.pluginConfig.fortuneList) {
            if (fortune.getMaterial() == material) {
                return fortune.getLevel();
            }
        }
        return 0;
    }

    /**
     * @param material material that will be added to fortune list
     * @param level int that will be chosen to be fortune level
     * @param sender when block exist this is the player that will be informed
     */
    public void addMaterialWithFortune(Material material, int level, CommandSender sender) {
        for (Fortune fortune : this.pluginConfig.fortuneList) {
            if (fortune.getMaterial() == material) {
                this.messageConfig.materialAlreadyExist.send(sender);
                return;
            }
        }

        Fortune newFortune = new Fortune(material, level);
        this.pluginConfig.fortuneList.add(newFortune);
        this.pluginConfig.save();
    }

    /**
     * @param material material that will be removed
     */
    public void removeMaterial(Material material) {
        this.pluginConfig.fortuneList.removeIf(fortune -> fortune.getMaterial() == material);
        this.pluginConfig.save();
    }
}
