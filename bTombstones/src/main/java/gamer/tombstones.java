package gamer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class tombstones extends JavaPlugin {
    private static long index;
    private static tombstones INSTANCE;
    private static Map<String, Map.Entry<Block, DeathListener.DeathData>> deaths = new LinkedHashMap<>();
    private File file;


    {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        super.onEnable();

        if (!(file = new File(getDataFolder(), "config.yml")).exists()) {
            try {
                getConfig().save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            copy(getResource("config.yml"), file);
        } else {
            loadDeaths();
        }
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }


    @Override
    public void onDisable() {
        super.onDisable();
        deaths.clear();
    }


    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024 << 2];

            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    private void loadDeaths() {
        ConfigurationSection deathsList = getConfig().getConfigurationSection("deaths");
        if (deathsList == null) return;
        Set<String> keys = deathsList.getKeys(false);
        if (keys == null) return;
        keys.forEach(s -> {
            ConfigurationSection configData = deathsList.getConfigurationSection(s);

            Location location = Location.deserialize(configData.getConfigurationSection("location").getValues(false));
            List<?> items = configData.getList("items");
            ItemStack[] i = new ItemStack[items.size()];
            items.forEach(o -> i[items.indexOf(o)] = (ItemStack) o);
            items.clear();
            DeathListener.DeathData data = new DeathListener.DeathData(location, i, configData.getInt("xp"), Material.getMaterial(configData.getString("material")));
            deaths.put(s, new AbstractMap.SimpleEntry<>(null, data));
        });
        index = deaths.size() + 1;
    }


    private static void saveDeaths() {
        INSTANCE.getConfig().set("deaths", new YamlConfiguration());
        deaths.forEach((id, death) -> {
            YamlConfiguration configData = new YamlConfiguration();
            DeathListener.DeathData data = death.getValue();
            configData.set("xp", data.xp);
            configData.set("material", data.material.name());
            configData.set("location", data.location.serialize());
            configData.set("items", data.items);

            INSTANCE.getConfig().getConfigurationSection("deaths").set(id, configData);
        });
        INSTANCE.getConfig().set("config-version", 1.5);
        try {
            INSTANCE.getConfig().save(INSTANCE.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class DeathListener implements Listener {


        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            Action action = event.getAction();
            Player player = event.getPlayer();
            if (action == Action.RIGHT_CLICK_BLOCK) {
                DeathData data;
                Map.Entry<Block, DeathData> death;
                Location location = event.getClickedBlock().getLocation().toBlockLocation();
                for (String block : deaths.keySet()) {
                    death = deaths.get(block);
                    data = death.getValue();
                    if (data.location.getBlockX() == location.getBlockX() && data.location.getBlockY() == location.getBlockY() && data.location.getBlockZ() == location.getBlockZ()) {
                        player.setLevel(player.getLevel() + data.xp);


                        Map<Integer, ItemStack> failed = player.getInventory().addItem(data.items);
                        failed.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));


                        if (death.getKey() != null) {
                            location.getBlock().setType(death.getKey().getType(), true);
                            death.getKey().getState().update(true);
                        } else {
                            location.getBlock().setType(data.material);
                        }

                        deaths.remove(block);
                        saveDeaths();
                        break;
                    }

                }
            }
        }


        @EventHandler
        public void onEvent(EntityDeathEvent event) {


            if (event.getEntity() instanceof Player) {
                Location location = event.getEntity().getLocation();
                Player player = (Player) event.getEntity();

                ItemStack[] items = event.getDrops().toArray(new ItemStack[0]);

                deaths.put(String.valueOf(index++), new AbstractMap.SimpleEntry<>(location.getBlock(), new DeathData(location.toBlockLocation(), items, player.getLevel(), location.getBlock().getType())));

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(INSTANCE, () -> location.getBlock().setType(Material.CHEST), 5);
                event.getDrops().clear();
                event.setDroppedExp(0);

                saveDeaths();

                event.getEntity().sendMessage(String.format("Death Coordinates [X: %s, Y: %s, Z: %s]", location.getBlockX(), location.getBlockY(), location.getBlockZ()));

            }
        }


        static class DeathData {
            Material material;
            Location location;
            ItemStack[] items;
            int xp;

            DeathData(Location location, ItemStack[] items, int xp, Material material) {
                this.location = location;
                this.items = items;
                this.xp = xp;
                this.material = material;
            }
        }
    }
}
