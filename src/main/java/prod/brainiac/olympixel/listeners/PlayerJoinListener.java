package prod.brainiac.olympixel.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.utils.TeamManager;

public class PlayerJoinListener implements Listener {

    private final TeamManager teamManager;
    private final JavaPlugin plugin;

    public PlayerJoinListener(TeamManager teamManager, JavaPlugin plugin){
        this.teamManager = teamManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        teamManager.addPlayerToTeam(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Remove the player from their team
        teamManager.removePlayerFromTeam(player);
        Bukkit.getScheduler().runTaskLater(plugin, teamManager::resortPlayers, 1L);
    }
}
