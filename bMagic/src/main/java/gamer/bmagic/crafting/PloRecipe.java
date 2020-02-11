package gamer.bmagic.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;
import java.util.Map;


public class PloRecipe {
    private ItemStack output;
    private ItemStack[] slots = new ItemStack[9];

    public PloRecipe(ItemStack output) {
        this.output = output;
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new ItemStack(Material.AIR);
        }
    }


    public PloRecipe(Material material) {
        this(new ItemStack(material));
    }

    ItemStack getOutput() {
        return this.output;
    }

    public void register(NamespacedKey key) {
        ShapedRecipe rec = new ShapedRecipe(key, output);

        ItemStack[] slots = getSlots();
        String[] lines = new String[3];
        Material[] materials = new Material[9];
        for (int iLine = 0; iLine < 3; iLine++) {
            StringBuilder line = new StringBuilder();
            for (int index = 0; index < 3; index++) {
                ItemStack slot = slots[(iLine * 3) + index];
                if (slot.getType() == Material.AIR) {
                    materials[(iLine * 3) + index] = null; // ? Are undefined pre length arrays null filled?
                    line.append(' ');
                } else {
                    line.append(slot.getType().name(), 0, 1);
                    materials[(iLine * 3) + index] = slot.getType();
                }
            }
            lines[iLine] = line.toString();
        }
        rec.shape(lines);

        for (Material material : materials) {
            if (material != null) {
                rec.setIngredient(material.name().charAt(0), material);
            }
        }


        Bukkit.addRecipe(rec);
        PloRecipeList.addShapedRecipe(this);
    }


    ItemStack[] getSlots() {
        return slots;
    }

    public PloRecipe setSlot(int i, ItemStack stack) {
        if (i < 0 || i > 8) {
            new Throwable("Slot " + i + " is out of range [0-8]").printStackTrace();
            return this;
        }
        slots[i] = stack;
        return this;
    }


    public PloRecipe setSlot(int i, Material material) {
        return setSlot(i, new ItemStack(material));
    }


    public boolean matches(List<ItemStack> items) {
        boolean match = true;


        ItemStack[] slots = getSlots();
        for (int i = 0; i < items.size(); i++) {
            ItemStack slot = slots[i];
            if (!slot.equals(items.get(i))) {
                match = false;
            }
        }
        return match;

    }

}
