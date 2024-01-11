package cc.dreamcode.generator.features.generator.template;

import cc.dreamcode.generator.PluginMain;
import cc.dreamcode.generator.builder.ItemReplacer;
import cc.dreamcode.generator.nms.api.NmsAccessor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import java.util.Map;

@Getter
public class GeneratorTemplate {

    private final ItemStack itemStack;
    private final int regenerationSpeed;
    private final String shapedString;

    private final Map<Character, Material> shaped;

    public GeneratorTemplate(ItemStack itemStack, int r, String shapedString, Map<Character, Material> shaped) {
        this.itemStack = itemStack;
        this.regenerationSpeed = r;
        this.shapedString = shapedString;
        this.shaped = shaped;
        register();
    }

    private final NmsAccessor nmsAccessor = PluginMain.getPluginMain().getNmsAccessor();

    public void register() {
        ShapedRecipe shapedRecipe = nmsAccessor.getRecipeAccessor().getShapedRecipe(new ItemReplacer(this.itemStack).fixColors(), PluginMain.getPluginMain());
        String[] s = shapedString.split(",");
        shapedRecipe.shape(s[0], s[1], s[2]);
        this.shaped.forEach(shapedRecipe::setIngredient);
        Bukkit.addRecipe(shapedRecipe);
    }
}
