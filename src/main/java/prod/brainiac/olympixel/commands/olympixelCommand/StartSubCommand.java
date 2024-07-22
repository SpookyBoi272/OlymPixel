package prod.brainiac.olympixel.commands.olympixelCommand;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.utils.ChatMsgManager;
import prod.brainiac.olympixel.utils.GameManager;

public class StartSubCommand extends SubCommand {

    public static GameManager manager;
    private final ChatMsgManager chatMsgManager;
    private final JavaPlugin plugin;

    public StartSubCommand(ChatMsgManager chatMsgManager, JavaPlugin plugin){
        this.chatMsgManager = chatMsgManager;
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start a new match of Olympixel among online players";
    }

    @Override
    public Boolean requiresOP() {
        return true;
    }

    @Override
    public String getSyntax() {
        return "/olympixel start";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (GameManager.isGameRunning()){
            chatMsgManager.msgPlayer(player, "Running Game has not ended yet");
            return;
        }

        new BukkitRunnable() {

            int remSecs = 4;

            @Override
            public void run() {
                if (remSecs<1){
                    this.cancel();
                }

                if (remSecs==0){
                    manager = new GameManager(plugin,chatMsgManager);
                    manager.startGame();
                }

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (remSecs==0){
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN+"Game Started"));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1F,1F);


                    }else{
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED+"Game Starting in: "+remSecs));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1F,0.1F);
                    }
                }
                remSecs--;
            }
        }.runTaskTimer(plugin, 0L, 20L);


    }
}
