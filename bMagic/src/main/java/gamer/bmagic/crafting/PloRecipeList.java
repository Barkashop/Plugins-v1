package gamer.bmagic.crafting;

import java.util.ArrayList;
import java.util.List;

public class PloRecipeList {
    /**
     * @author Plo457
     */
    private static List<PloRecipe> shaped;

    static void addShapedRecipe(PloRecipe recipe) {
        if (shaped == null) {
            shaped = new ArrayList<>();
        }
        shaped.add(recipe);
    }

    static List<PloRecipe> getShapedRecipes() {
        return shaped;
    }

}
