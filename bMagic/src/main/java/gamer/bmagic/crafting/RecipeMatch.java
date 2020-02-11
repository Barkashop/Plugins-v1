package gamer.bmagic.crafting;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class RecipeMatch {
    /**
     * @author Plo457
     * Just no. This code was so terrible i couldnt stand it i rewrote this method ** fite ** me
     */
    public static boolean matches(List<ItemStack> items, PloRecipe recipe) {
        return recipe.matches(items);
    }
}
