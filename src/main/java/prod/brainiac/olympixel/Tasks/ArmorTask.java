package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
import prod.brainiac.olympixel.events.ArmorEquipEvent;
import prod.brainiac.olympixel.utils.GameManager;

public class ArmorTask extends Task {

    public Listener listener;

    @Override
    public int getTaskID() {
        return 8;
    }

    @Override
    public String getObjective() {
        return "Wear a Diamond Chest plate";
    }

    public class listener implements Listener {
        @EventHandler
        public void onArmorEquip(ArmorEquipEvent event){
            Player player = event.getPlayer();
            if (!GameManager.isPlayerIG(player,getTaskID())) {
                return;
            }

            if (event.getNewArmorPiece().getType() == Material.DIAMOND_CHESTPLATE){
                player.sendMessage("You equipped a Diamond Chest plate");
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
