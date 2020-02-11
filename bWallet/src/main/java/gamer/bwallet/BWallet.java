package gamer.bwallet;

import gamer.bwallet.utils.FileHelper;
import gamer.bwallet.utils.WalletManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class BWallet extends JavaPlugin {
    public static WalletManager manager = new WalletManager();
    private static BWallet INSTANCE;
    private static final String VERSION = "1.0";
    private static final Logger log = Logger.getLogger("BWallet");


    {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        log.info(String.format("Loaded bWallet [Version %s]", VERSION));
        createDefaults();
        manager.loadWallets();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }


    public static BWallet $() {
        return INSTANCE;
    }

    public void createDefaults() {
        File walletsFile = FileHelper.getFile("wallets.yml");
        if (!walletsFile.exists()) {
            try {
                new YamlConfiguration().save(walletsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File configFile = FileHelper.getFile("config.yml");
        if (!configFile.exists()) {
            try {
                FileHelper.copy(getResource("config.yml"), configFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class EventListener implements Listener {
        @EventHandler
        public void onJoin(PlayerJoinEvent event) {
            manager.getWallet(event.getPlayer());
        }
    }
}
