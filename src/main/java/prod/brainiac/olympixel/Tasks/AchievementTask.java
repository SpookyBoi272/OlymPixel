package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
import prod.brainiac.olympixel.utils.GameManager;

public class AchievementTask extends Task {

    public Listener listener;

    @Override
    public int getTaskID() {
        return 11;
    }

    @Override
    public String getObjective() {
        return "Get the 'A Terrible Fortress' Achievement";
    }

    public class listener implements Listener {
        @EventHandler
        public void onAchievement(PlayerAdvancementDoneEvent event) {
            Player player = event.getPlayer();
            Advancement advancement = event.getAdvancement();

            if (!GameManager.isPlayerIG(player,getTaskID())) {
                return;
            }

            // Check for the "A Terrible Fortress" advancement
            if (advancement.getKey().toString().equals("minecraft:nether/fortress")) {
                player.sendMessage("You got the 'A Terrible Fortress' achievement");
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
