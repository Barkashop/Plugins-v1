package gamer.bmagic.spell.scroll.impl;

import gamer.bmagic.Main;
import gamer.bmagic.crafting.PloRecipe;
import gamer.bmagic.spell.scroll.ScrollSpell;
import gamer.bmagic.spell.scroll.ScrollUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static gamer.bmagic.spell.scroll.ScrollUtils.Scrolls.TELEPORT_SCROLL;

public class TPScroll extends ScrollSpell {
    private Random random = new Random();

    public TPScroll() {
        super(((Damageable) TELEPORT_SCROLL.getItemMeta()).getDamage());

        new PloRecipe(ScrollUtils.Scrolls.REFINED_TELEPORT_SCROLL)
                .setSlot(1, TELEPORT_SCROLL)
                .setSlot(3, TELEPORT_SCROLL)
                .setSlot(5, TELEPORT_SCROLL)
                .setSlot(7, TELEPORT_SCROLL)
                .setSlot(4, ScrollUtils.Cores.REFINED_TELEPORT_CORE)
                .register(new NamespacedKey(Main.INSTANCE, "16346329825"));

    }

    @Override
    public boolean cast(Player player) {
        player.sendMessage(ChatColor.GOLD + "This isn't weed");
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 17, 1));

        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);

        Player p = null;
        for (int i = 0; i < 3; i++) {
            p = players[random.nextInt(players.length)];
            if (p.getUniqueId().equals(player.getUniqueId())) {
                continue;
            }
            player.teleport(p);
            return true;
        }
        player.teleport(p);
        return true;
    }


}
