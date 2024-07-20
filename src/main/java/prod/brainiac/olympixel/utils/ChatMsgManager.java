package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatMsgManager {

    private final String prefix;
    private final ChatColor prefixColor;
    private final ChatColor accentColor;

    public ChatMsgManager(ChatColor prefixColor, String prefix, ChatColor accentColor) {
        this.prefixColor = prefixColor;
        this.prefix = prefix;
        this.accentColor = accentColor;
    }

    public void announcePlayers(String message){
        GameManager.playerScores.keySet().forEach((uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null){
                player.sendMessage(prefixColor+prefix+" "+accentColor+message);
            }
        }));
    }

    public void announceAll(String message){
        Bukkit.broadcastMessage(prefixColor+prefix+" "+accentColor+message);
    }

    public void msgPlayer(Player player, String message){
        player.sendMessage(prefixColor+prefix+" "+accentColor+message);
    }

    public void sendObjective(String objective){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(accentColor+"---------Objective---------");
            player.sendMessage(ChatColor.GREEN+objective);
            player.sendMessage(accentColor+"---------------------------");
        }
    }

    public void announceWinners(List<Map.Entry<UUID, Integer>> winners){
        StringBuilder winnersList = new StringBuilder();
        winnersList.append(ChatColor.GREEN).append("Winners:\n");
        for (Map.Entry<UUID, Integer> entry : winners){
            Player winner = Bukkit.getPlayer(entry.getKey());
            int score = entry.getValue();
            if (winner != null){
                winnersList.append(ChatColor.GOLD)
                        .append("1.")
                        .append(winner.getDisplayName())
                        .append("   ")
                        .append(score)
                        .append("\n");
            }
        }
        announceAll(winnersList.toString());
    }
}
