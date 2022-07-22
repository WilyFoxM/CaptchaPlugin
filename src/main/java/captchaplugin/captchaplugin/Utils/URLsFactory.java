package captchaplugin.captchaplugin.Utils;

import captchaplugin.captchaplugin.CaptchaPlugin;
import captchaplugin.captchaplugin.ConfigUtils.DefaultConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class URLsFactory {
    /**
     * Returns name of a link in URLs list.
     *
     * @param  i  index of name in config file
     */
    public static String getName(int i) {
        DefaultConfigUtils.reload();
        FileConfiguration config = DefaultConfigUtils.get();
        List<String> names = new ArrayList<>();

        for (String key : config.getKeys(false)) {
            names.add(key);
        }

        return names.get(i);
    }

    /**
     * Returns index of link in urls list by name.
     *
     * @param  i  name of url
     */
    public static int getIndex(String i) {
        switch (i) {
            case "Fox":
                return 0;
            case "Frog":
                return 1;
            case "Cat":
                return 2;
            case "Dog":
                return 3;
            default:
                return 0;
        }
    }
}
