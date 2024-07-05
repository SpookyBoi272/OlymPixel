package prod.brainiac.olympixel.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OlympixelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()){
            commandSender.sendMessage("You do not have permissions to execute this command");
            return true;
        }


        return true;
    }
}
