package prod.brainiac.olympixel;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.OlympixelManager;
import prod.brainiac.olympixel.commands.OlympixelTabComp;
import prod.brainiac.olympixel.events.ArmorListener;
import prod.brainiac.olympixel.events.DispenserArmorListener;
import prod.brainiac.olympixel.listeners.PlayerJoinListener;
import prod.brainiac.olympixel.listeners.PlayerLeaveListener;
import prod.brainiac.olympixel.utils.ChatMsgManager;
import prod.brainiac.olympixel.utils.ConfigHook;
import prod.brainiac.olympixel.utils.TeamManager;

import java.util.Objects;

public final class Olympixel extends JavaPlugin {
    private final ChatMsgManager chatMsgManager = new ChatMsgManager(ChatColor.DARK_PURPLE, "[Olympixel]", ChatColor.LIGHT_PURPLE);
    private final TeamManager teamManager = new TeamManager();
    public final ConfigHook configHook = new ConfigHook(this);


    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("olympixel")).setExecutor(new OlympixelManager(chatMsgManager, this));
        Objects.requireNonNull(getCommand("olympixel")).setTabCompleter(new OlympixelTabComp());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(teamManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(teamManager, this), this);
        getServer().getPluginManager().registerEvents(new DispenserArmorListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        teamManager.init();
        saveDefaultConfig();
    }

}
