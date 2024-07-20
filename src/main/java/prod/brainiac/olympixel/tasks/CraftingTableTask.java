package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
import prod.brainiac.olympixel.utils.GameManager;

public class CraftingTableTask extends Task{

    public Listener listener;

    @Override
    public int getTaskID() {
        return 0;
    }

    @Override
    public String getObjective() {
        return "Craft a crafting Table";
    }

    @Override
    public String getWinMsg() {
        return "You crafted a Crafting Table.";
    }

    private static class listener implements Listener{
        @EventHandler
        public void onCompletion(CraftItemEvent event){
            if(event.isCancelled()) {
                return;
            }

            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            if (event.getCurrentItem() == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked();

            if (!GameManager.isPlayerIG(player)){
                return;
            }

            if (event.getCurrentItem().getType() == Material.CRAFTING_TABLE){
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
