package prod.brainiac.olympixel;

import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.HelpCommand;

public final class Olympixel extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("Help").setExecutor(new HelpCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
