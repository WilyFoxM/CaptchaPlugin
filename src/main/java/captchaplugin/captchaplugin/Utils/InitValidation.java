package captchaplugin.captchaplugin.Utils;

import captchaplugin.captchaplugin.CaptchaPlugin;
import captchaplugin.captchaplugin.ConfigUtils.ConfigUtils;
import captchaplugin.captchaplugin.Data.PlayersOnValidation;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class InitValidation {
    private static BukkitTask validationTimer;

    /**
     * Generating random validation list and correct answer.
     * Teleporting player to validation location.
     * Clearing player inventory.
     * Stricting movement and saving previous location and inventory.
     *
     * @param  player  a player object
     */
    public static void init(Player player) {
        if (player.hasPermission("captcha.bypass")) return;

        Random rand = new Random();
        PlayerInventory pInv = player.getInventory();

        GenerateValidationList generator = new GenerateValidationList();
        Map<String, String> generated = generator.generate();

        int randomIndex = rand.nextInt(generated.size());
        String answer = (String) generated.keySet().toArray()[randomIndex];
        String validateUrl = (String) generated.values().toArray()[randomIndex];

        ItemStack map = MapUtils.getFilledMap(validateUrl);

        for (String option : generated.keySet()) {
            String styledOption = RandomColor.getRandomColor() + "" + ChatColor.BOLD + option;
            TextComponent clickable = new TextComponent(styledOption);
            clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/validateAnswer " + option.toLowerCase()));
            player.spigot().sendMessage(clickable);
        }

        PlayersOnValidation.players.put(player, true);
        PlayersOnValidation.answers.put(player, answer);

        FileConfiguration data = ConfigUtils.get();

        if (data.getString(player.getUniqueId().toString() + "_loc") == null || Objects.equals(data.getString(player.getUniqueId().toString() + "_loc"), "none")) data.set(player.getUniqueId().toString() + "_loc", Serializer.stringFromLocation(player.getLocation()));

        Map<Integer, ItemStack> inv = new HashMap<>();
        for (int i = 0; i <= 36; i++) {
            ItemStack item = pInv.getItem(i);
            if (item != null) inv.put(i, item);
        }

        if (data.getString(player.getUniqueId().toString() + "_inv") == null || Objects.equals(data.getString(player.getUniqueId().toString() + "_inv"), "none")) data.set(player.getUniqueId().toString() + "_inv", Serializer.stringFromInv(inv));

        ConfigUtils.save();

        pInv.clear();
        pInv.setItemInMainHand(map);

        player.teleport(new Location(player.getWorld(), 0, 1000, 0, 0, 100));

        Bukkit.getScheduler().runTaskLater(CaptchaPlugin.getInstance(), () -> {
            if (PlayersOnValidation.players.get(player)) player.kickPlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Not validated!");
        }, 200);

        AtomicReference<Float> progress = new AtomicReference<>(0.1f);
        validationTimer = Bukkit.getScheduler().runTaskTimer(CaptchaPlugin.getInstance(), () -> {
            if (PlayersOnValidation.players.get(player) && Bukkit.getPlayer(player.getUniqueId()) != null) {
                BossBar bar = Bukkit.createBossBar(RandomColor.getRandomColor() + "VALIDATION", BarColor.GREEN, BarStyle.SOLID, BarFlag.CREATE_FOG);
                bar.setProgress(progress.get());
                bar.addPlayer(player);
                progress.updateAndGet(v -> (float) (v + 0.1));
                Bukkit.getScheduler().runTaskLater(CaptchaPlugin.getInstance(), bar::removeAll, 20);
            } else {
                clearValidationTimer();
            }
        }, 0L, 20L);
    }

    private static void clearValidationTimer() {
        Bukkit.getScheduler().cancelTask(validationTimer.getTaskId());
    }
}
