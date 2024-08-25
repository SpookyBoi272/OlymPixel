package prod.brainiac.olympixel.commands.olympixelCommand;

import org.bukkit.entity.Player;
import prod.brainiac.olympixel.Olympixel;

public class SetCountSubCommand extends SubCommand {

    private final Olympixel plugin;

    public SetCountSubCommand(Olympixel plugin) {
        this.plugin = plugin;
    }


    @Override
    public String getName() {
        return "setCD";
    }

    @Override
    public String getDescription() {
        return "Set countdown before the game starts";
    }

    @Override
    public Boolean requiresOP() {
        return true;
    }

    @Override
    public String getSyntax() {
        return "/olympixel setCD";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage("Invalid usage of command");
            return;
        }

        if (args.length == 1) {
            plugin.configHook.setCountdownSecs(args[0].charAt(0));
        }
    }
}
