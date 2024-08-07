package prod.brainiac.olympixel.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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

        GameManager.playerScores.keySet().forEach((uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null){
                //send chat message
                player.sendMessage(accentColor+"---------Objective---------");
                player.sendMessage(ChatColor.GREEN+objective);
                player.sendMessage(accentColor+"---------------------------");

                //send title
                player.sendTitle(ChatColor.GREEN + "New Objective", accentColor+objective, 10, 40, 10);
            }
        }));
    }

    public void sendRoundWinner(Player player){
        announcePlayers(ChatColor.GOLD +player.getDisplayName()+ accentColor +" completed their Task.");
    }

    public void announceWinners(Map.Entry<UUID, Integer> winnerEntry){
        Player winner = Bukkit.getPlayer(winnerEntry.getKey());
        int score = winnerEntry.getValue();
        if (winner != null){
            announceAll(ChatColor.GOLD+winner.getDisplayName()+ accentColor +" won the game");
            for (Player player : Bukkit.getOnlinePlayers()){
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                player.sendTitle(prefixColor+"Winner", ChatColor.GOLD+winner.getDisplayName() + "\n Score: "+score, 10, 40, 20);
            }
        }
    }
}
