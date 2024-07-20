package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
import prod.brainiac.olympixel.utils.GameManager;

public class KillMobTask extends Task {

    public Listener listener;

    @Override
    public int getTaskID() {
        return 9;
    }

    @Override
    public String getObjective() {
        return "Kill a Zombie";
    }

    @Override
    public String getWinMsg() {
        return "You killed a zombie.";
    }

    public static class listener implements Listener {
        @EventHandler
        public void onMobKill(EntityDeathEvent event) {
            if (event.getEntity().getKiller() == null) {
                return;
            }

            Player player = event.getEntity().getKiller();

            if (!GameManager.isPlayerIG(player)) {
                return;
            }

            if (event.getEntity().getType() == EntityType.ZOMBIE) {
                StartSubCommand.manager.startNextRound(player);
            }
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new listener();
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public Listener getListener() {
        return listener;
    }
}
