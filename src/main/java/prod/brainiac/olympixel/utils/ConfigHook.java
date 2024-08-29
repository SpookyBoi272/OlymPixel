package prod.brainiac.olympixel.utils;

import org.bukkit.configuration.file.FileConfiguration;
import prod.brainiac.olympixel.Olympixel;

public class ConfigHook {
    private final FileConfiguration config;
    private final Olympixel plugin;


    public ConfigHook(Olympixel plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
    }

    public int getCountdownSecs() {
        return config.getInt("Game.countdown");
    }

    public Boolean repeatTask() {
        return config.getBoolean("Game.repeatTasks");
    }

    public int getWinScore() {
        return config.getInt("Game.winScore");
    }

    public void setCountdownSecs(int val) {
        config.set("Game.countdown", val);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public void setWinScore(int val) {
        config.set("Game.winScore", val);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
