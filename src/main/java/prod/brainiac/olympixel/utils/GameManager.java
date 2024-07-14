package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.Tasks.CraftingTableTask;
import prod.brainiac.olympixel.Tasks.CryingObiTask;
import prod.brainiac.olympixel.Tasks.PortalTask;
import prod.brainiac.olympixel.Tasks.Task;

import java.util.*;

public class GameManager {
    public static Map<UUID, Task> onGoingTasks = new HashMap<>();
    private final ArrayList<Task> tasks = new ArrayList<>();
    public Map<UUID,Integer> playerScores = new HashMap<>();
    private int currentRound = 0;

    public GameManager(){
        tasks.add(new CraftingTableTask());
        tasks.add(new CryingObiTask());
        tasks.add(new PortalTask());
    }

    public void startNextRound(){
        currentRound ++;
        if (currentRound == 1){
            Bukkit.broadcastMessage("Game Started");
            Random random = new Random();
            for (Player player : Bukkit.getOnlinePlayers()){
                int randomTaskID = random.nextInt(tasks.size());
                Task task= tasks.get(randomTaskID);
                onGoingTasks.put(player.getUniqueId(),task);
                player.sendMessage("---------Objective---------");
                player.sendMessage(task.getObjective());
                player.sendMessage("---------------------------");
            }
        }else {
            Bukkit.broadcastMessage("Starting Round "+currentRound);

        }



        ArrayList<Integer> registeredTask = new ArrayList<>();
        for (Task task : onGoingTasks.values()){
            if (!registeredTask.contains(task.getTaskID())){
                task.registerListener(Olympixel.getPlugin());
                registeredTask.add(task.getTaskID());
            }
        }

    }

    public void endGame(Player winner){

        Bukkit.broadcastMessage(winner.getDisplayName()+" Won the game.");


        for (Task task : onGoingTasks.values()){
            HandlerList.unregisterAll(task.listener);
        }

        onGoingTasks.clear();
    }

    public void addScore(Player player){
        
    }

}
