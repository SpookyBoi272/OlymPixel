package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.tasks.*;

import java.util.*;

public class GameManager {
    private final JavaPlugin plugin;
    private static Boolean gameRunning = false;
    private final ArrayList<Task> availableTasks = new ArrayList<>();
    private final ChatMsgManager chatMsgManager;

    private Task currentTask;
    protected static final Map<UUID, Integer> playerScores = new HashMap<>();
    private int currentRound = 0;
    private ScoreManager scoreManager;

    public GameManager(JavaPlugin plugin, ChatMsgManager chatMsgManager) {
        this.chatMsgManager = chatMsgManager;
        this.plugin = plugin;
        availableTasks.addAll(Arrays.asList(
                new ArmorTask(this),
                new CraftingTableTask(this),
                new CryingObiTask(this),
                new DiamondPickaxeTask(this),
                new DrinkMilkTask(this),
                new DyeTask(this),
                new FlowerTask(this),
                new KillMobTask(this),
                new PortalTask(this),
                new PotionEffectTask(this),
                new StandOnBlockTask(this)
        ));

    }

    public void startGame() {
        gameRunning = true;
        chatMsgManager.announceAll("Game Stared");
        this.scoreManager =  new ScoreManager();
        currentRound++;
        Random random = new Random();
        int randomTaskID = random.nextInt(availableTasks.size());
        currentTask = availableTasks.get(randomTaskID);
        availableTasks.remove(currentTask);
        for (Player player : Bukkit.getOnlinePlayers()){
            playerScores.put(player.getUniqueId(), 0);
        }
        registerTask(currentTask,plugin);
        chatMsgManager.sendObjective(currentTask.getObjective());
    }

    public void startNextRound(Player currentRoundWinner) {
        unregisterTask(currentTask);
        chatMsgManager.msgPlayer(currentRoundWinner, currentTask.getWinMsg());
        addScore(currentRoundWinner);
        scoreManager.updateScores(playerScores);
        chatMsgManager.announcePlayers(currentRoundWinner.getDisplayName() + " completed their Task.");
        currentRound++;

        if (playerScores.containsValue(3)) {
            endGame();
        } else {
            chatMsgManager.announcePlayers("Starting Round " + currentRound);
            Random random = new Random();
            int randomTaskID = random.nextInt(availableTasks.size());
            currentTask = availableTasks.get(randomTaskID);
            registerTask(currentTask,plugin);
            chatMsgManager.sendObjective(currentTask.getObjective());
        }
    }

    private void endGame() {
        scoreManager.removeAll();
        List<Map.Entry<UUID, Integer>> winners = calculateWinner();

        chatMsgManager.announceAll("The Game has ended.");
        if (winners != null) {
            chatMsgManager.announceWinners(winners);
        }

        gameRunning = false;
    }

    private List<Map.Entry<UUID, Integer>> calculateWinner() {

        if (playerScores.isEmpty()) {
            return null;
        }

        List<Map.Entry<UUID, Integer>> winners = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;

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

    private void addScore(Player player) {
        int newScore = playerScores.get(player.getUniqueId()) + 1;
        playerScores.put(player.getUniqueId(), newScore);
    }

    private void registerTask(Task task, JavaPlugin plugin) {
        task.registerListener(plugin);
    }

    private void unregisterTask(Task task) {
        HandlerList.unregisterAll(task.getListener());
    }

    public static Boolean isGameRunning(){
        return gameRunning;
    }

    public static Boolean isPlayerIG(Player player) {
        return playerScores.containsKey(player.getUniqueId());
    }

    public void dcPlayer(Player player){
        playerScores.remove(player.getUniqueId());
        if (playerScores.isEmpty()){
            endGame();
        }
    }
}
