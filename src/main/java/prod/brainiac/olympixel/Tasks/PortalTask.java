package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
import prod.brainiac.olympixel.utils.GameManager;

public class PortalTask extends Task{

    public Listener listener;

    @Override
    public int getTaskID() {
        return 1;
    }

    @Override
    public String getObjective() {
        return "Travel Through a Nether Portal";
    }

    public class listener implements Listener{
        @EventHandler
        public void onPortalTravel(PlayerPortalEvent event){
            Player player = event.getPlayer();

            if (!GameManager.onGoingTasks.containsKey(player.getUniqueId())){
                return;
            }

            if (GameManager.onGoingTasks.get(player.getUniqueId()).getTaskID() == getTaskID()){
                player.sendMessage("You entered through nether portal.");
                StartSubCommand.manager.startNextRound(player);
            }
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new listener();
        Bukkit.getServer().getPluginManager().registerEvents(listener,plugin);
    }
}
