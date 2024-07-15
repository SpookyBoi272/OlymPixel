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
    private final ArrayList<Task> allTasks = new ArrayList<>();

    public static Map<UUID, Task> onGoingTasks = new HashMap<>();
    private final ArrayList<Integer> registeredTasks = new ArrayList<>();
    public Map<UUID, Integer> playerScores = new HashMap<>();
    private int currentRound = 0;

    public GameManager() {
        allTasks.add(new CraftingTableTask());
        allTasks.add(new CryingObiTask());
        allTasks.add(new PortalTask());
    }

    public void startGame(){
        Bukkit.broadcastMessage("Game Started");
        currentRound++;
        Random random = new Random();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerScores.put(player.getUniqueId(), 0);
            int randomTaskID = random.nextInt(allTasks.size());
            Task task = allTasks.get(randomTaskID);
            onGoingTasks.put(player.getUniqueId(), task);
            player.sendMessage("---------Objective---------");
            player.sendMessage(task.getObjective());
            player.sendMessage("---------------------------");
        }
        registerTasks();
    }

    public void startNextRound(Player currentRoundWinner) {
        currentRound++;
        unregisterAllTasks();
        if (currentRound > 3) {
            endGame();
        } else {
            addScore(currentRoundWinner,1);
            Bukkit.broadcastMessage("Starting Round " + currentRound);
            Random random = new Random();
            onGoingTasks.keySet().forEach(uuid -> {
                int randomTaskID = random.nextInt(allTasks.size());
                Task task = allTasks.get(randomTaskID);
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    onGoingTasks.put(player.getUniqueId(), task);
                    player.sendMessage("---------Objective---------");
                    player.sendMessage(task.getObjective());
                    player.sendMessage("---------------------------");
                }
            });
            registerTasks();
        }




    }

    private void endGame() {

        List<Map.Entry<UUID, Integer>> winners = calculateWinner(playerScores);

        Bukkit.broadcastMessage("The Game has ended.");
        Bukkit.broadcastMessage("Winners:");
        assert winners != null;
        for (Map.Entry<UUID, Integer> winner : winners) {
            Bukkit.broadcastMessage(Objects.requireNonNull(Bukkit.getPlayer(winner.getKey())).getDisplayName());
        }

        unregisterAllTasks();
        onGoingTasks.clear();
    }

    private List<Map.Entry<UUID, Integer>> calculateWinner(Map<UUID, Integer> playerScores) {

        if (playerScores != null && playerScores.isEmpty()) {
            return null;
        }

        List<Map.Entry<UUID, Integer>> winners = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;

        assert playerScores != null;
        for (Map.Entry<UUID, Integer> entry : playerScores.entrySet()) {

            int score = entry.getValue();
            if (score > maxScore) {

                winners.clear();
                winners.add(entry);
                maxScore = entry.getValue();
            } else if (score == maxScore) {
                winners.add(entry);
            }
        }
        return winners;
    }

    public void addScore(Player player, int value) {
        int newScore = playerScores.get(player.getUniqueId()) + value;
        playerScores.put(player.getUniqueId(), newScore);
    }

    private void registerTasks(){
        for (Task task : onGoingTasks.values()) {
            if (!registeredTasks.contains(task.getTaskID())) {
                System.out.println("Task registered");
                task.registerListener(Olympixel.getPlugin());
                registeredTasks.add(task.getTaskID());
            }
        }
    }

    private void unregisterAllTasks(){
        for (Task task : onGoingTasks.values()) {
            HandlerList.unregisterAll(task.listener);
        }
    }
}
