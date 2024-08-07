package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

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
        @EventHandler
        public void onPlayerTrade(PlayerStatisticIncrementEvent event) {

            Player player = event.getPlayer();

            if (!GameManager.isPlayerIG(player)) {
                return;
            }

            if (event.getStatistic() == Statistic.TRADED_WITH_VILLAGER) {
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
        return null;
    }
}
