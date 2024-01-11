package cc.dreamcode.firework.recipe;

import cc.dreamcode.firework.FireWorkPlugin;
import cc.dreamcode.firework.config.PluginConfig;
import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;


public class RecipeComponent {

    private @Inject PluginConfig pluginConfig;
    private @Inject FireWorkPlugin fireWorkPlugin;
    private @Inject DreamLogger dreamLogger;

    public void registerRecipe() {
        try {
            ShapedRecipe fireworkRecipe = new ShapedRecipe(
                    new NamespacedKey(this.fireWorkPlugin, "fajerwerka"),
                    ItemBuilder.of(this.pluginConfig.fireworkItem).fixColors().toItemStack()
            );

            fireworkRecipe.shape("123", "456", "789");

            this.pluginConfig.ingredients.forEach((key, value) -> {
                if (value.parseMaterial() == null) {
                    throw new BukkitPluginException("Firework item material must be valid!");
                }

                fireworkRecipe.setIngredient(key, value.parseMaterial());
            });

            Bukkit.addRecipe(fireworkRecipe);

            if (this.pluginConfig.debug) {
                this.dreamLogger.debug("Recipe has been registered successfully!");
            }
        } catch (Exception e) {
            this.dreamLogger.error("Failed while adding recipe, Aborting!", new BukkitPluginException());
        }
    }
}