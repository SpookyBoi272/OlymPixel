package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

public class PortalTask extends Task{
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

            if (!GameManager.map.containsKey(player.getUniqueId())){
                return;
            }

            if (GameManager.map.get(player.getUniqueId()).getTaskID() == getTaskID()){
                player.sendMessage("You entered through nether portal.");
            }
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(new listener(),plugin);
    }
}
