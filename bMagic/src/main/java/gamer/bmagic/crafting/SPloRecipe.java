package gamer.bmagic.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.List;

@Deprecated
public class SPloRecipe extends PloRecipe {


    public SPloRecipe(Material material) {
        super(material);
    }

    @Override
    public void register(NamespacedKey key) {
        ShapelessRecipe rec = new ShapelessRecipe(key, getOutput());

        for (int i = 0; i < getSlots().length; i++) {
            Material type = getSlots()[i].getType();
            if (type != Material.AIR) {
                rec.addIngredient(type);
            }
        }

        Bukkit.addRecipe(rec);
        PloRecipeList.addShapedRecipe(this);
    }


    @Override
    public boolean matches(List<ItemStack> items) {
        boolean match = true;
        ItemStack[] slots = getSlots();

        for (int i = 0; i < items.size(); i++) {
            boolean inside = false;
            for (int i1 = 0; i1 < slots.length; i1++) {
                ItemStack item = items.get(i);
                if (item == null) continue;
                if (items.get(i).equals(slots[i1])) {
                    inside = true;
                }
            }
            if (!inside) match = false;
        }
        return match;
    }
}
