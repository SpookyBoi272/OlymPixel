package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
import prod.brainiac.olympixel.utils.GameManager;

public class DiamondPickaxeTask extends Task {

    public Listener listener;

    @Override
    public int getTaskID() {
        return 4;
    }

    @Override
    public String getObjective() {
        return "Acquire a Diamond Pickaxe in your inventory";
    }

    public class listener implements Listener {
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            Player player = (Player) event.getWhoClicked();

            if (!GameManager.isPlayerIG(player,getTaskID())) {
                return;
            }

            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.DIAMOND_PICKAXE) {
                player.sendMessage("You acquired a Diamond Pickaxe");
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
