package prod.brainiac.olympixel.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.listeners.PlayerMoveListener;
import prod.brainiac.olympixel.utils.ChatMsgManager;
import prod.brainiac.olympixel.utils.GameManager;

public class StartSubCommand extends SubCommand {

    private static GameManager gameManager;
    private final ChatMsgManager chatMsgManager;
    private final Olympixel plugin;

    public StartSubCommand(ChatMsgManager chatMsgManager, Olympixel plugin) {
        this.chatMsgManager = chatMsgManager;
        this.plugin = plugin;
    }

    public static GameManager getGameManager() {
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

        if (GameManager.isGameRunning()) {
            chatMsgManager.msgPlayer(player, "Running Game has not ended yet");
            return;
        }

        new BukkitRunnable() {

            int remSecs = plugin.configHook.getCountdownSecs();
            private boolean firstRun = true;
            private PlayerMoveListener playerMoveListener;

            @Override
            public void run() {
                if (remSecs < 1) {
                    this.cancel();
                }

                if (firstRun) {
                    Bukkit.getOnlinePlayers().forEach(player -> player.setInvulnerable(true));
                    playerMoveListener = new PlayerMoveListener();
                    Bukkit.getServer().getPluginManager().registerEvents(playerMoveListener, plugin);
                    firstRun = false;
                }

                if (remSecs == 0) {
                    gameManager = new GameManager(plugin, chatMsgManager);
                    gameManager.startGame();
                    HandlerList.unregisterAll(playerMoveListener);
                    Bukkit.getOnlinePlayers().forEach(player -> player.setInvulnerable(false));
                }

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (remSecs == 0) {
//                        player.sendTitle(ChatColor.DARK_PURPLE + "Olympixel", ChatColor.LIGHT_PURPLE + "Game Started", 0, 30, 0);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);


                    } else {
                        player.sendTitle(ChatColor.DARK_PURPLE + String.valueOf(remSecs), ChatColor.LIGHT_PURPLE + "Game Starting", 0, 18, 0);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 0.1F);

                    }
                }
                remSecs--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}
