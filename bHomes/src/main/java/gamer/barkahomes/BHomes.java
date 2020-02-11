package gamer.barkahomes;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class BHomes extends JavaPlugin {
    private static Logger logger = Logger.getLogger("BHomes");
    public HashMap<UUID, List<Home>> homes = new HashMap<>(125);

    @Override
    public void onEnable() {
        getCommand("home").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length < 1) {
                    player.sendMessage(ChatColor.GOLD + "Invalid args /home [name]");
                    return true;
                }
                List<Home> h = this.homes.get(player.getUniqueId());
                h.forEach(home -> {
                    if (home.name.equalsIgnoreCase(args[0])) {
                        player.teleport(home.location);
                        player.sendMessage(ChatColor.GOLD + "Teleported to home '" + ChatColor.GRAY + home.displayname + ChatColor.GOLD + "'");
                    }
                });
            }
            return true;
        });

        getCommand("homes").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                List<Home> homes = this.homes.get(player.getUniqueId());
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < homes.size(); i++) {
                    builder.append(',').append(' ').append(homes.get(i).displayname);
                }
                player.sendMessage(ChatColor.GOLD + "Your homes are: " + builder.substring(2));
            }
            return true;
        });

        getCommand("sethome").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length < 1) {
                    player.sendMessage(ChatColor.GOLD + "Invalid args /sethome [name]");
                    return true;
                }
                List<Home> h;
                if ((h = homes.get(player.getUniqueId())) != null) {
                    Home home = new Home(player.getLocation().toBlockLocation().toCenterLocation(), args[0]);
                    for (int i = 0; i < h.size(); i++) {
                        Home home1 = h.get(i);
                        if (home1.name.equals(home.name)) {
                            h.set(i, home);
                            saveHomes();
                            player.sendMessage(ChatColor.GOLD + "Set home: '" + ChatColor.GRAY + args[0] + ChatColor.GOLD + "'");
                            return true;
                        }
                    }
                    h.add(home);
                } else {
                    h = new ArrayList<>();
                    h.add(new Home(player.getLocation().toBlockLocation().toCenterLocation(), args[0]));
                    homes.put(player.getUniqueId(), h);
                }
                saveHomes();
                player.sendMessage(ChatColor.GOLD + "Set home: '" + ChatColor.GRAY + args[0] + ChatColor.GOLD + "'");
            }
            return true;
        });


        getCommand("deletehome").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                List<Home> h;
                if ((h = homes.get(player.getUniqueId())) != null) {
                    for (int i = 0; i < h.size(); i++) {
                        Home home = h.get(i);
                        if (home.name.equals(args[0].toUpperCase())) {
                            h.remove(i);
                            player.sendMessage(ChatColor.GOLD + "Deleted home '" + ChatColor.GRAY + home.displayname + ChatColor.GOLD + "'");
                            return true;
                        }
                    }
                    player.sendMessage(ChatColor.GOLD + "You do not have a home called '" + ChatColor.GRAY + args[0] + ChatColor.GOLD + "'");
                    return true;
                }
                player.sendMessage(ChatColor.GOLD + "You do not have any homes");
            }
            return true;
        });
        loadHomes();
    }


    private Location sanitize(Location from) {
        Location n = from.clone();
        n.setPitch(0);
        n.setYaw(0);
        return n;
    }

    @Override
    public void onDisable() {
    }


    private void loadHomes() {
        File file = new File(getDataFolder(), "homes.yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            Set<String> keys = config.getKeys(false);
            keys.forEach(s -> {
                UUID uuid = UUID.fromString(s);
                List<Home> ho = new ArrayList<>();
                ConfigurationSection playerHomes = config.getConfigurationSection(s);
                Set<String> keys1 = playerHomes.getKeys(false);
                keys1.forEach(key -> {
                    ConfigurationSection homeData = playerHomes.getConfigurationSection(key);
                    ho.add(new Home(Location.deserialize(homeData.getConfigurationSection("location").getValues(false)), homeData.getString("displayname")));
                });
                homes.put(uuid, ho);
            });
            logger.info(String.format("Loaded %d homes", homes.size()));
        }
    }


    private void saveHomes() {
        File file = new File(getDataFolder(), "homes.yml");
        YamlConfiguration config = new YamlConfiguration();
        homes.forEach((uuid, homes1) -> {
            YamlConfiguration y = new YamlConfiguration();
            int h = 0;
            for (int i = 0; i < homes1.size(); i++) {
                Home home = homes1.get(i);
                y.set(String.valueOf(h++), convertHome(home));
            }
            config.set(uuid.toString(), y);
        });
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private YamlConfiguration convertHome(Home home) {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("name", home.name);
        yaml.set("displayname", home.displayname);
        yaml.set("location", home.location.serialize());
        return yaml;
    }

    public class Home {
        public Location location;
        public String name;
        public String displayname;


        public Home(Location location, String name) {
            this.location = location;
            this.displayname = name;
            this.name = name.toUpperCase();
        }
    }

}
