package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class TeamManager {
    ScoreboardManager manager;
    private static Scoreboard OlympixelScoreboard;
    private Objective objective;

    public  void init(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager != null){
            this.manager = manager;
            OlympixelScoreboard = manager.getNewScoreboard();
            objective = OlympixelScoreboard.registerNewObjective("Olympixel","dummy",ChatColor.GREEN+"READY"+ChatColor.RESET);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            addAllPlayers();
        }
    }

    public  void addPlayerToTeam(Player player) {
        player.setScoreboard(OlympixelScoreboard);
        objective.getScore(ChatColor.RED + player.getName()).setScore(Bukkit.getOnlinePlayers().size());
    }

    public void removePlayerFromTeam(Player player) {
        player.setScoreboard(manager.getMainScoreboard());
    }

    public void resortPlayers(){
        int i = 1;
        for (Player player : Bukkit.getOnlinePlayers()) {
            objective.getScore(ChatColor.RED + player.getName()).setScore(i);
            i++;
        }
    }

    private void addAllPlayers(){
        int i = 1;
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayerToTeam(player);
            objective.getScore(ChatColor.RED + player.getName()).setScore(i);
            i++;
        }
    }

    public static Scoreboard getOlympixelScoreboard() {
        return OlympixelScoreboard;
    }
}
