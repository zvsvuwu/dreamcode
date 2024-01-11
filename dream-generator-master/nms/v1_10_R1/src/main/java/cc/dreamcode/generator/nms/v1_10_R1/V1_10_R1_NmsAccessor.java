package cc.dreamcode.generator.nms.v1_10_R1;

import cc.dreamcode.generator.nms.api.NmsAccessor;
import cc.dreamcode.generator.nms.api.recipe.RecipeAccessor;
import cc.dreamcode.generator.nms.v1_10_R1.recipe.V1_10_R1_RecipeAccessor;

public class V1_10_R1_NmsAccessor implements NmsAccessor {
    @Override
    public RecipeAccessor getRecipeAccessor() {
        return new V1_10_R1_RecipeAccessor();
    }
}
