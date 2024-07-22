package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ScoreManager {

    ScoreboardManager manager;
    private Objective scores;
    private final ArrayList<Player> playersWithSB = new ArrayList<>();

    public ScoreManager(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager != null) {
            this.manager = manager;
            Scoreboard gameScoreboard = manager.getNewScoreboard();
            this.scores = gameScoreboard.registerNewObjective("scores", "dummy",ChatColor.GREEN+"Scores");
            scores.setDisplaySlot(DisplaySlot.SIDEBAR);
            Bukkit.getOnlinePlayers().forEach((Player player) ->{
                player.setScoreboard(gameScoreboard);
                playersWithSB.add(player);
                scores.getScore(ChatColor.DARK_PURPLE+player.getDisplayName()).setScore(0);
            });
        }
    }

    public void updateScores(Map<UUID,Integer> playerScores){
        playerScores.forEach((UUID uuid, Integer score)-> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null){
                scores.getScore(ChatColor.DARK_PURPLE+player.getDisplayName()).setScore(score);
            }
        });
    }

    public void removeAll(){
        playersWithSB.forEach((Player player) -> player.setScoreboard(TeamManager.getOlympixelScoreboard()));
    }
}
