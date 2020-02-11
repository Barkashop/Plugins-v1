package gamer.bmagic.spell.scroll;

import gamer.bmagic.Main;
import gamer.bmagic.crafting.CraftManager;
import gamer.bmagic.crafting.PloRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ScrollUtils {

    public static class Cores {
        public static final ItemStack SCROLL_CORE = new ItemStack(Material.PHANTOM_MEMBRANE);
        public static final ItemStack TELEPORT_CORE = new ItemStack(Material.LAPIS_LAZULI);
        public static final ItemStack REFINED_TELEPORT_CORE = new ItemStack(Material.LAPIS_BLOCK);
        public static final ItemStack ALCHEMY_CORE = new ItemStack(Material.BOOK);
        public static final ItemStack ADVANCED_ALCHEMY_CORE = new ItemStack(Material.POTION);
    }


    public static class Scrolls {
        public static final ItemStack TELEPORT_SCROLL = new ItemStack(Material.MAP, 1);
        public static final ItemStack REFINED_TELEPORT_SCROLL = new ItemStack(Material.MAP, 1);
        public static final ItemStack ALCHEMY_SCROLL = new ItemStack(Material.MAP, 1);


        private Scrolls() {
            {
                ItemMeta meta = TELEPORT_SCROLL.getItemMeta();

                meta.setDisplayName("Teleportation Scroll");
                ((Damageable) meta).setDamage(1024);

                TELEPORT_SCROLL.setItemMeta(meta);
                CraftManager.BLACKLIST.add(TELEPORT_SCROLL);
            }
            {
                ItemMeta meta = REFINED_TELEPORT_SCROLL.getItemMeta();

                meta.setDisplayName("Refined Teleportation Scroll");
                ((Damageable) meta).setDamage(826);

                REFINED_TELEPORT_SCROLL.setItemMeta(meta);
                CraftManager.BLACKLIST.add(REFINED_TELEPORT_SCROLL);
            }
            {
                ItemMeta meta = ALCHEMY_SCROLL.getItemMeta();

                meta.setDisplayName("Alchemy Scroll");
                ((Damageable) meta).setDamage(826);

                ALCHEMY_SCROLL.setItemMeta(meta);
                CraftManager.BLACKLIST.add(ALCHEMY_SCROLL);
            }
        }
    }


    public ScrollUtils() {
        new Scrolls();


        // Scroll Core //
        {
            ItemMeta meta = Cores.SCROLL_CORE.getItemMeta();
            meta.setDisplayName("Spell Core");
            ((Damageable) meta).setDamage(24);
            Cores.SCROLL_CORE.setItemMeta(meta);


            new PloRecipe(Cores.SCROLL_CORE)
                    .setSlot(1, Material.GOLD_NUGGET)
                    .setSlot(3, Material.GOLD_NUGGET)
                    .setSlot(5, Material.GOLD_NUGGET)
                    .setSlot(7, Material.GOLD_NUGGET)
                    .setSlot(4, Material.LAPIS_LAZULI)
                    .register(new NamespacedKey(Main.INSTANCE, "scroll_core"));
            CraftManager.BLACKLIST.add(Cores.SCROLL_CORE);
        }
        // Teleportation Core //
        {

            ItemMeta meta = Cores.TELEPORT_CORE.getItemMeta();
            meta.setDisplayName("Teleportation Core");
            ((Damageable) meta).setDamage(24);
            Cores.TELEPORT_CORE.setItemMeta(meta);


            new PloRecipe(Cores.TELEPORT_CORE)
                    .setSlot(1, Material.QUARTZ)
                    .setSlot(3, Material.QUARTZ)
                    .setSlot(5, Material.QUARTZ)
                    .setSlot(7, Material.QUARTZ)
                    .setSlot(4, Cores.SCROLL_CORE)
                    .register(new NamespacedKey(Main.INSTANCE, "teleport_core"));
            CraftManager.BLACKLIST.add(Cores.TELEPORT_CORE);
        }
        // Refined Teleportation Core //
        {

            ItemMeta meta = Cores.REFINED_TELEPORT_CORE.getItemMeta();
            meta.setDisplayName("Refined Teleportation Core");
            ((Damageable) meta).setDamage(24);
            Cores.REFINED_TELEPORT_CORE.setItemMeta(meta);

            new PloRecipe(Cores.REFINED_TELEPORT_CORE)
                    .setSlot(1, Cores.TELEPORT_CORE)
                    .setSlot(3, Cores.TELEPORT_CORE)
                    .setSlot(5, Cores.TELEPORT_CORE)
                    .setSlot(7, Cores.TELEPORT_CORE)
                    .setSlot(4, Cores.SCROLL_CORE)
                    .register(new NamespacedKey(Main.INSTANCE, "rteleport_core"));


            CraftManager.BLACKLIST.add(Cores.REFINED_TELEPORT_CORE);

        }

        // Alchemy Core
        {
            ItemMeta meta = Cores.ALCHEMY_CORE.getItemMeta();
            meta.setDisplayName("Alchemy Core");
            ((Damageable) meta).setDamage(24);
            Cores.ALCHEMY_CORE.setItemMeta(meta);

            new PloRecipe(Cores.ALCHEMY_CORE)
                    .setSlot(1, Material.IRON_ORE)
                    .setSlot(3, Material.IRON_ORE)
                    .setSlot(5, Material.GOLD_ORE)
                    .setSlot(7, Material.GOLD_ORE)
                    .setSlot(4, Cores.SCROLL_CORE)
                    .register(new NamespacedKey(Main.INSTANCE, "alchemy_core"));


            CraftManager.BLACKLIST.add(Cores.ALCHEMY_CORE);


        }

        // Advanced Alchemy Core
        {
            ItemMeta meta = Cores.ADVANCED_ALCHEMY_CORE.getItemMeta();
            meta.setDisplayName("Advanced Alchemy Core");
            ((Damageable) meta).setDamage(24);
            Cores.ADVANCED_ALCHEMY_CORE.setItemMeta(meta);

            new PloRecipe(Cores.ADVANCED_ALCHEMY_CORE)
                    .setSlot(1, Cores.ALCHEMY_CORE)
                    .setSlot(3, Cores.ALCHEMY_CORE)
                    .setSlot(5, Cores.ALCHEMY_CORE)
                    .setSlot(7, Cores.ALCHEMY_CORE)
                    .setSlot(4, Material.DIAMOND_BLOCK);
//                    .register(new NamespacedKey(Main.INSTANCE, "aalchemy_core"));


            CraftManager.BLACKLIST.add(Cores.ADVANCED_ALCHEMY_CORE);

        }
    }


    public static int getMetadata(ItemStack stack) {
        if (stack.hasItemMeta()) {
            return ((Damageable) stack.getItemMeta()).getDamage();
        }
        return -1;
    }
}
