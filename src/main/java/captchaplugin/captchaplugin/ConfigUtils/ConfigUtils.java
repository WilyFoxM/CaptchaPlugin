package captchaplugin.captchaplugin.ConfigUtils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {
    private static File file;
    private static FileConfiguration locales;

    /**
     * Checks plugin data folder and creating it if not exists.
     * Checks data.yml file in plugin data folder and creating it if not exists.
     *
     * @param  plugin  a plugin instance
     */
    public static void setUp(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().warning(e.getMessage());
                return;
            }
        }

        locales = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Get Configuration File.
     *
     * @return  data FileConfiguration
     */
    public static FileConfiguration get() {
        return locales;
    }

    /**
     * Saves all existing in file data.
     */
    public static void save() {
        try {
            locales.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().warning(e.getMessage());
        }
    }

    /**
     * Get File in plugin data folder.
     *
     * @return  data File
     */
    public static File getFile() {
        return file;
    }

    /**
     * Reloading configuration file to update any changes from any sources.
     */
    public static void reload() {
        locales = YamlConfiguration.loadConfiguration(file);
    }
}
