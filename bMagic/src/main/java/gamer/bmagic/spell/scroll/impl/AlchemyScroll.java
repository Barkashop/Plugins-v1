package gamer.bmagic.spell.scroll.impl;

import gamer.bmagic.spell.scroll.ScrollSpell;
import gamer.bmagic.spell.scroll.ScrollUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AlchemyScroll extends ScrollSpell implements Listener {
    public AlchemyScroll() {
        super(1825);
    }

    @Override
    public boolean cast(Player player) {
        return false;
    }


    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        ItemStack with = event.getCursor();
        if (clicked == null) return;
        if (with == null) return;

        if (ScrollUtils.getMetadata(clicked) == this.id) {
            event.setCancelled(true);
            switch (with.getType()) {
                case IRON_ORE:
                case IRON_INGOT:
                    if (with.getType() == Material.IRON_ORE) {
                        event.setCursor(new ItemStack(Material.GOLD_ORE, with.getAmount()));
                    } else {
                        event.setCursor(new ItemStack(Material.GOLD_INGOT, with.getAmount()));
                    }
                    clicked.setAmount(clicked.getAmount() - 1);
                    break;
                default:
                    break;
            }
        }
    }

}
