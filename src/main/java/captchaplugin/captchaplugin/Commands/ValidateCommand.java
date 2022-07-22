package captchaplugin.captchaplugin.Commands;

import captchaplugin.captchaplugin.Utils.InitValidation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ValidateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) return false;

        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);
        boolean fromConsole = !(sender instanceof Player);

        if (!fromConsole && !sender.hasPermission("captcha.validateOthers")) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You don't have permissions!");
            return true;
        }

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player not found!");
            return true;
        }

        InitValidation.init(player);

        return true;
    }
}
