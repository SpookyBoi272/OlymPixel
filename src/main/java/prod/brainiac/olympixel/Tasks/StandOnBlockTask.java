package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
import prod.brainiac.olympixel.utils.GameManager;

public class StandOnBlockTask extends Task {

    public Listener listener;

    @Override
    public int getTaskID() {
        return 6;
    }

    @Override
    public String getObjective() {
        return "Stand on a Diamond Block";
    }

    public class listener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();

            if (!GameManager.isPlayerIG(player,getTaskID())) {
                return;
            }

            if (player.getLocation().subtract(0,1,0).getBlock().getType() == Material.DIAMOND_BLOCK) {
                player.sendMessage("You are standing on a Diamond Block");
                StartSubCommand.manager.startNextRound(player);
            }
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new listener();
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
