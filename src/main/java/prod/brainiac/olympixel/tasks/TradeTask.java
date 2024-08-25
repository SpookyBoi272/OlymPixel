package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TradeTask extends Task {

    public Listener listener;

    public TradeTask(GameManager manager) {
        super(manager);
    }

    @Override
    public int getTaskID() {
        return 12;
    }

    @Override
    public String getObjective() {
        return "Trade an item with a villager";
    }

    @Override
    public String getWinMsg() {
        return "You traded an item with a villager";
    }

    private class GameListener implements Listener {
        private final Map<UUID, Long> lastTradeTime = new HashMap<>();

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerTrade(PlayerStatisticIncrementEvent event) {

            Player player = event.getPlayer();

            if (!GameManager.isPlayerIG(player) || !event.getStatistic().equals(Statistic.TRADED_WITH_VILLAGER)) {
                Bukkit.getLogger().info("Player not in game, event ignored.");
                return;
            }

            UUID playerUUID = player.getUniqueId();
            long currentTime = System.currentTimeMillis();

            // Add logging for debug
            if (lastTradeTime.containsKey(playerUUID)) {
                long lastTrade = lastTradeTime.get(playerUUID);
                Bukkit.getLogger().info("Current Time: " + currentTime + ", Last Trade Time: " + lastTrade);
                Bukkit.getLogger().info("Time Difference: " + (currentTime - lastTrade));
            }

            // Check cooldown
            // 1000 milliseconds (1 second)
            long tradeCooldown = 10000;
            if (lastTradeTime.containsKey(playerUUID) && (currentTime - lastTradeTime.get(playerUUID)) <= tradeCooldown) {
                Bukkit.getLogger().info("Cooldown active, event ignored.");
                // Ignore event if within cooldown period
            } else {
                // Update last trade time
                lastTradeTime.put(playerUUID, currentTime);
                Bukkit.getLogger().info("Trade event triggered for player: " + event.getStatistic());


                if (event.getStatistic().equals(Statistic.TRADED_WITH_VILLAGER)) {
                    Bukkit.getLogger().info("Starting next round for player: " + player.getName());
                    manager.startNextRound(player);
                }
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
        return null;
    }
}
