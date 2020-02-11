package gamer.bmagic.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftManager implements Listener {
    public static final List<ItemStack> BLACKLIST = new ArrayList<>();


    @EventHandler
    public void preCraftEvent(PrepareItemCraftEvent e) {
        List<ItemStack> items = Arrays.asList(e.getInventory().getMatrix());

        for (PloRecipe recipe : PloRecipeList.getShapedRecipes()) {
            if (RecipeMatch.matches(items, recipe)) {
                e.getInventory().setResult(recipe.getOutput());
                return;
            }
        }
    }
}
