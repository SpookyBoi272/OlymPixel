package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.tasks.*;

import java.util.*;

public class GameManager {
    private final Olympixel plugin;
    private final ConfigHook config;
    private static Boolean gameRunning = false;
    private final ChatMsgManager chatMsgManager;

    //played tasks are removed from startNextRound function {reset if empty}
    private final ArrayList<Task> availableTasks = new ArrayList<>();

    private Task currentTask;
    protected static final Map<UUID, Integer> playerScores = new HashMap<>();
    private int currentRound = 0;
    private ScoreManager scoreManager;

    public GameManager(Olympixel plugin, ChatMsgManager chatMsgManager) {
        this.chatMsgManager = chatMsgManager;
        this.plugin = plugin;
        this.config = plugin.configHook;
        rstAvailableTasks();
    }

    //used to start a new game
    public void startGame() {
        gameRunning = true;
        chatMsgManager.announceAll("Game Stared");
        this.scoreManager = new ScoreManager();
        currentRound++;

        //pick a random task from available tasks
        Random random = new Random();
        int randomTaskID = random.nextInt(availableTasks.size());
        currentTask = availableTasks.get(randomTaskID);

        //do not remove if repeat task is true
        if (!plugin.configHook.repeatTask()) {
            availableTasks.remove(currentTask);
        }

        //initial scores set
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            playerScores.put(player.getUniqueId(), 0);
        }
        registerTask(currentTask, plugin);

        //announce objective
        chatMsgManager.sendObjective(currentTask.getObjective());
    }

    //starts next round of currently running game
    //automatically triggered when prev round ends
    public void startNextRound(Player currentRoundWinner) {
        unregisterTask(currentTask);
        chatMsgManager.msgPlayer(currentRoundWinner, currentTask.getWinMsg());
        addScore(currentRoundWinner);
        scoreManager.updateScores(playerScores);
        chatMsgManager.sendRoundWinner(currentRoundWinner);


        //if a player has score of three then end game
        int winScore = config.getWinScore();
        if (playerScores.containsValue(winScore)) {
            endGame();
        } else {

            //else start next round
            for (UUID uuid : playerScores.keySet()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.7F, 1F);
                }
            }
            currentRound++;
            chatMsgManager.announcePlayers("Starting Round " + currentRound);

            Random random = new Random();
            //reset the available task if empty
            if (availableTasks.isEmpty()) {
                rstAvailableTasks();
            }

            //get a random task for next round
            int randomTaskID = random.nextInt(availableTasks.size());
            currentTask = availableTasks.get(randomTaskID);

            //do not remove if repeat task is true
            if (!plugin.configHook.repeatTask()) {
                availableTasks.remove(currentTask);
            }

            registerTask(currentTask, plugin);
            chatMsgManager.sendObjective(currentTask.getObjective());
        }
    }

    //ends currently running game
    private void endGame() {

        //reset scoreboards
        scoreManager.removeAll();

        //calc and display winners
        Map.Entry<UUID, Integer> winner = calculateWinner();

        chatMsgManager.announceAll("The Game has ended.");
        if (winner != null) {
            chatMsgManager.announceWinners(winner);
        }

        gameRunning = false;
    }

    public void stopGame() {
        unregisterTask(currentTask);
        scoreManager.removeAll();
        chatMsgManager.announceAll("Running game has been stopped by an admin");
        gameRunning = false;
    }

    private Map.Entry<UUID, Integer> calculateWinner() {

        if (playerScores.isEmpty()) {
            return null;
        }

        Map.Entry<UUID, Integer> winner = null;
        int maxScore = Integer.MIN_VALUE;

        for (Map.Entry<UUID, Integer> entry : playerScores.entrySet()) {
            int score = entry.getValue();
            if (score > maxScore) {
                winner = entry;
                maxScore = entry.getValue();
            }
        }
        return winner;
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

    public static Boolean isGameRunning() {
        return gameRunning;
    }

    public static Boolean isPlayerIG(Player player) {
        return playerScores.containsKey(player.getUniqueId());
    }

    public void dcPlayer(Player player) {
        playerScores.remove(player.getUniqueId());
        if (playerScores.isEmpty()) {
            endGame();
        }
    }

    private void rstAvailableTasks() {
        availableTasks.clear();
        availableTasks.addAll(Arrays.asList(
                new TradeTask(this),
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
}
