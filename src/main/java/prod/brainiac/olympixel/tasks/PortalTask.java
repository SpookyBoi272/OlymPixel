package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

public class PortalTask extends Task{

    public PortalListener listener;

    public PortalTask(GameManager manager) {
        super(manager);
    }

    @Override
    public int getTaskID() {
        return 1;
    }

    @Override
    public String getObjective() {
        return "Travel Through a Nether Portal";
    }

    @Override
    public String getWinMsg() {
        return "You travelled through a portal.";
    }

    public class PortalListener implements Listener{
        @EventHandler
        public void onPortalTravel(PlayerPortalEvent event){
            Player player = event.getPlayer();

            if (!GameManager.isPlayerIG(player)){
                return;
            }

            manager.startNextRound(player);
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new PortalListener();
        Bukkit.getServer().getPluginManager().registerEvents(listener,plugin);
    }

    @Override
    public Listener getListener() {
        return listener;
    }
}
