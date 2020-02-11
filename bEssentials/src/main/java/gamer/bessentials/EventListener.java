package gamer.bessentials;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventListener implements Listener {


    @EventHandler
    public void onEvent(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().isEdible()) {
            if (((Damageable) event.getItem().getItemMeta()).getDamage() == 1) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
            }
        }
    }


}
