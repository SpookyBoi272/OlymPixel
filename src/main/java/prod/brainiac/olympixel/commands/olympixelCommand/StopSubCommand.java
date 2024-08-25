package prod.brainiac.olympixel.commands.olympixelCommand;

import org.bukkit.entity.Player;
import prod.brainiac.olympixel.utils.ChatMsgManager;
import prod.brainiac.olympixel.utils.GameManager;

public class StopSubCommand extends SubCommand {

    private final ChatMsgManager chatMsgManager;

    public StopSubCommand(ChatMsgManager chatMsgManager) {
        this.chatMsgManager = chatMsgManager;
    }


    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stop currently running match of olympixel";
    }

    @Override
    public Boolean requiresOP() {
        return true;
    }

    @Override
    public String getSyntax() {
        return "/olympixel stop";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (GameManager.isGameRunning()) {
            StartSubCommand.getGameManager().stopGame();
        } else {
            chatMsgManager.msgPlayer(player, "There is no game running at the moment. Please start a game before trying to stop it.");
        }
    }
}
