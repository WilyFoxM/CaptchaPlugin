package captchaplugin.captchaplugin.Commands;

import captchaplugin.captchaplugin.Data.PlayersOnValidation;
import captchaplugin.captchaplugin.ConfigUtils.ConfigUtils;
import captchaplugin.captchaplugin.Utils.Serializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

public class ValidateAnswerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = ((Player) sender).getPlayer();
        if (!PlayersOnValidation.players.get(player)) return true;

        // Taking provided answer
        String answer = args[0];

        // Getting correct answer
        String correctAnswer = PlayersOnValidation.answers.get(player).toLowerCase();

        if (Objects.equals(answer, correctAnswer)) {
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Validated!");

            player.getInventory().clear();
            PlayersOnValidation.players.put(player, false);

            FileConfiguration data = ConfigUtils.get();

            // Getting location from config
            Location loc = Serializer.locationFromString((String) data.get(player.getUniqueId() + "_loc"));
            player.teleport(loc);

            // Unsetting config value
            data.set(player.getUniqueId().toString() + "_loc", "none");

            // Getting un-serialized inventory
            Map<Integer, ItemStack> inv = Serializer.invFromString((String) data.get(player.getUniqueId() + "_inv"));
            for (Map.Entry<Integer, ItemStack> e : inv.entrySet()) {
                player.getInventory().setItem(e.getKey(), e.getValue());
            }

            // Unsetting config value
            data.set(player.getUniqueId().toString() + "_inv", "none");
        } else {
            // Not correct answer
            player.kickPlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Not validated!");
        }

        return true;
    }
}
