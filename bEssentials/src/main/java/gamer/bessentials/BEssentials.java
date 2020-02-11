package gamer.bessentials;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BEssentials extends JavaPlugin {



    @Override
    public void onEnable() {


        Material[] edible = getEdibleMaterials();
        for (Material food : edible) {
            ItemStack result = new ItemStack(food, 1);

            ItemMeta meta = result.getItemMeta();
            meta.setDisplayName(food.name());
            ((Damageable) meta).setDamage(1);

            result.setItemMeta(meta);

            ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(this, "poised" + food.name()), result);

            recipe.addIngredient(food);
            recipe.addIngredient(Material.CACTUS_GREEN);

            Bukkit.addRecipe(recipe);

        }

        getServer().getPluginManager().registerEvents(new EventListener(), this);

    }


    private Material[] getEdibleMaterials() {
        List<Material> materials = new ArrayList<>();

        Material[] values = Material.values();
        for (Material value : values) {
            switch (value) {
                case APPLE:
                case BAKED_POTATO:
                case BEEF:
                case BEETROOT:
                case BEETROOT_SOUP:
                case BREAD:
                case CARROT:
                case CHICKEN:
                case CHORUS_FRUIT:
                case COD:
                case COOKED_BEEF:
                case COOKED_CHICKEN:
                case COOKED_COD:
                case COOKED_MUTTON:
                case COOKED_PORKCHOP:
                case COOKED_RABBIT:
                case COOKED_SALMON:
                case COOKIE:
                case DRIED_KELP:
                case ENCHANTED_GOLDEN_APPLE:
                case GOLDEN_APPLE:
                case GOLDEN_CARROT:
                case MELON_SLICE:
                case MUSHROOM_STEW:
                case MUTTON:
                case POISONOUS_POTATO:
                case PORKCHOP:
                case POTATO:
                case PUFFERFISH:
                case PUMPKIN_PIE:
                case RABBIT:
                case RABBIT_STEW:
                case ROTTEN_FLESH:
                case SALMON:
                case SPIDER_EYE:
                case TROPICAL_FISH:
                    materials.add(value);
                    break;
                default:
                    break;
            }
        }

        return materials.toArray(new Material[0]);
    }

}
