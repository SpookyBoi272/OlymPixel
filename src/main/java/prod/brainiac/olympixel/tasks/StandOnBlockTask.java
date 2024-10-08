package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

public class StandOnBlockTask extends Task {

    public Listener listener;

    public StandOnBlockTask(GameManager manager) {
        super(manager);
    }

    @Override
    public int getTaskID() {
        return 6;
    }

    @Override
    public String getObjective() {
        return "Stand on a Diamond Block";
    }

    @Override
    public String getWinMsg() {
        return "You stood on a Diamond Block";
    }

    public class GameListener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();

            if (!GameManager.isPlayerIG(player)) {
                return;
            }

            if (player.getLocation().subtract(0,1,0).getBlock().getType() == Material.DIAMOND_BLOCK) {
                manager.startNextRound(player);
            }
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new GameListener();
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public Listener getListener() {
        return listener;
    }
}
