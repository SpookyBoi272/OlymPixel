package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

public class CraftingTableTask extends Task{

    public CraftingListener listener;

    public CraftingTableTask(GameManager manager) {
        super(manager);
    }

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

    public class CraftingListener implements Listener{
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
                manager.startNextRound(player);
            }
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new CraftingListener();
        Bukkit.getServer().getPluginManager().registerEvents(listener,plugin);
    }

    @Override
    public Listener getListener() {
        return listener;
    }
}
