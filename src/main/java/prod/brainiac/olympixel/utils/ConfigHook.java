package prod.brainiac.olympixel.utils;

import org.bukkit.configuration.file.FileConfiguration;
import prod.brainiac.olympixel.Olympixel;

public class ConfigHook {
    private final FileConfiguration config;


    public ConfigHook(Olympixel plugin) {
        config = plugin.getConfig();
    }

    public int getCountdownSecs() {
        return config.getInt("Game.countdown");
    }

    public Boolean repeatTask() {
        return config.getBoolean("Game.repeatTasks");
    }

}
