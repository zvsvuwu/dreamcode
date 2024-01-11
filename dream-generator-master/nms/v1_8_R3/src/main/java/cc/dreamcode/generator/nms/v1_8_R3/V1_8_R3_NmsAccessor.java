package cc.dreamcode.generator.nms.v1_8_R3;

import cc.dreamcode.generator.nms.api.NmsAccessor;
import cc.dreamcode.generator.nms.api.recipe.RecipeAccessor;
import cc.dreamcode.generator.nms.v1_8_R3.recipe.V1_8_R3_RecipeAccessor;

public class V1_8_R3_NmsAccessor implements NmsAccessor {
    @Override
    public RecipeAccessor getRecipeAccessor() {
        return new V1_8_R3_RecipeAccessor();
    }
}
