package prod.brainiac.olympixel.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;
import prod.brainiac.olympixel.utils.TeamManager;

public class PlayerLeaveListener implements Listener {
    private final TeamManager teamManager;
    private final JavaPlugin plugin;
    private final GameManager gameManager;

    public PlayerLeaveListener(TeamManager teamManager, JavaPlugin plugin, GameManager manager) {
        this.teamManager = teamManager;
        this.plugin = plugin;
        this.gameManager = manager;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (GameManager.isPlayerIG(player)){
            gameManager.dcPlayer(player);
        }

        teamManager.removePlayerFromTeam(player);
        Bukkit.getScheduler().runTaskLater(plugin, teamManager::resortPlayers, 1L);
    }
}
