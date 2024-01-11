package cc.dreamcode.zonebox.command;

import cc.dreamcode.zonebox.config.MessageConfig;
import cc.dreamcode.zonebox.config.PluginConfig;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BlockCommand extends BukkitCommand {

    private @Inject
    MessageConfig messageConfig;
    private @Inject
    PluginConfig pluginConfig;

    public BlockCommand() {
        super("zonebox");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final long time = System.currentTimeMillis();
        if(args.length != 0) {
            if (args[0].equals("reload")) {
                reloadConfig(sender, time, false);
                return;
            }
            if (args[0].equals("add")) {
                if(args[1].isEmpty()) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/zonebox add <Material>")
                            .build());
                }
                Optional<XMaterial> material = XMaterial.matchXMaterial(args[1]);

                if (material.isPresent()) {
                    if(this.pluginConfig.allowedBlocks.contains(material.get())) {
                        this.messageConfig.blockAlreadyAdded.send(sender);
                        return;
                    }
                    this.pluginConfig.allowedBlocks.add(material.get());
                    this.messageConfig.blockSuccessfullyAdded.send(sender, new MapBuilder<String, Object>()
                            .put("block", material.get())
                            .build());
                    reloadConfig(sender, time, true);
                    return;
                } else {
                    this.messageConfig.blockDoesNotExist.send(sender);
                }
            }
            if (args[0].equals("remove")) {
                if(args[1].isEmpty()) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/zonebox remove <Material>")
                            .build());
                }
                Optional<XMaterial> material = XMaterial.matchXMaterial(args[1]);

                if (material.isPresent()) {
                    if(!this.pluginConfig.allowedBlocks.contains(material.get())) {
                        this.messageConfig.blockNotAdded.send(sender);
                        return;
                    }
                    this.pluginConfig.allowedBlocks.remove(material.get());
                    this.messageConfig.blockSuccessfullyRemoved.send(sender, new MapBuilder<String, Object>()
                            .put("block", material.get())
                            .build());
                    reloadConfig(sender, time, true);
                    return;
                } else {
                    this.messageConfig.blockDoesNotExist.send(sender);
                }
            }
            if(args[0].equals("list")) {
                this.messageConfig.addedBlocksList.send(sender, new MapBuilder<String, Object>()
                        .put("list", this.pluginConfig.allowedBlocks.toString())
                        .build());
                return;
            }
            this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                    .put("usage", "/zonebox reload - add/remove <Material>")
                    .build());
        }

    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(Arrays.asList("reload", "add", "remove", "list"));
        }
        if (args.length == 2) {
            List<String> materials = new ArrayList<>();
            for (Material material : Material.values()) {
                materials.add(material.name().toLowerCase());
            }
            return materials;
        }
        return null;
    }

    public void reloadConfig(CommandSender sender, long time, boolean shouldSave) {
        try {
            if (shouldSave) {
                this.pluginConfig.save();
            }
            this.messageConfig.load();
            this.pluginConfig.load();

            this.messageConfig.reloaded.send(sender, new MapBuilder<String, Object>()
                    .put("time", TimeUtil.convertMills(System.currentTimeMillis() - time))
                    .build());
        }
        catch (NullPointerException | OkaeriException e) {
            e.printStackTrace();

            this.messageConfig.reloadError.send(sender, new MapBuilder<String, Object>()
                    .put("error", e.getMessage())
                    .build());
        }
    }

}
