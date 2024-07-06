package prod.brainiac.olympixel.commands.olympixelCommand;

import org.bukkit.entity.Player;

public class StartSubCommand extends SubCommand {

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

    }
}
