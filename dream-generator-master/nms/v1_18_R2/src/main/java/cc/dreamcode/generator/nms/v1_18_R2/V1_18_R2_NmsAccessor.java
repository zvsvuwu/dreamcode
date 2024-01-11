package cc.dreamcode.generator.nms.v1_18_R2;

import cc.dreamcode.generator.nms.api.NmsAccessor;
import cc.dreamcode.generator.nms.api.recipe.RecipeAccessor;
import cc.dreamcode.generator.nms.v1_18_R2.recipe.V1_18_R2_RecipeAccessor;

public class V1_18_R2_NmsAccessor implements NmsAccessor {
    @Override
    public RecipeAccessor getRecipeAccessor() {
        return new V1_18_R2_RecipeAccessor();
    }
}
