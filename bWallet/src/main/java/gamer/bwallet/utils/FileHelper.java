package gamer.bwallet.utils;

import gamer.bwallet.BWallet;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static gamer.bwallet.BWallet.$;

public class FileHelper {


    public static File getFile(String name) {
        return new File($().getDataFolder(), name);
    }


    public static File[] getFiles(String path) {
        if (path.length() == 0) {
            return $().getDataFolder().listFiles();
        }
        return new File($().getDataFolder(), path).listFiles();
    }


    public static YamlConfiguration getYAML(String name) {
        return YamlConfiguration.loadConfiguration(getFile(name));
    }

    public static void copy(InputStream in, File file) {
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
}
