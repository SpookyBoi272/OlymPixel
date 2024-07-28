package prod.brainiac.olympixel.commands.olympixelCommand;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import prod.brainiac.olympixel.listeners.PlayerMoveListener;
import prod.brainiac.olympixel.utils.ChatMsgManager;
import prod.brainiac.olympixel.utils.GameManager;

public class StartSubCommand extends SubCommand {

    private static GameManager gameManager;
    private final ChatMsgManager chatMsgManager;
    private final JavaPlugin plugin;

    public StartSubCommand(ChatMsgManager chatMsgManager, JavaPlugin plugin){
        this.chatMsgManager = chatMsgManager;
        this.plugin = plugin;
    }

    public static GameManager getGameManager(){
        return gameManager;
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
            private boolean firstRun = true;
            private PlayerMoveListener playerMoveListener;

            @Override
            public void run() {
                if (remSecs<1){
                    this.cancel();
                }

                if (firstRun){
                    playerMoveListener = new PlayerMoveListener();
                    Bukkit.getServer().getPluginManager().registerEvents(playerMoveListener, plugin);
                    firstRun = false;
                }

                if (remSecs==0){
                    gameManager = new GameManager(plugin,chatMsgManager);
                    gameManager.startGame();
                    HandlerList.unregisterAll(playerMoveListener);
                }

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (remSecs==0){
                        player.sendTitle("Olympixel", "Game Started", 0,30,0);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1F,1F);


                    }else{
                        player.sendTitle(String.valueOf(remSecs), null, 0, 18, 0);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1F,0.1F);

                    }
                }
                remSecs--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}
