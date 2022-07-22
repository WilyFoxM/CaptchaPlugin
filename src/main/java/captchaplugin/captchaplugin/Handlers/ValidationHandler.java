package captchaplugin.captchaplugin.Handlers;

import captchaplugin.captchaplugin.Data.PlayersOnValidation;
import captchaplugin.captchaplugin.Utils.InitValidation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class ValidationHandler implements Listener {
    public ValidationHandler(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (PlayersOnValidation.players.get(p) != null && PlayersOnValidation.players.get(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        // Init validation on connection
        if (!p.hasPermission("captcha.bypass")) InitValidation.init(p);
    }
}
