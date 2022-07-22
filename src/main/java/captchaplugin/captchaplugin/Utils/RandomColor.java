package captchaplugin.captchaplugin.Utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomColor {
    /**
     * Get random ChatColor.
     *
     * @return  a random ChatColor object
     */
    public static ChatColor getRandomColor() {
        List<ChatColor> colors = new ArrayList<>();
        Random rand = new Random();

        colors.add(ChatColor.BLUE);
        colors.add(ChatColor.GREEN);
        colors.add(ChatColor.WHITE);
        colors.add(ChatColor.RED);
        colors.add(ChatColor.YELLOW);
        colors.add(ChatColor.DARK_BLUE);
        colors.add(ChatColor.DARK_PURPLE);
        colors.add(ChatColor.LIGHT_PURPLE);
        colors.add(ChatColor.AQUA);
        colors.add(ChatColor.BLACK);
        colors.add(ChatColor.DARK_AQUA);
        colors.add(ChatColor.DARK_GRAY);
        colors.add(ChatColor.DARK_GREEN);
        colors.add(ChatColor.GRAY);
        colors.add(ChatColor.GOLD);

        return colors.get(rand.nextInt(colors.size()));
    }
}
