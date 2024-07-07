package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.utils.GameManager;

public class CraftingTableTask extends Task{

    @Override
    public int getTaskID() {
        return 0;
    }

    @Override
    public String getObjective() {
        return "Craft a crafting Table";
    }

    private class listener implements Listener{
        @EventHandler
        public void onCompletion(CraftItemEvent event){
            Player player = (Player) event.getWhoClicked();

            if (!GameManager.map.containsKey(player.getUniqueId())){
                return;
            }

            if (GameManager.map.get(player.getUniqueId()).getTaskID() == getTaskID()){
                player.sendMessage("You crafted a crafting Table while ig");
            }
        }
    }

    @Override
    public void RegisterListener(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(new listener(),plugin);
    }
}
