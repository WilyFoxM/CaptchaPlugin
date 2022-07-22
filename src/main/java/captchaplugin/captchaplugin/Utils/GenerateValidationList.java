package captchaplugin.captchaplugin.Utils;

import captchaplugin.captchaplugin.CaptchaPlugin;
import captchaplugin.captchaplugin.ConfigUtils.DefaultConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class GenerateValidationList {
    private final List<String> urls = new ArrayList<>();

    /**
     * Initializing URLs list.
     * Generating 3 unique options in format of OPTION_NAME, IMAGE_URL.
     *
     * @return  random generated Map<String, String> of validation images
     */
    public Map<String, String> generate() {
        initURLs();
        Map<String, String> generated = new HashMap<>();

        do {
            List<String> randomEntry = getRandomEntry();
            String key = randomEntry.get(0);
            String value = randomEntry.get(1);
            while (!generated.containsKey(key)) {
                generated.put(key, value);
            }

        } while (generated.size() != 3);

        return generated;
    }

    private void initURLs() {
        DefaultConfigUtils.reload();
        FileConfiguration config = DefaultConfigUtils.get();

        for (String key : config.getKeys(false)) {
            urls.add(config.getString(key));
        }
    }

    private List<String> getRandomEntry() {
        List<String> entry = new ArrayList<>();

        Random rand = new Random();
        int index = rand.nextInt(urls.size());
        String name = URLsFactory.getName(index);
        String url = urls.get(index);

        entry.add(name);
        entry.add(url);

        return entry;
    }
}
