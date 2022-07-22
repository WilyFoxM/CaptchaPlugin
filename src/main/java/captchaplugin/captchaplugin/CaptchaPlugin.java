package captchaplugin.captchaplugin;

import captchaplugin.captchaplugin.Commands.ValidateAnswerCommand;
import captchaplugin.captchaplugin.Commands.ValidateCommand;
import captchaplugin.captchaplugin.ConfigUtils.DefaultConfigUtils;
import captchaplugin.captchaplugin.Handlers.ValidationHandler;
import captchaplugin.captchaplugin.ConfigUtils.ConfigUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class CaptchaPlugin extends JavaPlugin {
    static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Init commands/handlers/configs
        this.getCommand("validate").setExecutor(new ValidateCommand());
        this.getCommand("validateAnswer").setExecutor(new ValidateAnswerCommand());

        new ValidationHandler(this);

        ConfigUtils.setUp(this);
        ConfigUtils.get().options().copyDefaults();
        ConfigUtils.save();

        DefaultConfigUtils.setUp(this);

        instance = this;
    }

    @Override
    public void onDisable() {
        // Saving config values
        ConfigUtils.get().options().copyDefaults();
        ConfigUtils.save();

        DefaultConfigUtils.save();
    }
}
