package gamer.bmagic.spell.scroll.impl;

import gamer.bmagic.Main;
import gamer.bmagic.crafting.PloRecipe;
import gamer.bmagic.spell.scroll.ScrollSpell;
import gamer.bmagic.spell.scroll.ScrollUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static gamer.bmagic.spell.SpellUtils.getRandom;

public class TeleportationScroll extends ScrollSpell {


    public TeleportationScroll() {
        super(1024);
        new PloRecipe(ScrollUtils.Scrolls.TELEPORT_SCROLL)
                .setSlot(0, Material.PAPER)
                .setSlot(1, Material.PAPER)
                .setSlot(2, Material.PAPER)
                .setSlot(3, Material.PAPER)
                .setSlot(4, ScrollUtils.Cores.TELEPORT_CORE)
                .setSlot(5, Material.PAPER)
                .setSlot(6, Material.PAPER)
                .setSlot(7, Material.PAPER)
                .setSlot(8, Material.PAPER)
                .register(new NamespacedKey(Main.INSTANCE, "15125236348"));
    }

    @Override
    public boolean cast(Player player) {
        player.sendMessage(ChatColor.GOLD + "I dont feel so go-");
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 17, 1));

        int factor = getRandom(-100, 400);
        int x = getRandom(-1000, 1000) * factor;
        int z = getRandom(-1000, 1000) * factor;
        int y = findY(x, z, player);
        player.teleport(new Location(player.getWorld(), x, y, z));
        return true;
    }


    private boolean canBe(int x, int z, int y, World world) {
        return !world.getBlockAt(x, y, z).getType().isSolid() && !world.getBlockAt(x, y + 1, z).getType().isSolid();
    }


    private int findY(int x, int z, Player player) {
        int height = 256;
        for (int i = 10; i < height; i++) {
            if (canBe(x, z, i, player.getWorld())) {
                return i;
            }
        }
        return height;
    }


}
