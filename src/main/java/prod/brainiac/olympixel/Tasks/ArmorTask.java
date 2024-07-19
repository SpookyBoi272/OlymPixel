package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.commands.olympixelCommand.StartSubCommand;
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
        public void onArmorEquip(PlayerInteractEvent event) {
            Material material = event.getMaterial();
            if (material ==  Material.AIR){
                return;
            }

            Player player = event.getPlayer();
            if (!GameManager.isPlayerIG(player,getTaskID())) {
                return;
            }

            if (event.getMaterial() == Material.DIAMOND_CHESTPLATE) {
                player.sendMessage("You equipped a Diamond Chest plate");
                StartSubCommand.manager.startNextRound(player);
            }
        }

        @EventHandler
        public void onArmourPut(InventoryClickEvent event) {
            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            if (!GameManager.isPlayerIG(player, getTaskID())) {
                return;
            }

            new BukkitRunnable(){


                @Override
                public void run() {
                    if (player.getInventory().getItem(EquipmentSlot.CHEST).getType().equals(Material.DIAMOND_CHESTPLATE)){
                        player.sendMessage("You equipped a Diamond Chest plate");
                        StartSubCommand.manager.startNextRound(player);
                    }
                }
            }.runTaskLater(Olympixel.getPlugin(),1L);
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new listener();
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
