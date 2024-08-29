package prod.brainiac.olympixel.commands;

import org.bukkit.entity.Player;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.utils.ChatMsgManager;

public class SetWinScoreSubCommand extends SubCommand {

    private final Olympixel plugin;
    private final ChatMsgManager chatMsgManager;

    public SetWinScoreSubCommand(Olympixel plugin, ChatMsgManager chatMsgManager) {
        this.plugin = plugin;
        this.chatMsgManager = chatMsgManager;
    }

    @Override
    public String getName() {
        return "setWS";
    }

    @Override
    public String getDescription() {
        return "Sets score one of the Players must achieve to win the game";
    }

    @Override
    public Boolean requiresOP() {
        return true;
    }

    @Override
    public String getSyntax() {
        return "/olympixel setWS <score>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length != 2) {
            chatMsgManager.msgPlayer(player, "Invalid usage of command");
            return;
        }

        String str = args[1];
        try {
            int score = Integer.parseInt(str);
            plugin.configHook.setWinScore(score);
            chatMsgManager.msgPlayer(player, "Successfully set winScore to " + score);
        } catch (NumberFormatException e) {
            chatMsgManager.msgPlayer(player, "Invalid usage of command");
        }
    }
}
