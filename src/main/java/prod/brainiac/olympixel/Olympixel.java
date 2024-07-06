package prod.brainiac.olympixel;

import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.OlympixelManager;
import prod.brainiac.olympixel.listeners.PlayerJoinListener;
import prod.brainiac.olympixel.utils.TeamManager;

public final class Olympixel extends JavaPlugin {

    private static Olympixel plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("olympixel").setExecutor(new OlympixelManager());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(),plugin);
        TeamManager.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Olympixel getPlugin(){
        return plugin;
    }
}
