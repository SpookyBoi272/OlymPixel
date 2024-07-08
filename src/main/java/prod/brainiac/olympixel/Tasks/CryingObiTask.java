package prod.brainiac.olympixel.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.utils.GameManager;

public class CryingObiTask extends Task{
    @Override
    public int getTaskID() {
        return 2;
    }

    @Override
    public String getObjective() {
        return "Acquire a Crying Obsidian in your inventory";
    }

    public class listener implements Listener{
        @EventHandler
        public void onCryObiPick(EntityPickupItemEvent event){

            if (!(event.getEntity() instanceof Player)){
                return;
            }

            Player player = (Player) event.getEntity();

            if (!GameManager.map.containsKey(player.getUniqueId())){
                return;
            }

            if (GameManager.map.get(player.getUniqueId()).getTaskID() == getTaskID()){
                player.sendMessage("You acquired Crying Obsidian");
            }
        }

        @EventHandler
        public void onCryObiGet(InventoryClickEvent event){
            Inventory clickedInventory = event.getClickedInventory();
            InventoryAction action = event.getAction();
            Player player = (Player) event.getWhoClicked();

            if (clickedInventory != null && clickedInventory.getType() == InventoryType.CHEST) {
                // Check if the action involves moving items to the player's inventory
                if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    ItemStack currentItem = event.getCurrentItem();

                    if (currentItem != null) {
                        String itemName = currentItem.getType().name();
                        player.sendMessage(ChatColor.GREEN + "You moved " + itemName + " to your inventory.");
                    }
                } else if (action == InventoryAction.PICKUP_ALL ||
                        action == InventoryAction.PICKUP_HALF ||
                        action == InventoryAction.PICKUP_ONE ||
                        action == InventoryAction.PICKUP_SOME) {
                    if (event.getView().getBottomInventory() == player.getInventory()) {
                        ItemStack currentItem = event.getCurrentItem();

                        if (currentItem != null) {
                            String itemName = currentItem.getType().name();
                            player.sendMessage(ChatColor.GREEN + "You moved " + itemName + " to your inventory.");
                        }
                    }
                }
            }
        }
    }


    @Override
    public void registerListener(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(new listener(), Olympixel.getPlugin());
    }


}
