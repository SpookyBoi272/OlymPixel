package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.Tasks.CraftingTableTask;
import prod.brainiac.olympixel.Tasks.CryingObiTask;
import prod.brainiac.olympixel.Tasks.PortalTask;
import prod.brainiac.olympixel.Tasks.Task;

import java.util.*;

public class GameManager {
    public static Map<UUID, Task> map = new HashMap<>();
    private final ArrayList<Task> tasks = new ArrayList<>();

    public GameManager(){
        tasks.add(new CraftingTableTask());
        tasks.add(new CryingObiTask());
        tasks.add(new PortalTask());
    }

    public void startGame(){
        Bukkit.broadcastMessage("Game Started");

        Random random = new Random();
        for (Player player : Bukkit.getOnlinePlayers()){
            int randomTaskID = random.nextInt(tasks.size());
            Task task= tasks.get(randomTaskID);
            map.put(player.getUniqueId(),task);
            player.sendMessage("---------Objective---------");
            player.sendMessage(task.getObjective());
            player.sendMessage("---------------------------");
        }

        ArrayList<Integer> registeredTask = new ArrayList<>();
        for (Task task : map.values()){
            if (!registeredTask.contains(task.getTaskID())){
                task.registerListener(Olympixel.getPlugin());
                registeredTask.add(task.getTaskID());
            }
        }

    }

}
