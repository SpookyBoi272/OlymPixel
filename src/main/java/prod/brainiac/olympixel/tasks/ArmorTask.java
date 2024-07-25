package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.events.ArmorEquipEvent;
import prod.brainiac.olympixel.utils.GameManager;

public class ArmorTask extends Task {

    public GameListener listener;

    public ArmorTask(GameManager manager) {
        super(manager);
    }

    @Override
    public int getTaskID() {
        return 8;
    }

    @Override
    public String getObjective() {
        return "Wear a Diamond Chest plate";
    }

    @Override
    public String getWinMsg() {
        return "You equipped Diamond Chest plate.";
    }

    public class GameListener implements Listener {
        @EventHandler
        public void onArmorEquip(ArmorEquipEvent event){
            Player player = event.getPlayer();
            if (!GameManager.isPlayerIG(player)) {
                return;
            }

            if (event.getNewArmorPiece().getType() == Material.DIAMOND_CHESTPLATE){
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
