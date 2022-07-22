package captchaplugin.captchaplugin.Data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayersOnValidation {
    // Static data holder
    public static Map<Player, Boolean> players = new HashMap<>();
    public static Map<Player, String> answers = new HashMap<>();
}
