package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class TeamManager {
    private static Scoreboard OlympixelScoreboard;
    private static final String teamName = "Olympixel";
    private static Team team;
    private static Objective objective;

    public static void init(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        OlympixelScoreboard = manager.getNewScoreboard();
        createTeam(ChatColor.GREEN);
        objective = OlympixelScoreboard.registerNewObjective("Olympixel","dummy",ChatColor.GREEN+"READY");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        addAllPlayers();
    }

    private static void createTeam(ChatColor color) {
        team = OlympixelScoreboard.registerNewTeam(teamName);
        team.setDisplayName(color + teamName);
        team.setColor(color);
        team.setPrefix(color.toString());
        team.setSuffix(ChatColor.RESET.toString());
    }

    public static void addPlayerToTeam(Player player) {
        if (team != null) {
            team.addEntry(player.getName());
            player.setScoreboard(OlympixelScoreboard);
        }
    }

    public static void removePlayerFromTeam(Player player) {
        if (team != null) {
            team.removeEntry(player.getName());
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            OlympixelScoreboard.resetScores(ChatColor.RED + player.getName());
        }
    }

    public static void resortPlayers(){
        objective.unregister();
        objective = OlympixelScoreboard.registerNewObjective("Olympixel","dummy",ChatColor.GREEN+"READY");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        addAllPlayers();
    }

    private static void addAllPlayers(){
        int i = 1;
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayerToTeam(player);
            objective.getScore(ChatColor.RED + player.getName()).setScore(i);
            i++;
        }
    }
}
