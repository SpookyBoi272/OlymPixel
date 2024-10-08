package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

public class DrinkMilkTask extends Task {

    public Listener listener;

    public DrinkMilkTask(GameManager manager) {
        super(manager);
    }

    @Override
    public int getTaskID() {
        return 10;
    }

    @Override
    public String getObjective() {
        return "Drink Milk";
    }

    @Override
    public String getWinMsg() {
        return "You drank cow milk.";
    }

    public class listener implements Listener {
        @EventHandler
        public void onMilkDrink(PlayerItemConsumeEvent event) {
            Player player = event.getPlayer();

            if (!GameManager.isPlayerIG(player)) {
                return;
            }

            if (event.getItem().getType() == Material.MILK_BUCKET) {
                manager.startNextRound(player);
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
