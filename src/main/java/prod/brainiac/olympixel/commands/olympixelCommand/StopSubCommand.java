package prod.brainiac.olympixel.commands.olympixelCommand;

import org.bukkit.entity.Player;
import prod.brainiac.olympixel.utils.GameManager;

public class StopSubCommand extends SubCommand {

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
        }
    }
}
