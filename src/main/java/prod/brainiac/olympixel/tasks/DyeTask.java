package prod.brainiac.olympixel.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import prod.brainiac.olympixel.utils.GameManager;

public class DyeTask extends Task {

    public Listener listener;

    public DyeTask(GameManager manager) {
        super(manager);
    }

    @Override
    public int getTaskID() {
        return 7;
    }

    @Override
    public String getObjective() {
        return "Acquire Red Dye in your inventory";
    }

    @Override
    public String getWinMsg() {
        return "You acquired Red dye.";
    }

    public class listener implements Listener {
        @EventHandler
        public void onDyePick(EntityPickupItemEvent event) {
            if (!(event.getEntity() instanceof Player)) {
                return;
            }

            Player player = (Player) event.getEntity();

            if (!GameManager.isPlayerIG(player)) {
                return;
            }

            if (event.getItem().getItemStack().getType() == Material.RED_DYE) {
                manager.startNextRound(player);
            }
        }

        @EventHandler
        public void onFlowerGet(InventoryClickEvent event){
            Player player = (Player) event.getWhoClicked();
            if (!GameManager.isPlayerIG(player)){
                return;
            }
            if (isMaterialObtained(event)){
                manager.startNextRound(player);
            }
        }

        private Boolean isMaterialObtained(InventoryClickEvent event){
            Inventory clickedInventory = event.getClickedInventory();
            if (clickedInventory == null || event.getCurrentItem()==null) {
                return false;
            }

            if (clickedInventory.getType() == InventoryType.PLAYER){
                if ((event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_ONE) && event.getCursor()!= null ){
                    return event.getCursor().getType().equals(Material.RED_DYE);
                }
                ItemStack currentItem = event.getCurrentItem();
                return currentItem.getType().equals(Material.RED_DYE);
            }else if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && event.getView().getBottomInventory().getType() == InventoryType.PLAYER){
                return hasSpace(event.getView().getBottomInventory(), event.getCurrentItem());
            }

            return false;
        }

        private boolean hasSpace(Inventory inventory, ItemStack item) {
            int amountToMove = item.getAmount();
            for (ItemStack slotItem : inventory.getContents()) {
                if (slotItem == null || slotItem.getType() == Material.AIR) {
                    return true;
                } else if (slotItem.isSimilar(item)) {
                    amountToMove -= (slotItem.getMaxStackSize() - slotItem.getAmount());
                    if (amountToMove <= 0) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void registerListener(JavaPlugin plugin) {
        listener = new listener();
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public Listener getListener() {
        return listener;
    }
}
