package captchaplugin.captchaplugin.ConfigUtils;

import captchaplugin.captchaplugin.CaptchaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class DefaultConfigUtils {
    private static File file;
    private static FileConfiguration locales;

    /**
     * Checks plugin data folder and creating it if not exists.
     * Checks config.yml file in plugin data folder and creating it if not exists.
     *
     * @param  plugin  a plugin instance
     */
    public static void setUp(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().warning(e.getMessage());
                return;
            }
        }

        locales = YamlConfiguration.loadConfiguration(file);

        load_defaults(locales);
    }

    private static void load_defaults(FileConfiguration config) {
        config.addDefault("Fox", "https://cdn.discordapp.com/attachments/981524136184651856/999345067225514005/125x125.png");
        config.addDefault("Frog", "https://cdn.discordapp.com/attachments/981524136184651856/999346647463108681/125x125_frog.png");
        config.addDefault("Cat", "https://cdn.discordapp.com/attachments/981524136184651856/999348550804713482/125x125_cat.png");
        config.addDefault("Dog", "https://cdn.discordapp.com/attachments/981524136184651856/999349737528508556/125x125_dog.png");

        config.options().copyDefaults(true);
        save();
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
     * Reloading configuration file to update any changes from any sources.
     */
    public static void reload() {
        locales = YamlConfiguration.loadConfiguration(file);
    }
}
