package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.tasks.*;

import java.util.*;

public class GameManager {
    private final ArrayList<Task> availableTasks = new ArrayList<>();
    private final ChatMsgManager chatMsgManager = new ChatMsgManager(ChatColor.LIGHT_PURPLE,"[Olympixel]", ChatColor.DARK_PURPLE );

    private Task currentTask;
    protected static final Map<UUID, Integer> playerScores = new HashMap<>();
    private int currentRound = 0;

    public GameManager() {
        availableTasks.addAll(Arrays.asList(

                new ArmorTask(),
                new CraftingTableTask(),
                new CryingObiTask(),
                new DiamondPickaxeTask(),
                new DrinkMilkTask(),
                new DyeTask(),
                new FlowerTask(),
                new KillMobTask(),
                new PortalTask(),
                new PotionEffectTask(),
                new StandOnBlockTask()
        ));

    }

    public void startGame() {
        chatMsgManager.announceAll("Game Stared");
        currentRound++;
        Random random = new Random();
        int randomTaskID = random.nextInt(availableTasks.size());
        currentTask = availableTasks.get(randomTaskID);
        availableTasks.remove(currentTask);
        for (Player player : Bukkit.getOnlinePlayers()){
            playerScores.put(player.getUniqueId(), 0);
        }
        registerTask(currentTask);
        chatMsgManager.sendObjective(currentTask.getObjective());
    }

    public void startNextRound(Player currentRoundWinner) {
        unregisterTask(currentTask);
        chatMsgManager.msgPlayer(currentRoundWinner, currentTask.getWinMsg());
        addScore(currentRoundWinner);
        chatMsgManager.announcePlayers(currentRoundWinner.getDisplayName() + " completed their Task.");
        currentRound++;

        if (currentRound > 3) {
            endGame();
        } else {
            chatMsgManager.announcePlayers("Starting Round " + currentRound);
            Random random = new Random();
            int randomTaskID = random.nextInt(availableTasks.size());
            currentTask = availableTasks.get(randomTaskID);
            registerTask(currentTask);
            chatMsgManager.sendObjective(currentTask.getObjective());
        }
    }

    private void endGame() {
        List<Map.Entry<UUID, Integer>> winners = calculateWinner();

        chatMsgManager.announceAll("The Game has ended.");
        if (winners != null) {
            chatMsgManager.announceWinners(winners);
        }
    }

    private List<Map.Entry<UUID, Integer>> calculateWinner() {

        if (GameManager.playerScores.isEmpty()) {
            return null;
        }

        List<Map.Entry<UUID, Integer>> winners = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;

        for (Map.Entry<UUID, Integer> entry : GameManager.playerScores.entrySet()) {

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

    private void addScore(Player player) {
        int newScore = playerScores.get(player.getUniqueId()) + 1;
        playerScores.put(player.getUniqueId(), newScore);
    }

    private void registerTask(Task task) {
        task.registerListener(Olympixel.getPlugin());
    }

    private void unregisterTask(Task task) {
        HandlerList.unregisterAll(task.getListener());
    }

    public static Boolean isPlayerIG(Player player) {
        return playerScores.containsKey(player.getUniqueId());
    }
}
