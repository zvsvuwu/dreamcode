package cc.dreamcode.fortune.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.fortune.config.MessageConfig;
import cc.dreamcode.fortune.fortune.FortuneManager;
import cc.dreamcode.utilities.builder.MapBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FortuneCommand extends BukkitCommand {

    @Inject
    private MessageConfig messageConfig;

    @Inject
    private FortuneManager fortuneManager;

    public FortuneCommand() {
        super("fortune");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length < 1) {
            this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                    .put("usage", "/fortune (add/remove) (material) (fortune level)")
                    .build());
            return;
        }

        String type = args[0];

        if (type.equalsIgnoreCase("add") && args.length >= 3) {
            Optional<XMaterial> material = XMaterial.matchXMaterial(args[1]);

            if (!material.isPresent()) {
                this.messageConfig.invalidMaterial.send(sender);
                return;
            }

            if(this.fortuneManager.getFortuneMaterialList().contains(material.get().parseMaterial())) {
                this.messageConfig.materialAlreadyExist.send(sender);
                return;
            }

            try {
                int fortuneLevel = Integer.parseInt(args[2]);
                this.fortuneManager.addMaterialWithFortune(material.get().parseMaterial(), fortuneLevel, sender);

                this.messageConfig.materialSuccessfulyAdded.send(sender, new MapBuilder<String, Object>()
                        .put("material", material.get())
                        .put("fortune", fortuneLevel)
                        .build());
            } catch (NumberFormatException e) {
                this.messageConfig.notNumber.send(sender);
            }
        } else if (type.equalsIgnoreCase("remove") && args.length >= 2) {
            Optional<XMaterial> material = XMaterial.matchXMaterial(args[1]);

            if (!material.isPresent()) {
                this.messageConfig.invalidMaterial.send(sender);
                return;
            }

            Material mat = material.get().parseMaterial();
            if (!this.fortuneManager.getFortuneMaterialList().contains(mat)) {
                this.messageConfig.materialCannotRemove.send(sender, new MapBuilder<String, Object>()
                        .put("material", mat)
                        .build());
                return;
            }

            fortuneManager.removeMaterial(mat);
            this.messageConfig.materialSuccessfulyRemoved.send(sender, new MapBuilder<String, Object>()
                    .put("material", mat)
                    .build());

        } else if (type.equalsIgnoreCase("list")) {
            List<Material> materialList = fortuneManager.getFortuneMaterialList();
            if (materialList.isEmpty()) {
                this.messageConfig.noFortuneMaterials.send(sender);
            } else {
                List<String> fortuneList = new ArrayList<>();
                for (int i = 0; i < materialList.size(); i++) {
                    Material mat = materialList.get(i);
                    int level = fortuneManager.getMaterialFortune(mat);
                    String entry = (i + 1) + ". " + mat + " - " + level;
                    fortuneList.add(entry);
                }

                this.messageConfig.fortuneMaterialList.send(sender,new MapBuilder<String, Object>()
                        .put("fortuneList", fortuneList.toString())
                        .build());
            }
        } else {
            this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                    .put("usage", "/fortune (add/remove) (material) (fortune level)")
                    .build());

        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("add");
            completions.add("remove");
            completions.add("list");
            return completions;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            List<String> completions = new ArrayList<>();
            List<Material> materialList = fortuneManager.getFortuneMaterialList();
            for (Material material : materialList) {
                completions.add(material.name());
            }
            return completions;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
            List<String> materials = new ArrayList<>();
            for (Material material : Material.values()) {
                materials.add(material.name().toLowerCase());
            }
            return materials;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        }
        return null;
    }
}
