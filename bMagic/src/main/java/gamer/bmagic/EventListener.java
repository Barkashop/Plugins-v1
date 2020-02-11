package gamer.bmagic;

import gamer.bmagic.spell.scroll.ScrollSpell;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.Damageable;

import java.util.Optional;


public class EventListener implements Listener {


    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.MAP) {
            Player player = event.getPlayer();
            int metadata = ((Damageable) event.getItem().getItemMeta()).getDamage();
            Optional<ScrollSpell> first = Main.SCROLLS.stream().filter(scroll -> scroll.id == metadata).findFirst();
            if (first.isPresent()) {
                ScrollSpell scroll = first.get();
                event.setUseItemInHand(Event.Result.DENY);
                if (scroll.cast(player)) {
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                }
            }
        }
    }


}
