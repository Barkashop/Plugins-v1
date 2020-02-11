package gamer.bwallet.utils;

import gamer.bwallet.BWallet;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static gamer.bwallet.BWallet.$;

public class WalletManager {
    private static Map<String, Wallet> wallets = new HashMap<>(25);

    static {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            AtomicBoolean changed = new AtomicBoolean(false);

            @Override
            public void run() {
                wallets.forEach((s, wallet) -> {
                    if (wallet.previous == wallet.balance) return;
                    wallet.previous = wallet.balance;
                    changed.set(true);
                });
                if (changed.getAndSet(false)) {
                    BWallet.manager.saveWallets();
                }
            }
        }, 0, 500);
    }


    public void loadWallets() {
        YamlConfiguration walletsConfig = FileHelper.getYAML("wallets.yml");
        Map<String, Object> values = walletsConfig.getValues(false);
        values.forEach((s, o) -> getWallet(o.toString()));
    }


    public Wallet loadWallet(String UUID) {
        Wallet wallet;

        File file = FileHelper.getFile("wallets" + File.separator + UUID + File.separator + "wallet.yml");
        if (!file.exists()) return null;
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        wallet = new Wallet();
        wallet.owner = Bukkit.getOfflinePlayer(java.util.UUID.fromString(yaml.getString("owner")));
        wallet.balance = yaml.getDouble("balance");
        wallet.previous = wallet.balance;
        wallets.put(UUID, wallet);
        return wallet;
    }


    public Wallet getWallet(String UUID) {
        Wallet wallet;
        if ((wallet = wallets.get(UUID)) != null || (wallet = loadWallet(UUID)) != null) {
            return wallet;
        }
        new File(FileHelper.getFile("wallets"), UUID).mkdirs();
        File file = FileHelper.getFile("wallets" + File.separator + UUID + File.separator + "wallet.yml");
        YamlConfiguration config = new YamlConfiguration();
        config.set("owner", UUID);
        config.set("balance", $().getConfig().getDouble("default"));
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        wallet = loadWallet(UUID);
        saveWallets();
        return wallet;
    }


    public Wallet getWallet(UUID uuid) {
        return getWallet(uuid.toString());
    }

    public Wallet getWallet(OfflinePlayer player) {
        return getWallet(player.getUniqueId());
    }

    private void saveWallets() {
        File file = FileHelper.getFile("wallets.yml");
        YamlConfiguration walletsConfig = new YamlConfiguration();
        wallets.forEach((s, wallet) -> walletsConfig.set(wallet.owner.getName(), s));
        try {
            walletsConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Wallet {
        private double previous = 0;
        public double balance = 0;
        public OfflinePlayer owner;


    }
}
