package cc.dreamcode.generator.nms.v1_16_R3;

import cc.dreamcode.generator.nms.api.NmsAccessor;
import cc.dreamcode.generator.nms.api.recipe.RecipeAccessor;
import cc.dreamcode.generator.nms.v1_16_R3.recipe.V1_16_R3_RecipeAccessor;

public class V1_16_R3_NmsAccessor implements NmsAccessor {
    @Override
    public RecipeAccessor getRecipeAccessor() {
        return new V1_16_R3_RecipeAccessor();
    }
}
