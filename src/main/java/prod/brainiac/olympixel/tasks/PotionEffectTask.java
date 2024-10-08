package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

public class PotionEffectTask extends Task {

    public Listener listener;

    public PotionEffectTask(GameManager manager) {
        super(manager);
    }

    @Override
    public int getTaskID() {
        return 12;
    }

    @Override
    public String getObjective() {
        return "Get Speed Potion Effect";
    }

    @Override
    public String getWinMsg() {
        return "You got Speed effect.";
    }

    public class listener implements Listener {
        @EventHandler
        public void onPotionEffect(EntityPotionEffectEvent event) {

            if (!(event.getEntity() instanceof Player)) {
                return;
            }

            Player player = (Player) event.getEntity();

            if (!GameManager.isPlayerIG(player)) {
                return;
            }

            if (event.getNewEffect() != null && event.getNewEffect().getType().equals(PotionEffectType.SPEED)) {
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
